package com.cerberus.player.serializing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.cerberus.player.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class SerializePlayer {
	/**
	 * GsonBuilder instance
	 */
	private static final GsonBuilder BUILDER = new GsonBuilder();

	/**
	 * Gson instance
	 */
	private final static Gson gson = BUILDER.setPrettyPrinting().create();

	private Player player;

	public SerializePlayer(Player player) {
		this.player = player;
	}

	public void serialize() throws IOException {

		if (player == null) {
			return;
		}
		try {
			File file = new File("./data/players/" + player.getUsername().toLowerCase().replaceAll(" ", "_")+".json");

			if (file.exists()) {
				file.delete();
			}

			file.createNewFile();

			FileWriter writer = new FileWriter(file);
			
			JsonObject object = new JsonObject();
			
			object.addProperty("username", player.getUsername());
			object.addProperty("password", player.getPassword());
			object.addProperty("playerRights", player.getPlayerRights());
			object.add("position", gson.toJsonTree(player.getPosition()));
			writer.write(gson.toJson(object));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
