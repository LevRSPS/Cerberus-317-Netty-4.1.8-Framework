package com.cerberus.net.packet.out.impl;

import com.cerberus.net.packet.PacketBuilder;
import com.cerberus.net.packet.Packet.Type;
import com.cerberus.net.packet.out.OutgoingPacket;
import com.cerberus.player.Player;

public class CameraReset implements OutgoingPacket {

	@Override
	public int opcode() {
		return 107;
	}

	@Override
	public Type type() {
		return Type.FIXED;
	}

	@Override
	public void sendPacket(Player player) {
		PacketBuilder builder = new PacketBuilder(opcode(), type());
		
		player.getSession().getChannel().writeAndFlush(builder.toPacket());

	}

}
