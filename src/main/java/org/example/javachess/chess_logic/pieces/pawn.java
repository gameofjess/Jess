package org.example.javachess.chess_logic.pieces;

import org.example.javachess.chess_logic.board;
import org.example.javachess.chess_logic.move;

import java.util.ArrayList;
import java.util.List;


public class pawn extends piece {
	public boolean enpassant = false;

	public pawn(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	@Override
	public move[] getMoves() {
		List<move> moves = new ArrayList<move>();
		if(isWhite){

			piece eins_vor = board.getPosition(position[0], position[1] + 1);
			if (eins_vor == null) {
				moves.add(new move(position[0], position[1] + 1));
			}

			piece zwei_vor = board.getPosition(position[0], position[1] + 2);
			if (position[1] == 2 && eins_vor == null && zwei_vor == null) {
				moves.add(new move(position[0], position[1] + 2));
			}

			piece schlagen_rechts = board.getPosition(position[0]+1, position[1] + 1);
			if (schlagen_rechts != null && !schlagen_rechts.isWhite) {
				moves.add(new move(position[0]+1, position[1] + 1, schlagen_rechts));
			}

			piece schlagen_links = board.getPosition(position[0]-1, position[1] + 1);
			if (schlagen_links != null && !schlagen_links.isWhite) {
				moves.add(new move(position[0]-1, position[1] + 1, schlagen_links));
			}

			piece enpassant_rechts = board.getPosition(position[0]+1, position[1]);
			if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite && enpassant_rechts instanceof pawn && ((pawn) enpassant_rechts).enpassant) {
				moves.add(new move(position[0]+1, position[1] + 1, enpassant_rechts));
			}

			piece enpassant_links = board.getPosition(position[0]-1, position[1]);
			if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite && enpassant_links instanceof pawn && ((pawn) enpassant_links).enpassant) {
				moves.add(new move(position[0]-1, position[1] + 1, enpassant_rechts));
			}
		}
		else{

			piece eins_vor = board.getPosition(position[0], position[1] - 1);
			if (eins_vor == null) {
				moves.add(new move(position[0], position[1] - 1));
			}

			piece zwei_vor = board.getPosition(position[0], position[1] - 2);
			if (position[1] == 2 && eins_vor == null && zwei_vor == null) {
				moves.add(new move(position[0], position[1] - 2));
			}

			piece schlagen_rechts = board.getPosition(position[0]+1, position[1] - 1);
			if (schlagen_rechts != null && !schlagen_rechts.isWhite) {
				moves.add(new move(position[0]+1, position[1] - 1, schlagen_rechts));
			}

			piece schlagen_links = board.getPosition(position[0]-1, position[1] - 1);
			if (schlagen_links != null && !schlagen_links.isWhite) {
				moves.add(new move(position[0]-1, position[1] - 1, schlagen_links));
			}

			piece enpassant_rechts = board.getPosition(position[0]+1, position[1]);
			if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite && enpassant_rechts instanceof pawn && ((pawn) enpassant_rechts).enpassant) {
				moves.add(new move(position[0]+1, position[1] - 1, enpassant_rechts));
			}

			piece enpassant_links = board.getPosition(position[0]-1, position[1]);
			if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite && enpassant_links instanceof pawn && ((pawn) enpassant_links).enpassant) {
				moves.add(new move(position[0]-1, position[1] - 1, enpassant_rechts));
			}
		}
		return moves.toArray(new move[moves.size()]);
	}


}