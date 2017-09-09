package com.cerberus.player.io;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.cerberus.login.LoginInformation;
import com.cerberus.model.Flags;
import com.cerberus.model.Position;
import com.cerberus.net.codec.packet.PacketDecoder;
import com.cerberus.net.codec.packet.PacketEncoder;
import com.cerberus.net.packet.Packet;
import com.cerberus.net.packet.out.impl.MapRegion;
import com.cerberus.net.packet.out.impl.Message;
import com.cerberus.player.Player;
import com.cerberus.world.World;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

/**
 * A session of a player that includes queued packets, the players channel, and the player themselves
 * @author Lev Gazarov
 *
 */
public class Session {
	private Channel channel;
	
	private Player player;
	
	private Queue<Packet> queuedPackets = new ConcurrentLinkedQueue<>();
	
	public Session(Channel channel) {
		this.setChannel(channel);
		this.setPlayer(new Player(this));
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Queue<Packet> getQueuedPackets() {
		return queuedPackets;
	}

	public Player getPlayer() {
		return player;
	}
	
	public void finish(LoginInformation packet) {
		player.setUsername(packet.getUsername());
		player.setPassword(packet.getPassword());
		player.setPlayerRights(packet.getPlayerRights());
		player.setPosition(new Position(3220, 3220));//hmmmm
		
		ByteBuf login = Unpooled.buffer();
		login.writeByte(packet.getResponse());
		login.writeByte(packet.getPlayerRights());
		login.writeByte(packet.getFlagged());
		channel.writeAndFlush(login);
		
		if (!World.players.contains(player))
		World.players.add(player);
		
		channel.pipeline().replace("decoder", "decoder", new PacketDecoder());
		channel.pipeline().addLast("encoder", new PacketEncoder());
		
		player.sendPacket(new Message("Welcome to Cerberus 317!"));	
		player.sendPacket(new MapRegion(player.getPosition()));
		player.setPositionUpdate(true);
		player.getUpdateFlags().add(Flags.APPEARANCE);
		
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
