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

public class board {
	public static List<piece> pieces = new ArrayList<piece>();

	public static void initialize() {
		pieces.add(new rook(true, new int[] {0,0} ));
		pieces.add(new knight(true, new int[] {1,0} ));
		pieces.add(new bishop(true, new int[] {2,0} ));
		pieces.add(new queen(true, new int[] {3,0} ));
		pieces.add(new king(true, new int[] {4,0} ));
		pieces.add(new bishop(true, new int[] {5,0} ));
		pieces.add(new knight(true, new int[] {6,0} ));
		pieces.add(new rook(true, new int[] {7,0} ));

		for (int i = 0; i < 8; i++) {
			pieces.add(new pawn(true, new int[] {i,1} ));
		}

		pieces.add(new rook(false, new int[] {0,7} ));
		pieces.add(new knight(false, new int[] {1,7} ));
		pieces.add(new bishop(false, new int[] {2,7} ));
		pieces.add(new queen(false, new int[] {3,7} ));
		pieces.add(new king(false, new int[] {4,7} ));
		pieces.add(new bishop(false, new int[] {5,7} ));
		pieces.add(new knight(false, new int[] {6,7} ));
		pieces.add(new rook(false, new int[] {7,7} ));

		for (int i = 0; i < 8; i++) {
			pieces.add(new pawn(false, new int[] {i,6} ));
		}
	}

	public static piece getPosition(int x, int y) {
		int[] position = {x,y};
		for (piece piece : pieces) {
			if( Arrays.equals(position, piece.position)) return piece;
		}
		return null;
	}
}
