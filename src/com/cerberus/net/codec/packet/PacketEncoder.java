package com.cerberus.net.codec.packet;

import com.cerberus.net.packet.Packet;
import com.cerberus.net.packet.Packet.Type;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * A Packet Encoder used to encode outgoing messages for the 317 protocol (Disabled ISAAC)
 * @author Lev Gazarov
 *
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
	
	@Override
	protected void encode(ChannelHandlerContext arg0, Packet arg1, ByteBuf arg2) throws Exception {
		final int opcode = arg1.getOpcode();

		if (opcode > 0) {
			arg2.writeByte(opcode);

			Type type = arg1.getType();
			int length = arg1.getSize();

			switch (type) {
			case VARIABLE:
				arg2.writeByte(length);
				break;
			case VARIABLE_SHORT:
				arg2.writeShort(length);
				break;
			default:
				break;
			}
			arg2.writeBytes(arg1.getBuffer());
		} else {
			arg2.writeBytes(arg1.getBuffer());
		}
	}
	
}
