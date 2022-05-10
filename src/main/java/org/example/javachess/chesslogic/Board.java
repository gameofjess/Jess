package org.example.javachess.chesslogic;

import java.util.List;
import java.util.Map;

import org.example.javachess.chesslogic.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * ----------------------------------------- 8 | 07 |*17*| 27 |*37*| 47 |*57*| 67 |*77*|
 * ----------------------------------------- 7 |*06*| 16 |*26*| 36 |*46*| 56 |*66*| 76 |
 * ----------------------------------------- 6 | 05 |*15*| 25 |*35*| 45 |*55*| 65 |*75*|
 * ----------------------------------------- 5 |*04*| 14 |*24*| 34 |*44*| 54 |*64*| 74 |
 * ----------------------------------------- 4 | 03 |*13*| 23 |*33*| 43 |*53*| 63 |*73*|
 * ----------------------------------------- 3 |*02*| 12 |*22*| 32 |*42*| 52 |*62*| 72 |
 * ----------------------------------------- 2 | 01 |*11*| 21 |*31*| 41 |*51*| 61 |*71*|
 * ----------------------------------------- 1 |*00*| 10 |*20*| 30 |*40*| 50 |*60*| 70 |
 * ----------------------------------------- A B C D E F G H
 */

public class Board {

	public static Map<Position, Piece> board = new HashMap<Position, Piece>();

	public static List<Piece> capturedPieces = new ArrayList<Piece>();

	public static King kingWhite;
	public static King kingBlack;

	public static Position getPosition(Piece piece){
			for (Map.Entry<Position, Piece> entry : board.entrySet()) {
				if (entry.getValue().equals(piece)) {
					return entry.getKey();
				}
			}
			return null;
	}

	public static void initialize() {
		board.put(new Position(0, 0), new Rook(true));
		board.put(new Position(1, 0), new Knight(true));
		board.put(new Position(2, 0), new Bishop(true));
		board.put(new Position(3, 0), new Queen(true));
		kingWhite = new King(true);
		board.put(new Position(4, 0), kingWhite);
		board.put(new Position(5, 0), new Bishop(true));
		board.put(new Position(6, 0), new Knight(true));
		board.put(new Position(7, 0), new Rook(true));

		for (int i = 0; i < 8; i++) {
			board.put(new Position(i, 1), new Pawn(true));
		}

		board.put(new Position(0, 7), new Rook(false));
		board.put(new Position(1, 7), new Knight(false));
		board.put(new Position(2, 7), new Bishop(false));
		board.put(new Position(3, 7), new Queen(false));
		kingBlack = new King(false);
		board.put(new Position(4, 7), kingBlack);
		board.put(new Position(5, 7), new Bishop(false));
		board.put(new Position(6, 7), new Knight(false));
		board.put(new Position(7, 7), new Rook(false));

		for (int i = 0; i < 8; i++) {
			board.put(new Position(i, 6), new Pawn(false));
		}

	}

	public static void print(){
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(Board.board.get(new Position(j, i)));
			}
			System.out.print("\n");
		}
	}
}
