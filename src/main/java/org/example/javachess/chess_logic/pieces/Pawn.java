package org.example.javachess.chess_logic.pieces;

import org.example.javachess.chess_logic.Board;
import org.example.javachess.chess_logic.Move;

import java.util.ArrayList;
import java.util.List;


public class Pawn extends Piece {
	public boolean enpassant = false;

	public Pawn(boolean isWhite, int[] position){
		super(isWhite, position);
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
				moves.add(new Move(position[0], position[1] - 2));
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


}