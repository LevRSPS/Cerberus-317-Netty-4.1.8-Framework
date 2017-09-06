package com.cerberus.net.codec.login;

import java.security.SecureRandom;
import java.util.List;

import com.cerberus.login.LoginInformation;
import com.cerberus.security.ISAACCipher;
import com.cerberus.util.ByteBufUtils;
import com.google.common.collect.ImmutableSet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * The login decoder for the #317 protocol
 * @author Lev Gazarov
 *
 */
@SuppressWarnings("unused")
public class LoginDecoder extends ByteToMessageDecoder {
	/**
	 * Constants
	 */
	private final static ImmutableSet<Integer> acceptableTypes = ImmutableSet.of(16,18);
	private final static int PROTOCOL = 317;
	private final static int SECURITY_ID = 10;
	private final static int CONNECTION_REQUEST = 14;
	private final static int MAGIC_ID = 0xFF;
	private final static byte[] INITIAL_RESPONSE = new byte[] { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };
	private final static SecureRandom random = new SecureRandom();
	
	/**
	 * Variables
	 */
	private int state = 0;
	
	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf in, List<Object> arg2) throws Exception {
		Channel channel = arg0.channel();
		
		if (!channel.isOpen()) {
			return;
		}
		
		switch(state) {
		case 0:
			int connectionRequest = in.readUnsignedByte();
			int nameSeed = in.readUnsignedByte();
			
			if (connectionRequest != CONNECTION_REQUEST) {
				ByteBuf buf = Unpooled.buffer();
				buf.writeByte(10);
				channel.writeAndFlush(buf);
				return;
			}
			
			ByteBuf stageOneBuffer = Unpooled.buffer();
			stageOneBuffer.writeBytes(INITIAL_RESPONSE);
			stageOneBuffer.writeByte(0);
			stageOneBuffer.writeLong(random.nextLong());
			channel.writeAndFlush(stageOneBuffer);
			
			state = 1;
			break;
		case 1:
			int connectionType = in.readUnsignedByte();
			
			if (!acceptableTypes.contains(connectionType)) {
				ByteBuf buf = Unpooled.buffer();
				buf.writeByte(11);
				channel.writeAndFlush(buf);
				return;
			}
			
			state = 2;
			break;
		case 2:
			int length = in.readUnsignedByte();

			System.out.println("length: "+length+" remaining bytes: "+in.readableBytes());
			
			int magicId = in.readUnsignedByte();
			
			if (magicId != MAGIC_ID) {
				ByteBuf buf = Unpooled.buffer();
				buf.writeByte(11);
				channel.writeAndFlush(buf);
				return;
			}
			
			
			int clientVersion = in.readShort();
			
			if (clientVersion != PROTOCOL) {
				ByteBuf buf = Unpooled.buffer();
				buf.writeByte(11);
				channel.writeAndFlush(buf);
				return;
			}
			
			int memoryId = in.readUnsignedByte();
			
			if (memoryId > 1) {
				ByteBuf buf = Unpooled.buffer();
				buf.writeByte(11);
				channel.writeAndFlush(buf);
				return;
			}
			
			for (int i = 0; i < 9; i++) {
				in.readInt();
			}
			
			int securityId = in.readUnsignedByte();
			
			if (securityId != SECURITY_ID) {
				ByteBuf buf = Unpooled.buffer();
				buf.writeByte(11);
				channel.writeAndFlush(buf);
				return;
			}
			
			long clientSeed = in.readLong();
			long otherSeed = in.readLong();
			
			int[] seed = { (int) clientSeed >> 32, (int) clientSeed, (int) otherSeed >> 32, (int) otherSeed };
			
			ISAACCipher decryptor = new ISAACCipher(seed);
			
			for (int i = 0; i < 4; i++) {
				seed[i] += 50;
			}
			
			int uid = in.readInt();
			
			String username = ByteBufUtils.getString(in);
			String password = ByteBufUtils.getString(in);
			
			arg2.add(new LoginInformation(username, password, decryptor, new ISAACCipher(seed), 2, 2, 0));
			break;
		}
	}

}
