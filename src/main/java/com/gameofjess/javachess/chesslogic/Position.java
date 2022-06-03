package com.gameofjess.javachess.chesslogic;

public class Position {
	/**
	 * This Class represents a position on the Chessboard
	 */
	public final int x;
	public final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
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

}
