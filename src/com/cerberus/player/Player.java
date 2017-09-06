package com.cerberus.player;

import java.util.ArrayList;
import java.util.Collection;

import com.cerberus.model.Flags;
import com.cerberus.model.Position;
import com.cerberus.net.packet.out.OutgoingPacket;
import com.cerberus.player.io.Session;

/**
 * A class that represents a player
 * @author Lev Gazarov
 *
 */
public class Player {
	private Session session;
	
	private String username;
	
	private String password;
	
	private int playerRights;
	
	private Position position; 
	
	private Collection<Flags> updateFlags = new ArrayList<>();

	
	public Player(Session session) {
		this.setSession(session);
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPlayerRights() {
		return playerRights;
	}

	public void setPlayerRights(int playerRights) {
		this.playerRights = playerRights;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Collection<Flags> getUpdateFlags() {
		return updateFlags;
	}
	
	public void sendPacket(OutgoingPacket packet) {
		if (packet == null) {
			return;
		}
		
		packet.sendPacket(this);
	}
}
