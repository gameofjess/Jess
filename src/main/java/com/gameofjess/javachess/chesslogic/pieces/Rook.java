package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.helper.game.Pieces;

import javafx.scene.image.Image;

public class Rook extends Piece {
	private static final Logger log = LogManager.getLogger(Rook.class);

    boolean castling;

	/**
	 * Constructor
	 * @param Board to be linked
	 * @param isWhite color of the piece
	 * @param castling boolean is castling possible
	 */
    public Rook(Board Board, boolean isWhite, boolean castling) {
        super(Board, isWhite);
        this.castling = castling;
    }

	@Override
    public Move[] getMoves(boolean checking) {
		log.trace("getting moves rook");
        List<Move> moves = new ArrayList<>();
		Position position = board.getPosition(this);

		//right
		for (int j = 1; j < 8; j++) {
			Position testPosition = new Position(position.getX() + j, position.getY());
			if (testPosition.getX() < 8) {
				Piece testLocation = board.getBoardMap().get(testPosition);
				if(testLocation == null){
					Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
				}
				else if (testLocation.isWhite() == isWhite) {
					break;
				}
				else if(testLocation.isWhite() != isWhite){
					Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
					break;
				}
			}
			else break;
		}
		//links
		for (int j = 1; j < 8; j++) {
			Position testPosition = new Position(position.getX() - j, position.getY());
			if (testPosition.getX() >= 0) {
				Piece testLocation = board.getBoardMap().get(testPosition);
				if(testLocation == null){
					Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
				}
				else if (testLocation.isWhite() == isWhite) {
					break;
				}
				else if(testLocation.isWhite() != isWhite){
					Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
					break;
				}
			}
			else break;
		}
		//up
		for (int j = 1; j < 8; j++) {
			Position testPosition = new Position(position.getX(), position.getY() + j);
			if (testPosition.getY() < 8) {
				Piece testLocation = board.getBoardMap().get(testPosition);
				if(testLocation == null){
					Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
				}
				else if (testLocation.isWhite() == isWhite) {
					break;
				}
				else if(testLocation.isWhite() != isWhite){
					Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
					break;
				}
			}
			else break;
		}
		//down
		for (int j = 1; j < 8; j++) {
			Position testPosition = new Position(position.getX(), position.getY() - j);
			if (testPosition.getY() >= 0) {
				Piece testLocation = board.getBoardMap().get(testPosition);
				if(testLocation == null){
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
				}
				else if (testLocation.isWhite() == isWhite) {
					break;
				}
				else if(testLocation.isWhite() != isWhite){
					Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
					break;
				}
			}
			else break;
		}
        return moves.toArray(new Move[0]);
    }

	@Override
    public void makeMove(Move move) {
		log.trace("making move");
		castling = false;
		if (move.getCapturePosition() != null) {
			board.capture(move.getCapturePosition());
		}
		board.boardMapRemove(board.getPosition(this));
        board.boardMapAdd(move.getDestination() , this);
    }

    @Override
    public Pieces getEnumValue() {
        return Pieces.ROOK;
    }

	@Override
	public Image getImage() {
        return getEnumValue().getImage(isWhite);
	}

	@Override
	public Piece getClone(Board board) {
		return new Rook(board, isWhite, castling);
	}


}
