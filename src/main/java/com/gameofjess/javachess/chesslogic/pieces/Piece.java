package com.gameofjess.javachess.chesslogic.pieces;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import javafx.scene.image.Image;

public abstract class Piece implements Cloneable {
    /**
     * Abstract Class to represent a chesspiece
     */

    transient Board board;
    boolean isWhite;
    String fen;

    public Piece(Board board, boolean isWhite) {
        this.board = board;
        this.isWhite = isWhite;
    }

    public abstract Move[] getMoves(boolean checking);


    /**
     * Returns an Array of all valid moves for the Current Board
     * 
     * @return Move[]
     */
    public Move[] getMoves() {
        return getMoves(true);
    }


    /**
     * Make a given move on the Board
     * 
     * @param move
     */
    public void makeMove(Move move) {
        if (move.getCapturePosition() != null) {
            board.capture(move.getCapturePosition());;
        }
        board.boardMapRemove(board.getPosition(this));
        board.boardMapAdd(move.getDestination(), this);
    }


    /**
     * @return String
     */
    public String toString() {
        return " " + fen + "  ";
    }

    public abstract Image getImage();

    /**
     * @return the isWhite
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * @return the fen
     */
    public String getFen() {
        return fen;
    }

	public Position getPosition(){
		return board.getPosition(this);
	}
}
