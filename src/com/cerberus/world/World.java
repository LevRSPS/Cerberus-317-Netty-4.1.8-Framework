package com.cerberus.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.cerberus.net.packet.in.PacketHandler;
import com.cerberus.player.Player;
import com.cerberus.player.serializing.SerializePlayer;
import com.cerberus.player.updating.PlayerUpdating;

/**
 * A class that represents the whole server (players, player that need to be removed, etc)
 * @author Lev Gazarov
 *
 */
public class World {
	public static final List<Player> players = new ArrayList<>();
	
	public static final Queue<Player> toRemove = new ConcurrentLinkedQueue<>();
	
	public static void tick() {
		
		Iterator<Player> $it = toRemove.iterator();
		while ($it.hasNext()) {
			Player player = $it.next();
			if (player == null) {
				break;
			}
			
			try {
				new SerializePlayer(player).serialize();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			players.remove(player);
			$it.remove();
		}
		
		PlayerUpdating.updatePlayers();
		
		for (Player player : players) {
			if (player == null) {
				continue;
			}
			
			PacketHandler.handlePackets(player);
			//player.getPacketSender().sendMessage("Welcome to Cerberus 317!");
			//TODO: Process queued packets
		}
	}
}
