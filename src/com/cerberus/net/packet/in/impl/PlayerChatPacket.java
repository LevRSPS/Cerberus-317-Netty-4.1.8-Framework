package com.cerberus.net.packet.in.impl;

import com.cerberus.net.packet.Packet;
import com.cerberus.net.packet.in.HandlePacket;
import com.cerberus.player.Player;

/**
 * An implementation of the {@link HandlePacket} interface used to handle the chat packet
 * @author Lev Gazarov
 *
 */
@SuppressWarnings("unused")
public class PlayerChatPacket implements HandlePacket {

	@Override
	public void handlePacket(Player player, Packet packet) {
		int ignoreByte = packet.readUnsignedByte();
		int effect = packet.readByteS();
		int color = packet.readByteS();
		
		String chat = packet.readString();
		
		System.out.println(player.getUsername()+" is saying: "+chat);
	}

}
