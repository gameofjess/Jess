package com.gameofjess.javachess.chesslogic;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gameofjess.javachess.chesslogic.pieces.Piece;

public class playground {
    public static void main(String[] args) {
		Random rand = new Random();
        Board board = new Board();
		board.initialize();
		board.print();
		System.out.println("\n");
		for (int i = 0; i < 10; i++) {
			List<Piece> pieces = new ArrayList<>(board.board.values());
			Piece piece = null;
			Move[] moves = null;
			Move move = null;
			while(move == null){
				piece = pieces.get(rand.nextInt(pieces.size()));
				moves = piece.getMoves();
				if (moves.length > 0) {
					move = moves[rand.nextInt(moves.length)];
					piece.makeMove(move);
				}
			}
			board.print();
			System.out.println("\n");
			
		}

    }
}


