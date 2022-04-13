package org.example.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;

public class King extends Piece {
	public boolean rochade = true;

	public King(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	@Override
	public Move[] getMoves() {
		List<Move> moves = new ArrayList<Move>();

		Piece test;
		//hoch
		test = Board.getPosition(position[0], position[1] + 1);
		if (position[1] + 1 < 8 && (test == null || test.isWhite != isWhite)) {
			moves.add(new Move(position[0], position[1] + 1, test));
		}
		//hoch rechts
		test = Board.getPosition(position[0] + 1, position[1] + 1);
		if (position[0] + 1 < 8 && position[1] + 1 < 8 && (test == null || test.isWhite != isWhite)) {
			moves.add(new Move(position[0] + 1, position[1] + 1, test));
		}
		//rechts
		test = Board.getPosition(position[0] + 1 , position[1]);
		if (position[0] + 1 < 8 && (test == null || test.isWhite != isWhite)) {
			moves.add(new Move(position[0] + 1, position[1], test));
		}
		//runter rechts
		test = Board.getPosition(position[0] + 1, position[1] - 1);
		if (position[0] + 1 < 8 && position[1] - 1 >= 0 && (test == null || test.isWhite != isWhite)) {
			moves.add(new Move(position[0] + 1, position[1] - 1, test));
		}
		//runter
		test = Board.getPosition(position[0], position[1] - 1);
		if (position[1] - 1 >= 0 && (test == null || test.isWhite != isWhite)) {
			moves.add(new Move(position[0], position[1] - 1, test));
		}
		//runter links
		test = Board.getPosition(position[0] - 1, position[1] - 1);
		if (position[0] - 1 >= 0 && position[1] - 1 >= 0 && (test == null || test.isWhite != isWhite)) {
			moves.add(new Move(position[0] - 1, position[1] - 1, test));
		}
		//links
		test = Board.getPosition(position[0] - 1, position[1]);
		if (position[0] - 1 >= 0 && (test == null || test.isWhite != isWhite)) {
			moves.add(new Move(position[0] - 1, position[1], test));
		}
		//hoch links
		test = Board.getPosition(position[0] - 1, position[1] + 1);
		if (position[0] - 1 >= 0 && position[1] + 1 < 8 && (test == null || test.isWhite != isWhite)) {
			moves.add(new Move(position[0] - 1, position[1] + 1, test));
		}

		if (rochade && Board.getPosition(5, position[1]) == null && Board.getPosition(6, position[1]) == null) {
			moves.add(new Move(7, position[1], true));
		}

		if (rochade && Board.getPosition(1, position[1]) == null && Board.getPosition(2, position[1]) == null && Board.getPosition(3, position[1]) == null) {
			moves.add(new Move(0, position[1], true));
		}

		return moves.toArray(new Move[moves.size()]);
	}
}
