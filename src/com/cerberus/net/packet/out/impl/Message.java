package com.cerberus.net.packet.out.impl;

import com.cerberus.net.packet.PacketBuilder;
import com.cerberus.net.packet.out.OutgoingPacket;
import com.cerberus.player.Player;
import com.cerberus.net.packet.Packet.Type;

/**
 * An implementation of the {@link OutgoingPacket} interface used to send a message to a player
 * @author Lev Gazarov
 *
 */
public class Message implements OutgoingPacket {
	private String message;
	
	public Message(String message) {
		this.message = message;
	}
	
	@Override
	public Type type() {
		return Type.VARIABLE;
	}

	@Override
	public int opcode() {
		return 253;
	}
	
	@Override
	public void sendPacket(Player player) {
		PacketBuilder packet = new PacketBuilder(opcode(), type());
		
		packet.putString(message);
		
		player.getSession().getChannel().writeAndFlush(packet.toPacket());
	}

}
