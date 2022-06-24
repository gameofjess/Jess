package com.gameofjess.javachess.chesslogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Position {
	/**
	 * This Class represents a position on the Chessboard
	 */
	private static final Logger log = LogManager.getLogger(Position.class);
	private final byte x;
	private final byte y;

	/**
	 * constructor
	 * @param x value of the position between 0 and 7
	 * @param y value of the position between 0 and 7
	 */
	public Position(int x, int y) {
		log.trace("Create Position object");
		this.x = (byte)x;
		this.y = (byte)y;
	}

	/**
	 * @return clone of the position object
	 */
	public Position getClone(){
		return new Position(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Position position))
			return false;
		return x == position.x && y == position.y;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 64 * result + y;
		return result;
	}

	/**
	 * @return the x value of the position
	 */
	public byte getX() {
		return x;
	}
	/**
	 * @return the y value of the position
	 */
	public byte getY() {
		return y;
	}

	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
