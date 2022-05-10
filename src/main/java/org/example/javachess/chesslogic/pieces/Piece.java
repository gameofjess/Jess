package org.example.javachess.chesslogic.pieces;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;
public abstract class Piece implements Cloneable {

	Board Board;
	public boolean isWhite;
    public String fen;

    public Piece(Board Board, boolean isWhite) {
		this.Board = Board;
        this.isWhite = isWhite;

    }

    public abstract Move[] getMoves(boolean checking);

    public Move[] getMoves() {
        return getMoves(true);
    }

    public void makeMove(Move move) {
		if (move.capture != null) {
			Board.capturedPieces.add(move.capture);
		}
		Board.board.remove(Board.getPosition(this));
        Board.board.put(move.destination , this);
    }

	public String toString(){
		return " "+fen+"  ";
	}

}
