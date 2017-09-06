package com.cerberus.util;

import io.netty.buffer.ByteBuf;

/**
 * ByteBufUtils class from Hyperion
 * @author Graham Edgecombe
 *
 */
public class ByteBufUtils {
	/**
	 * Gets an RS2 string from the buffer.
	 * @param buf The buffer.
	 * @return The RS2 string.
	 */
	public static String getString(ByteBuf buf) {
		StringBuilder bldr = new StringBuilder();
		char c;
		while(buf.isReadable() && (c = (char) buf.readUnsignedByte()) != 10) {
			bldr.append(c);
		}
		return bldr.toString();
	}
	
	/**
	 * Converts a name to a long value.
	 * @param string 	The string to convert to long.
	 * @return 			The long value of the string.
	 */
	public static long stringToLong(String string) {
		long l = 0L;
		for(int i = 0; i < string.length() && i < 12; i++) {
			char c = string.charAt(i);
			l *= 37L;
			if(c >= 'A' && c <= 'Z')
				l += (1 + c) - 65;
			else if(c >= 'a' && c <= 'z')
				l += (1 + c) - 97;
			else if(c >= '0' && c <= '9')
				l += (27 + c) - 48;
		}
		while(l % 37L == 0L && l != 0L)
			l /= 37L;
		return l;
	}
}