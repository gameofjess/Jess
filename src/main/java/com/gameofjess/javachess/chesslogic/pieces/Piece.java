package org.example.javachess.chesslogic.pieces;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;
import javafx.scene.image.Image;
public abstract class Piece implements Cloneable {
	/**
	 * Abstract Class to represent a chesspiece
	 */

	Board Board;
	public boolean isWhite;
    public String fen;

    public Piece(Board Board, boolean isWhite) {
		this.Board = Board;
        this.isWhite = isWhite;

    }

    public abstract Move[] getMoves(boolean checking);

    
	/** 
	 * Returns an Array of all valid moves for the Current Board
	 * @return Move[]
	 */
	public Move[] getMoves() {
        return getMoves(true);
    }

    
	/** 
	 * Make a given move on the Board
	 * @param move
	 */
	public void makeMove(Move move) {
		if (move.capture != null) {
			Board.capturedPieces.add(move.capture);
		}
		Board.board.remove(Board.getPosition(this));
        Board.board.put(move.destination , this);
    }

	
	/** 
	 * @return String
	 */
	public String toString(){
		return " "+fen+"  ";
	}

	public abstract Image getImage();

}
