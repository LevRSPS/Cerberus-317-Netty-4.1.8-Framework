package com.cerberus.net.packet.in;

import com.cerberus.net.packet.Packet;
import com.cerberus.net.packet.in.impl.PlayerChatPacket;
import com.cerberus.player.Player;

/**
 * A class used to handle all queued packets for a {@link Player}
 * @author Lev Gazarov
 *
 */
public class PacketHandler {
	private static HandlePacket[] handlers = new HandlePacket[255];
	
	static {
		handlers[4] = new PlayerChatPacket();
	}
	
	public static HandlePacket getPacket(int packetId) {
		if (packetId > 255) {
			return null;
		} else {
			return handlers[packetId];
		}
	}
	
	public static void handlePackets(Player player) {
		if (player.getSession().getQueuedPackets().size() <= 0) {
			return;
		}
		
		for (Packet packet : player.getSession().getQueuedPackets()) {
			if (packet.getOpcode() <= 0) {
				continue;
			}
			
			HandlePacket handler = getPacket(packet.getOpcode());
			
			if (handler == null) {
				continue;
			}
			
			handler.handlePacket(player, packet);
		}
		
		player.getSession().getQueuedPackets().clear();
	}
}
