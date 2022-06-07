package com.gameofjess.javachess.chesslogic.pieces;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;

import javafx.scene.image.Image;

public abstract class Piece implements Cloneable {
    /**
     * Abstract Class to represent a chesspiece
     */

    transient Board Board;
    boolean isWhite;
    String fen;

    public Piece(Board Board, boolean isWhite) {
        this.Board = Board;
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
            Board.capture(move.getCapturePosition());;
        }
        Board.boardMapRemove(Board.getPosition(this));
        Board.boardMapAdd(move.getDestination(), this);
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
}
