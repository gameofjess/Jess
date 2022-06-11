package com.gameofjess.javachess.chesslogic.pieces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import javafx.scene.image.Image;

public abstract class Piece implements Cloneable {
    /**
     * Abstract Class to represent a chesspiece
     */
	private static final Logger log = LogManager.getLogger(Board.class);

    transient Board board;
    boolean isWhite;
    String fen;

    public Piece(Board board, boolean isWhite) {
		log.debug("Creating {}", this.getClass().getSimpleName());
        this.board = board;
        this.isWhite = isWhite;
    }

    public abstract Move[] getMoves(boolean checking);

	public abstract Piece getClone(Board board);

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
		log.debug("making move");
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

	boolean checkCheckMove(Move move){
		log.debug("Check Check for move generation");
		Board cloneBoard = new Board(board);
		log.debug("cloned board!");
		cloneBoard.getBoardMap().get(move.getOrigin()).makeMove(move);
		if (isWhite) {
			return cloneBoard.getKingWhite().checkCheck();
		}
		else{
			return cloneBoard.getKingBlack().checkCheck();
		}
	}
}
