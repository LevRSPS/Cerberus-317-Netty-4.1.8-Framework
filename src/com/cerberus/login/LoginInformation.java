package com.cerberus.login;

import com.cerberus.security.ISAACCipher;

/**
 * A class used to store information about a player's login
 * @author Lev Gazarov
 *
 */
public class LoginInformation {
	private int uid;
	
	private ISAACCipher decryptor;
	
	private ISAACCipher encryptor;
	
	private String username;
	
	private String password;
	
	private int response;
	
	private int playerRights;
	
	private int flagged;
	
	public LoginInformation(String username, String password, ISAACCipher decryptor, ISAACCipher encryptor, int response, int playerRights, int flagged) {
		this.setUsername(username);
		this.setPassword(password);
		this.setDecryptor(decryptor);
		this.setEncryptor(encryptor);
		this.setResponse(response);
		this.setPlayerRights(playerRights);
		this.setFlagged(flagged);
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public ISAACCipher getDecryptor() {
		return decryptor;
	}

	public void setDecryptor(ISAACCipher decryptor) {
		this.decryptor = decryptor;
	}

	public ISAACCipher getEncryptor() {
		return encryptor;
	}

	public void setEncryptor(ISAACCipher encryptor) {
		this.encryptor = encryptor;
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

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public int getPlayerRights() {
		return playerRights;
	}

	public void setPlayerRights(int playerRights) {
		this.playerRights = playerRights;
	}

	public int getFlagged() {
		return flagged;
	}

	public void setFlagged(int flagged) {
		this.flagged = flagged;
	}
}
