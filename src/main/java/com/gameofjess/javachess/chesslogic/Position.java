package com.gameofjess.javachess.chesslogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Position {
	/**
	 * This Class represents a position on the Chessboard
	 */
	private static final Logger log = LogManager.getLogger(Position.class);
	final int x;
	final int y;

	public Position(int x, int y) {
		//log.debug("Creating Position Object ({},{})", x, y);
		this.x = x;
		this.y = y;
	}

	public Position getClone(){
		//log.debug("Cloning Position Object ({},{})", x, y);
		return new Position(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Position))
			return false;
		Position position = (Position) o;
		return x == position.x && y == position.y;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 64 * result + y;
		return result;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
