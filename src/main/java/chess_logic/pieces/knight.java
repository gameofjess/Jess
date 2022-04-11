package chess_logic.pieces;

import java.util.ArrayList;
import java.util.List;

import chess_logic.board;
import chess_logic.move;

public class knight extends piece {
	public knight(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	@Override
	public move[] getMoves() {
		List<move> moves = new ArrayList<move>();
		//hoch rechts
		if (position[0] < 7 && position[1] < 6 && board.getPosition(position[0] +1 , position[1] + 2) == null) {
			moves.add(new move(position[0] +1, position[1] + 2));
		}
		if (position[0] < 7 && position[1] < 6 && board.getPosition(position[0] +1 , position[1] + 2).isWhite != isWhite) {
			moves.add(new move(position[0] +1, position[1] + 2, board.getPosition(position[0] +1 , position[1] + 2)));
		}
		//rechts hoch
		if (position[0] < 6 && position[1] < 7 && board.getPosition(position[0] +2 , position[1] + 1) == null) {
			moves.add(new move(position[0] +2, position[1] + 1));
		}
		if (position[0] < 6 && position[1] < 7 && board.getPosition(position[0] +2 , position[1] + 1).isWhite != isWhite) {
			moves.add(new move(position[0] +2, position[1] + 1, board.getPosition(position[0] +2 , position[1] + 1)));
		}
		//rechts runter
		if (position[0] < 6 && position[1] > 0 && board.getPosition(position[0] +2 , position[1] - 1) == null) {
			moves.add(new move(position[0] +2, position[1] - 1));
		}
		if (position[0] < 6 && position[1] > 0 && board.getPosition(position[0] +2 , position[1] - 1).isWhite != isWhite) {
			moves.add(new move(position[0] +2, position[1] - 1, board.getPosition(position[0] +2 , position[1] - 1)));
		}
		//runter rechts
		if (position[0] < 7 && position[1] > 1 && board.getPosition(position[0] +1 , position[1] - 2) == null) {
			moves.add(new move(position[0] +1, position[1] - 2));
		}
		if (position[0] < 7 && position[1] > 1 && board.getPosition(position[0] +1 , position[1] - 2).isWhite != isWhite) {
			moves.add(new move(position[0] +1, position[1] - 2, board.getPosition(position[0] +1 , position[1] - 2)));
		}
		//runter links
		if (position[0] > 0 && position[1] > 1 && board.getPosition(position[0] -1 , position[1] - 2) == null) {
			moves.add(new move(position[0] -1, position[1] - 2));
		}
		if (position[0] > 0 && position[1] > 1 && board.getPosition(position[0] -1 , position[1] - 2).isWhite != isWhite) {
			moves.add(new move(position[0] -1, position[1] - 2, board.getPosition(position[0] -1 , position[1] - 2)));
		}
		//Links runter
		if (position[0] > 1 && position[1] > 0 && board.getPosition(position[0] -2 , position[1] - 1) == null) {
			moves.add(new move(position[0] -2, position[1] - 1));
		}
		if (position[0] > 1 && position[1] > 0 && board.getPosition(position[0] -2 , position[1] - 1).isWhite != isWhite) {
			moves.add(new move(position[0] -2, position[1] - 1, board.getPosition(position[0] -2 , position[1] - 1)));
		}
		//links hoch
		if (position[0] > 1 && position[1] < 7 && board.getPosition(position[0] -2 , position[1] + 1) == null) {
			moves.add(new move(position[0] -2, position[1] + 1));
		}
		if (position[0] > 1 && position[1] < 7 && board.getPosition(position[0] -2 , position[1] + 1).isWhite != isWhite) {
			moves.add(new move(position[0] -2, position[1] + 1, board.getPosition(position[0] -2 , position[1] + 1)));
		}
		//hoch links
		if (position[0] > 0&& position[1] < 6 && board.getPosition(position[0] -1 , position[1] + 2) == null) {
			moves.add(new move(position[0] -1, position[1] + 2));
		}
		if (position[0] > 0 && position[1] < 6 && board.getPosition(position[0] -1 , position[1] + 2).isWhite != isWhite) {
			moves.add(new move(position[0] -1, position[1] + 2, board.getPosition(position[0] -1 , position[1] + 2)));
		}
		return moves.toArray(new move[moves.size()]);
	}
}
