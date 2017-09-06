package com.cerberus.net.codec.packet;

import java.util.List;

import com.cerberus.net.packet.Packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Packet Decoder for the #317 Protocol (Disabled ISAAC)
 * @author Lev Gazarov
 *
 */
public class PacketDecoder extends ByteToMessageDecoder {
	private int opcode = -1;
	private int size = 0;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf incoming, List<Object> out) throws Exception {
		if (opcode == -1) {
			if (incoming.isReadable(1)) {
				opcode = (incoming.readUnsignedByte()) & 0xFF;
				size = incoming.readableBytes();		
			}
		}
		
		if (opcode != 1) {
			if (incoming.isReadable(size)) {
				try {
					out.add(new Packet(opcode, incoming.readBytes(size)));
				} finally {
					opcode = -1;
					size = 0;
				}
			}
		}
	}

}
