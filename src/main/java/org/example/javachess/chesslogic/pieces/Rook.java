package org.example.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;

public class Rook extends Piece {
	public boolean rochade = true;

	public Rook(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	public Rook(boolean isWhite, int[] position, boolean rochade){
		super(isWhite, position);
		this.rochade = rochade;
	}


	@Override
	public Move[] getMoves() {

		List<Move> moves = new ArrayList<Move>();
		
		int i;
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
	public void makeMove(Move move){
		rochade = false;
		position[0] = move.destinationX;
		position[1] = move.destinationY;
		if (move.capture != null) {
			move.capture.position = null;
			Board.capturedPieces.add(move.capture);
			Board.pieces.remove(move.capture);
		}
	}


	@Override
	public Rook getClone() {
		return new Rook(isWhite, position, rochade);
	}

}
