package com.gameofjess.javachess.chesslogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gameofjess.javachess.chesslogic.pieces.*;

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
	/**
	 * The Class representing the Chessboard 
	 */

	Map<Position, Piece> board = new HashMap<Position, Piece>();

	List<Piece> capturedPieces = new ArrayList<Piece>();

    King kingWhite;
	King kingBlack;

	
	/** 
	 * 
	 * @param piece
	 * @return Position Position Object of the passed Piece
	 */
	public  Position getPosition(Piece piece){
			for (Map.Entry<Position, Piece> entry : board.entrySet()) {
				if (entry.getValue().equals(piece)) {
					return entry.getKey();
				}
			}
			return null;
	}

	/**
	 * Set the Board to the basic position
	 */
	public  void initialize() {
		board.put(new Position(0, 0), new Rook(this, true));
		board.put(new Position(1, 0), new Knight(this, true));
		board.put(new Position(2, 0), new Bishop(this, true));
		board.put(new Position(3, 0), new Queen(this, true));
		kingWhite = new King(this, true);
		board.put(new Position(4, 0), kingWhite);
		board.put(new Position(5, 0), new Bishop(this, true));
		board.put(new Position(6, 0), new Knight(this, true));
		board.put(new Position(7, 0), new Rook(this, true));

		for (int i = 0; i < 8; i++) {
			board.put(new Position(i, 1), new Pawn(this, true));
		}

		board.put(new Position(0, 7), new Rook(this, false));
		board.put(new Position(1, 7), new Knight(this, false));
		board.put(new Position(2, 7), new Bishop(this, false));
		board.put(new Position(3, 7), new Queen(this, false));
		kingBlack = new King(this, false);
		board.put(new Position(4, 7), kingBlack);
		board.put(new Position(5, 7), new Bishop(this, false));
		board.put(new Position(6, 7), new Knight(this, false));
		board.put(new Position(7, 7), new Rook(this, false));

		for (int i = 0; i < 8; i++) {
			board.put(new Position(i, 6), new Pawn(this, false));
		}

	}

	/**
	 * FOR DEBUGGING:
	 * Print the current state of the Board to the console
	 */
	public void print(){
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(board.get(new Position(j, i)));
			}
			System.out.print("\n");
		}
	}

	/**
	 * @return the board
	 */
	public Map<Position, Piece> getBoardMap() {
		return Collections.unmodifiableMap(board);
	}

	/**
	 * @return the capturedPieces
	 */
	public List<Piece> getCapturedPieces() {
		return Collections.unmodifiableList(capturedPieces);
	}

	/**
	 * @return the kingWhite
	 */
	public King getKingWhite() {
		return kingWhite;
	}

	/**
	 * @return the kingBlack
	 */
	public King getKingBlack() {
		return kingBlack;
	}

	public void addCapturedPiece(Piece piece){
		capturedPieces.add(piece);
	}

	public void boardMapRemove(Position position){
		board.remove(position);
	}

	public void boardMapAdd(Position position, Piece piece){
		board.put(position, piece);
	}
}
