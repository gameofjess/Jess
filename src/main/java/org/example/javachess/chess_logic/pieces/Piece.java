package org.example.javachess.chess_logic.pieces;

import org.example.javachess.chess_logic.Move;

public abstract class Piece {

	public boolean isWhite;
	public int[] position = new int[2];

	public Piece(boolean isWhite, int[] position){
		this.isWhite = isWhite;
		this.position = position;
	}

	public abstract Move[] getMoves();
}
