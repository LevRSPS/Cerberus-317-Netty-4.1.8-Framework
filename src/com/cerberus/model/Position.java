package com.cerberus.model;

/**
 * A class used to hold a 3D Position
 * @author Lev Gazarov
 *
 */
public class Position {
	private int x;
	
	private int y;
	
	private int z;
	
	public Position(int x, int y, int... z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z.length > 0 ? z[0] : 0);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
}
