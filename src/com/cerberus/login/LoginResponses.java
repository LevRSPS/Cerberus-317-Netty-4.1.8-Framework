package com.cerberus.login;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.cerberus.Server;
import com.cerberus.player.Player;
import com.cerberus.world.World;
import com.google.gson.Gson;

public enum LoginResponses {
	SUCCESS(2), INVALID_USER_OR_PASS(3), BANNED(4), LOGGED_IN(5), UPDATED(6), FULL(7), LOGIN_SERVER_OFFLINE(
			8), LOGIN_LIMIT_EXCEEDED(9), UPDATING(14);

	private int response;

	private LoginResponses(int response) {
		this.setResponse(response);
	}
	
	public static LoginResponses getResponse(LoginInformation info) throws FileNotFoundException {
		if (Server.serverUpdating) {
			return UPDATING;
		}
		
		if (World.players.size() >= Server.MAX_PLAYERS) {
			return FULL;
		}
		
		if (new File("./data/players/"+info.getUsername().toLowerCase().replaceAll(" ", "_")+".json").exists()) {
			File character = new File("./data/players/"+info.getUsername().replaceAll(" ", "_")+".json");
			
			FileReader reader = new FileReader(character);
			
			Player player = new Gson().fromJson(reader, Player.class);
			
			if (!player.getPassword().equalsIgnoreCase(info.getPassword())) {
				return INVALID_USER_OR_PASS;
			}
		}
		
		
		return SUCCESS;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}
}
