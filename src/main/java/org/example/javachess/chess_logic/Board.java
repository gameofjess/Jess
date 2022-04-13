package org.example.javachess.chess_logic;

import org.example.javachess.chess_logic.pieces.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/*
	-----------------------------------------
8	| 07 |*17*| 27 |*37*| 47 |*57*| 67 |*77*|
	-----------------------------------------
7	|*06*| 16 |*26*| 36 |*46*| 56 |*66*| 76 |
	-----------------------------------------
6	| 05 |*15*| 25 |*35*| 45 |*55*| 65 |*75*|
	-----------------------------------------
5	|*04*| 14 |*24*| 34 |*44*| 54 |*64*| 74 |
	-----------------------------------------
4	| 03 |*13*| 23 |*33*| 43 |*53*| 63 |*73*|
	-----------------------------------------
3	|*02*| 12 |*22*| 32 |*42*| 52 |*62*| 72 |
	-----------------------------------------
2	| 01 |*11*| 21 |*31*| 41 |*51*| 61 |*71*|
	-----------------------------------------
1	|*00*| 10 |*20*| 30 |*40*| 50 |*60*| 70 |
	-----------------------------------------
	  A    B    C    D    E    F    G    H
*/

public class Board {
	public static List<Piece> pieces = new ArrayList<Piece>();

	public static void initialize() {
		pieces.add(new Rook(true, new int[] {0,0} ));
		pieces.add(new Knight(true, new int[] {1,0} ));
		pieces.add(new Bishop(true, new int[] {2,0} ));
		pieces.add(new Queen(true, new int[] {3,0} ));
		pieces.add(new King(true, new int[] {4,0} ));
		pieces.add(new Bishop(true, new int[] {5,0} ));
		pieces.add(new Knight(true, new int[] {6,0} ));
		pieces.add(new Rook(true, new int[] {7,0} ));

		for (int i = 0; i < 8; i++) {
			pieces.add(new Pawn(true, new int[] {i,1} ));
		}

		pieces.add(new Rook(false, new int[] {0,7} ));
		pieces.add(new Knight(false, new int[] {1,7} ));
		pieces.add(new Bishop(false, new int[] {2,7} ));
		pieces.add(new Queen(false, new int[] {3,7} ));
		pieces.add(new King(false, new int[] {4,7} ));
		pieces.add(new Bishop(false, new int[] {5,7} ));
		pieces.add(new Knight(false, new int[] {6,7} ));
		pieces.add(new Rook(false, new int[] {7,7} ));

		for (int i = 0; i < 8; i++) {
			pieces.add(new Pawn(false, new int[] {i,6} ));
		}
	}

	public static Piece getPosition(int x, int y) {
		int[] position = {x,y};
		for (Piece piece : pieces) {
			if( Arrays.equals(position, piece.position)) return piece;
		}
		return null;
	}

	public static boolean checkCheck(){
		//TODO 
		return false;
	}
}
