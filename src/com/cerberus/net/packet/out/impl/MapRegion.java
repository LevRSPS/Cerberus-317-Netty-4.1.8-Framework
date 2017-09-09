package com.cerberus.net.packet.out.impl;

import com.cerberus.model.Position;
import com.cerberus.net.packet.Packet.Type;
import com.cerberus.net.packet.PacketBuilder;
import com.cerberus.net.packet.ValueType;
import com.cerberus.net.packet.out.OutgoingPacket;
import com.cerberus.player.Player;

public class MapRegion implements OutgoingPacket {
	private Position position;
	
	public MapRegion(Position position) {
		this.position = position;
	}
	
	@Override
	public int opcode() {
		return 73;
	}

	@Override
	public Type type() {
		return Type.FIXED;
	}

	@Override
	public void sendPacket(Player player) {
		PacketBuilder builder = new PacketBuilder(opcode(), type());
		int regionX = (position.getX() >> 3) - 6;
		int regionY = (position.getY() >> 3) - 6;
		builder.putShort(regionX + 6, ValueType.A); 
		builder.putShort(regionY + 6);
		
		player.getSession().getChannel().writeAndFlush(builder.toPacket());
	}

}
