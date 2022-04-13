package org.example.javachess.chess_logic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chess_logic.Board;
import org.example.javachess.chess_logic.Move;

public class Rook extends Piece {
	public boolean rochade = true;

	public Rook(boolean isWhite, int[] position){
		super(isWhite, position);
	}


	@Override
	public Move[] getMoves() {
		List<Move> moves = new ArrayList<Move>();

		//rechts
		for (int i = position[0]+1; i < 8; i++) {
			Piece test_position = Board.getPosition(i, position[1]);
			if (test_position != null && test_position.isWhite == isWhite) {
				break;
			}
			if (test_position == null || test_position.isWhite != isWhite) {
				moves.add(new Move(i, position[1], test_position));
			}
		}
		//links
		for (int i = position[0]-1; i >= 0; i--) {
			Piece test_position = Board.getPosition(i, position[1]);
			if (test_position != null && test_position.isWhite == isWhite) {
				break;
			}
			if (test_position == null || test_position.isWhite != isWhite) {
				moves.add(new Move(i, position[1], test_position));
			}
		}
		//hoch
		for (int i = position[1]; i < 8; i++) {
			Piece test_position = Board.getPosition(position[0], i);
			if (test_position != null && test_position.isWhite == isWhite) {
				break;
			}
			if (test_position == null || test_position.isWhite != isWhite) {
				moves.add(new Move(position[0], i, test_position));
			}
		}
		//runter
		for (int i = position[1]; i >= 0; i--) {
			Piece test_position = Board.getPosition(position[0], i);
			if (test_position != null && test_position.isWhite == isWhite) {
				break;
			}
			if (test_position == null || test_position.isWhite != isWhite) {
				moves.add(new Move(position[0], i, test_position));
			}
		}
		return moves.toArray(new Move[moves.size()]);
	}
}
