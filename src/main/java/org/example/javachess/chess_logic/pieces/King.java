package org.example.javachess.chess_logic.pieces;

import org.example.javachess.chess_logic.Move;

public class King extends Piece {
	public boolean rochade = true;

	public King(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	@Override
	public Move[] getMoves() {
		// TODO Auto-generated method stub
		return null;
	}
}
