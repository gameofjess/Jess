package org.example.javachess.chesslogic.pieces;

import org.example.javachess.chesslogic.Move;

public abstract class Piece {

	public boolean isWhite;
	public int[] position = new int[2];

	public Piece(boolean isWhite, int[] position){
		this.isWhite = isWhite;
		this.position = position;
	}

	@Override
	public String toString(){
		return ((isWhite) ? "White " : "Black ") + String.format("%s: X=%d Y=%d", this.getClass().getSimpleName(), position[0], position[1]);  
	}

	public abstract Move[] getMoves();
}
