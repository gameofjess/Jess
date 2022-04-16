package org.example.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;

public class Knight extends Piece {
	public Knight(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	@Override
	public Move[] getMoves() {
		List<Move> moves = new ArrayList<Move>();
		//hoch rechts
		if (position[0] < 7 && position[1] < 6 && Board.getPosition(position[0] +1 , position[1] + 2) == null) {
			moves.add(new Move(position[0] +1, position[1] + 2));
		}
		if (position[0] < 7 && position[1] < 6 && Board.getPosition(position[0] +1 , position[1] + 2).isWhite != isWhite) {
			moves.add(new Move(position[0] +1, position[1] + 2, Board.getPosition(position[0] +1 , position[1] + 2)));
		}
		//rechts hoch
		if (position[0] < 6 && position[1] < 7 && Board.getPosition(position[0] +2 , position[1] + 1) == null) {
			moves.add(new Move(position[0] +2, position[1] + 1));
		}
		if (position[0] < 6 && position[1] < 7 && Board.getPosition(position[0] +2 , position[1] + 1).isWhite != isWhite) {
			moves.add(new Move(position[0] +2, position[1] + 1, Board.getPosition(position[0] +2 , position[1] + 1)));
		}
		//rechts runter
		if (position[0] < 6 && position[1] > 0 && Board.getPosition(position[0] +2 , position[1] - 1) == null) {
			moves.add(new Move(position[0] +2, position[1] - 1));
		}
		if (position[0] < 6 && position[1] > 0 && Board.getPosition(position[0] +2 , position[1] - 1).isWhite != isWhite) {
			moves.add(new Move(position[0] +2, position[1] - 1, Board.getPosition(position[0] +2 , position[1] - 1)));
		}
		//runter rechts
		if (position[0] < 7 && position[1] > 1 && Board.getPosition(position[0] +1 , position[1] - 2) == null) {
			moves.add(new Move(position[0] +1, position[1] - 2));
		}
		if (position[0] < 7 && position[1] > 1 && Board.getPosition(position[0] +1 , position[1] - 2).isWhite != isWhite) {
			moves.add(new Move(position[0] +1, position[1] - 2, Board.getPosition(position[0] +1 , position[1] - 2)));
		}
		//runter links
		if (position[0] > 0 && position[1] > 1 && Board.getPosition(position[0] -1 , position[1] - 2) == null) {
			moves.add(new Move(position[0] -1, position[1] - 2));
		}
		if (position[0] > 0 && position[1] > 1 && Board.getPosition(position[0] -1 , position[1] - 2).isWhite != isWhite) {
			moves.add(new Move(position[0] -1, position[1] - 2, Board.getPosition(position[0] -1 , position[1] - 2)));
		}
		//Links runter
		if (position[0] > 1 && position[1] > 0 && Board.getPosition(position[0] -2 , position[1] - 1) == null) {
			moves.add(new Move(position[0] -2, position[1] - 1));
		}
		if (position[0] > 1 && position[1] > 0 && Board.getPosition(position[0] -2 , position[1] - 1).isWhite != isWhite) {
			moves.add(new Move(position[0] -2, position[1] - 1, Board.getPosition(position[0] -2 , position[1] - 1)));
		}
		//links hoch
		if (position[0] > 1 && position[1] < 7 && Board.getPosition(position[0] -2 , position[1] + 1) == null) {
			moves.add(new Move(position[0] -2, position[1] + 1));
		}
		if (position[0] > 1 && position[1] < 7 && Board.getPosition(position[0] -2 , position[1] + 1).isWhite != isWhite) {
			moves.add(new Move(position[0] -2, position[1] + 1, Board.getPosition(position[0] -2 , position[1] + 1)));
		}
		//hoch links
		if (position[0] > 0&& position[1] < 6 && Board.getPosition(position[0] -1 , position[1] + 2) == null) {
			moves.add(new Move(position[0] -1, position[1] + 2));
		}
		if (position[0] > 0 && position[1] < 6 && Board.getPosition(position[0] -1 , position[1] + 2).isWhite != isWhite) {
			moves.add(new Move(position[0] -1, position[1] + 2, Board.getPosition(position[0] -1 , position[1] + 2)));
		}
		return moves.toArray(new Move[moves.size()]);
	}

	@Override
	public Knight getClone() {
		return new Knight(isWhite, position);
	}
}
