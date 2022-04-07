package chess_logic;

import chess_logic.pices.*;

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
	public static List<pice> pices = new ArrayList<pice>();

	public static void initialize() {
		pices.add(new rook(true, new int[] {0,0} ));
		pices.add(new knight(true, new int[] {1,0} ));
		pices.add(new bishop(true, new int[] {2,0} ));
		pices.add(new queen(true, new int[] {3,0} ));
		pices.add(new king(true, new int[] {4,0} ));
		pices.add(new bishop(true, new int[] {5,0} ));
		pices.add(new knight(true, new int[] {6,0} ));
		pices.add(new rook(true, new int[] {7,0} ));

		for (int i = 0; i < 8; i++) {
			pices.add(new pawn(true, new int[] {i,1} ));
		}

		pices.add(new rook(false, new int[] {0,7} ));
		pices.add(new knight(false, new int[] {1,7} ));
		pices.add(new bishop(false, new int[] {2,7} ));
		pices.add(new queen(false, new int[] {3,7} ));
		pices.add(new king(false, new int[] {4,7} ));
		pices.add(new bishop(false, new int[] {5,7} ));
		pices.add(new knight(false, new int[] {6,7} ));
		pices.add(new rook(false, new int[] {7,7} ));

		for (int i = 0; i < 8; i++) {
			pices.add(new pawn(false, new int[] {i,6} ));
		}
	}

	public static pice getPosition(int x, int y) {
		int[] position = {x,y};
		for (pice pice : pices) {
			if( Arrays.equals(position, pice.position)) return pice;
		}
		return null;
	}
}
