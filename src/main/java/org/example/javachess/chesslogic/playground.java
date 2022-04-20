package org.example.javachess.chesslogic;

import java.util.Random;

import org.example.javachess.chesslogic.pieces.Piece;

public class playground {
	public static void main(String[] args) {
		Random rangen = new Random();
		Board.initialize();
		for (int i = 0; i < 20; i++) {
			Piece pcs = Board.pieces.get(rangen.nextInt(Board.pieces.size()));
			//Piece pcs = Board.pieces.get(Board.pieces.size()-1);
			Move[] mvs = pcs.getMoves();
			if (mvs.length > 0) {
				Move mv = mvs[rangen.nextInt(mvs.length)];
				pcs.makeMove(mv);
				System.out.println(Board.getFEN());
			}
		}
    }
}
