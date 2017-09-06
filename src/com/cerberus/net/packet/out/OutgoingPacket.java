package com.cerberus.net.packet.out;

import com.cerberus.net.packet.Packet.Type;
import com.cerberus.player.Player;

/**
 * An interface used to build outgoing packets
 * @author Lev Gazarov
 *
 */
public interface OutgoingPacket {
	public int opcode();
	
	public Type type();
	
	void sendPacket(Player player);
}
