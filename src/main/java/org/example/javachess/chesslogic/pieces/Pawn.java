package org.example.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;


public class Pawn extends Piece {
	public boolean enpassant = false;

	public Pawn(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	public Pawn(boolean isWhite, int[] position, boolean enpassant){
		super(isWhite, position);
		this.enpassant = enpassant;
	}

	@Override
	public Move[] getMoves() {
		List<Move> moves = new ArrayList<Move>();
		if(isWhite){

			Piece eins_vor = Board.getPosition(position[0], position[1] + 1);
			if (eins_vor == null) {
				moves.add(new Move(position[0], position[1] + 1));
			}

			Piece zwei_vor = Board.getPosition(position[0], position[1] + 2);
			if (position[1] == 2 && eins_vor == null && zwei_vor == null) {
				moves.add(new Move(position[0], position[1] + 2));
			}

			Piece schlagen_rechts = Board.getPosition(position[0]+1, position[1] + 1);
			if (schlagen_rechts != null && !schlagen_rechts.isWhite) {
				moves.add(new Move(position[0]+1, position[1] + 1, schlagen_rechts));
			}

			Piece schlagen_links = Board.getPosition(position[0]-1, position[1] + 1);
			if (schlagen_links != null && !schlagen_links.isWhite) {
				moves.add(new Move(position[0]-1, position[1] + 1, schlagen_links));
			}

			Piece enpassant_rechts = Board.getPosition(position[0]+1, position[1]);
			if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
				moves.add(new Move(position[0]+1, position[1] + 1, enpassant_rechts));
			}

			Piece enpassant_links = Board.getPosition(position[0]-1, position[1]);
			if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
				moves.add(new Move(position[0]-1, position[1] + 1, enpassant_rechts));
			}
		}
		else{

			Piece eins_vor = Board.getPosition(position[0], position[1] - 1);
			if (eins_vor == null) {
				moves.add(new Move(position[0], position[1] - 1));
			}

			Piece zwei_vor = Board.getPosition(position[0], position[1] - 2);
			if (position[1] == 2 && eins_vor == null && zwei_vor == null) {
				moves.add(new Move(true, position[0], position[1] - 2));
			}

			Piece schlagen_rechts = Board.getPosition(position[0]+1, position[1] - 1);
			if (schlagen_rechts != null && !schlagen_rechts.isWhite) {
				moves.add(new Move(position[0]+1, position[1] - 1, schlagen_rechts));
			}

			Piece schlagen_links = Board.getPosition(position[0]-1, position[1] - 1);
			if (schlagen_links != null && !schlagen_links.isWhite) {
				moves.add(new Move(position[0]-1, position[1] - 1, schlagen_links));
			}

			Piece enpassant_rechts = Board.getPosition(position[0]+1, position[1]);
			if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
				moves.add(new Move(position[0]+1, position[1] - 1, enpassant_rechts));
			}

			Piece enpassant_links = Board.getPosition(position[0]-1, position[1]);
			if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
				moves.add(new Move(position[0]-1, position[1] - 1, enpassant_rechts));
			}
		}
		return moves.toArray(new Move[moves.size()]);
	}

	@Override
	public void makeMove(Move move){
		enpassant = move.enpassant;
		position[0] = move.destinationX;
		position[1] = move.destinationY;
		if (move.capture != null) {
			move.capture.position = null;
			Board.capturedPieces.add(move.capture);
			Board.pieces.remove(move.capture);
		}
	}

	@Override
	public Pawn getClone() {
		return new Pawn(isWhite, position, enpassant);
	}
}