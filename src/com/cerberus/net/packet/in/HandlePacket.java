package com.cerberus.net.packet.in;

import com.cerberus.net.packet.Packet;
import com.cerberus.player.Player;

/**
 * An interface that will be used to handle incoming packets
 * @author Lev Gazarov
 *
 */
public interface HandlePacket {

	void handlePacket(Player player, Packet packet);

}
