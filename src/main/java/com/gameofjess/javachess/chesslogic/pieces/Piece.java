package com.gameofjess.javachess.chesslogic.pieces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.helper.game.Pieces;

import javafx.scene.image.Image;

public abstract class Piece implements Cloneable {
    /**
     * Abstract Class to represent a chesspiece
     */
	private static final Logger log = LogManager.getLogger(Board.class);

    transient Board board;
    boolean isWhite;
    String fen;

    /**
     * Constructor
     * @param board
     * @param isWhite
     */
    public Piece(Board board, boolean isWhite) {
		log.trace("Creating {}", this.getClass().getSimpleName());
        this.board = board;
        this.isWhite = isWhite;
    }

    /**
     * get possible moves moves
     * @param checking enable/disable moves that put the king into check
     * @return array of move objects
     */
    public abstract Move[] getMoves(boolean checking);

	public abstract Piece getClone(Board board);

    /**
     * get all legal moves
     * @return
     */
    public Move[] getMoves() {
        return getMoves(true);
    }


    /**
     * Make a given move on the Board
     * @param move
     */
    public void makeMove(Move move) {
        log.debug("current board:\n{}", board);
		log.trace("making move");
        if (move.getCapturePosition() != null) {
            board.capture(move.getCapturePosition());;
        }
        board.boardMapRemove(board.getPosition(this));
        board.boardMapAdd(move.getDestination(), this);
        log.debug("current board:\n{}", board);
    }


    /**
     * @return String
     */
    public String toString() {
        return " " + fen + "  ";
    }

    /**
     * Method that returns the corresponding value in the Pieces-enum
     */
    public abstract Pieces getEnumValue();

    /**
     * Method that returns the corresponding javafx image object.
     */
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

    /**
     * Checks if a move puts the King into check
     * @param move
     * @return
     */
	boolean checkCheckMove(Move move){
		log.trace("Check Check for move generation");
		Board cloneBoard = new Board(board);
		log.trace("cloned board!");
		cloneBoard.getBoardMap().get(move.getOrigin()).makeMove(move);
		if (isWhite) {
			return cloneBoard.getKingWhite().checkCheck();
		}
		else{
			return cloneBoard.getKingBlack().checkCheck();
		}
	}
}
