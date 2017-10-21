package com.cerberus.player.serializing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.cerberus.model.Position;
import com.cerberus.player.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DeserializePlayer {

	public static void deserialize(Player player) throws FileNotFoundException, IOException {
		File file = new File("./data/players/"+player.getUsername().toLowerCase().replaceAll(" ", "_")+".json");
		
		if (!file.exists()) {
			return;
		}
		
		try (FileReader fileReader = new FileReader(file)) {
			JsonParser fileParser = new JsonParser();
			Gson builder = new GsonBuilder().create();
			JsonObject reader = (JsonObject) fileParser.parse(fileReader);
			
			player.setUsername(reader.get("username").getAsString());
			player.setPassword(reader.get("password").getAsString());
			player.setPlayerRights(reader.get("playerRights").getAsInt());
			player.setPosition(builder.fromJson(reader.get("position"), Position.class));
			
			fileReader.close();
		}

	}
}
