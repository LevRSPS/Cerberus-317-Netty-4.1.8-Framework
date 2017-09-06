package com.cerberus.util;

import com.cerberus.player.io.Session;

import io.netty.util.AttributeKey;

/**
 * Constants for the Cerberus framework
 * @author Lev Gazarov
 *
 */
public class Constants {
	public static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("sessionKey");
	
	public static final int PORT = 43594;
}
