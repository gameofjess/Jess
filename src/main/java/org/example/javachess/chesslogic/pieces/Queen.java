package org.example.javachess.chesslogic.pieces;


import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;

public class Queen extends Piece {
	public Queen(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	@Override
	public Move[] getMoves() {
		List<Move> moves = new ArrayList<Move>();
		
		int i;
		//hoch rechts
		i = 1;
		while (position[0] + i < 8 && position[1] + i < 8 && (Board.getPosition(position[0] + i, position[1] + i) == null || Board.getPosition(position[0] + i, position[1] + i).isWhite != isWhite )) {
			moves.add(new Move(position[0] + i, position[1] + i, Board.getPosition(position[0] + i, position[1] + i)));
			i++;
		}
		//runter rechts
		i = 1;
		while (position[0] + i < 8 && position[1] - i >= 0 && (Board.getPosition(position[0] + i, position[1] - i) == null || Board.getPosition(position[0] + i, position[1] - i).isWhite != isWhite )) {
			moves.add(new Move(position[0] + i, position[1] - i, Board.getPosition(position[0] + i, position[1] - i)));
			i++;
		}
		//runter links
		i = 1;
		while (position[0] - i >= 0 && position[1] - i >= 0 && (Board.getPosition(position[0] - i, position[1] - i) == null || Board.getPosition(position[0] - i, position[1] - i).isWhite != isWhite )) {
			moves.add(new Move(position[0] - i, position[1] - i, Board.getPosition(position[0] - i, position[1] - i)));
			i++;
		}
		//hoch links
		i = 1;
		while (position[0] - i >= 0 && position[1] + i < 8 && (Board.getPosition(position[0] - i, position[1] + i) == null || Board.getPosition(position[0] - i, position[1] + i).isWhite != isWhite )) {
			moves.add(new Move(position[0] - i, position[1] + i, Board.getPosition(position[0] - i, position[1] + i)));
			i++;
		}
		//rechts
		i = 1;
		while (position[0] + i < 8 && (Board.getPosition(position[0] + i, position[1]) == null || Board.getPosition(position[0] + i, position[1]).isWhite != isWhite )) {
			moves.add(new Move(position[0] + i, position[1], Board.getPosition(position[0] + i, position[1])));
			i++;
		}
		//links
		i = 1;
		while (position[0] - i >= 0 && (Board.getPosition(position[0] - i, position[1]) == null || Board.getPosition(position[0] - i, position[1]).isWhite != isWhite )) {
			moves.add(new Move(position[0] - i, position[1], Board.getPosition(position[0] - i, position[1])));
			i++;
		}
		//hoch
		i = 1;
		while (position[1] + i < 8 && (Board.getPosition(position[0], position[1] + i) == null || Board.getPosition(position[0], position[1] + i).isWhite != isWhite )) {
			moves.add(new Move(position[0], position[1] + i, Board.getPosition(position[0] - i, position[1] + i)));
			i++;
		}
		//runter
		i = 1;
		while (position[1] - i >= 0 && (Board.getPosition(position[0], position[1] - i) == null || Board.getPosition(position[0], position[1] - i).isWhite != isWhite )) {
			moves.add(new Move(position[0], position[1] - i, Board.getPosition(position[0], position[1] - i)));
			i++;
		}
		return moves.toArray(new Move[moves.size()]);
	}

	@Override
	public Queen getClone() {
		return new Queen(isWhite, position);
	}
}
