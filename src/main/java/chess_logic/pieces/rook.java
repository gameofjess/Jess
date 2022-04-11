package chess_logic.pieces;

import java.util.ArrayList;
import java.util.List;

import chess_logic.board;
import chess_logic.move;

public class rook extends piece {
	public rook(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	@Override
	public move[] getMoves() {
		List<move> moves = new ArrayList<move>();

		//rechts
		for (int i = position[0]+1; i < 8; i++) {
			piece test_position = board.getPosition(i, position[1]);
			if (test_position != null && test_position.isWhite == isWhite) {
				break;
			}
			if (test_position == null || test_position.isWhite != isWhite) {
				moves.add(new move(i, position[1], test_position));
			}
		}
		//links
		for (int i = position[0]-1; i >= 0; i--) {
			piece test_position = board.getPosition(i, position[1]);
			if (test_position != null && test_position.isWhite == isWhite) {
				break;
			}
			if (test_position == null || test_position.isWhite != isWhite) {
				moves.add(new move(i, position[1], test_position));
			}
		}
		//hoch
		for (int i = position[1]; i < 8; i++) {
			piece test_position = board.getPosition(position[0], i);
			if (test_position != null && test_position.isWhite == isWhite) {
				break;
			}
			if (test_position == null || test_position.isWhite != isWhite) {
				moves.add(new move(position[0], i, test_position));
			}
		}
		//runter
		for (int i = position[1]; i >= 0; i--) {
			piece test_position = board.getPosition(position[0], i);
			if (test_position != null && test_position.isWhite == isWhite) {
				break;
			}
			if (test_position == null || test_position.isWhite != isWhite) {
				moves.add(new move(position[0], i, test_position));
			}
		}
		return moves.toArray(new move[moves.size()]);
	}
}
