package chess_logic.pieces;

import java.util.ArrayList;
import java.util.List;

import chess_logic.board;

public class pawn extends piece {
	public pawn(boolean isWhite, int[] position){
		super(isWhite, position);
	}

	@Override
	public int[][] getMoves() {
		List<int[]> moves = new ArrayList<int[]>();
		if(isWhite){
			//2 vor weiß
			if( position[1] == 1 && board.getPosition(position[0], position[1] + 2) == null) moves.add(new int[] {position[0], position[1]+2});
			//1 vor weiß
			if( board.getPosition(position[0], position[1] + 1) == null) moves.add(new int[] {position[0], position[1]+1});
			//schlagen rechts
			if( board.getPosition(position[0]+1, position[1] + 1) != null && !board.getPosition(position[0]+1, position[1] + 1).isWhite) moves.add(new int[] {position[0]+1, position[1]+1});
			//schlagen links
			if( board.getPosition(position[0]-1, position[1] + 1) != null && !board.getPosition(position[0]-1, position[1] + 1).isWhite) moves.add(new int[] {position[0]-1, position[1]+1});
		}
		else{
			//2 vor schwarz
			if( position[1] == 6 && board.getPosition(position[0], position[1] - 2) == null) moves.add(new int[] {position[0], position[1]-2});
			//1 vor schwarz
			if( board.getPosition(position[0], position[1] - 1) == null) moves.add(new int[] {position[0], position[1]-1});
			//schlagen rechts
			if( board.getPosition(position[0]+1, position[1] - 1) != null && board.getPosition(position[0]+1, position[1] + 1).isWhite) moves.add(new int[] {position[0]+1, position[1]-1});
			//schlagen links
			if( board.getPosition(position[0]-1, position[1] - 1) != null && board.getPosition(position[0]-1, position[1] + 1).isWhite) moves.add(new int[] {position[0]-1, position[1]-1});
		}
		return moves.toArray(new int[moves.size()][2]);
	}

	
}
