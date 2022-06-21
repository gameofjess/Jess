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

public class Bishop extends Piece {
	private static final Logger log = LogManager.getLogger(Bishop.class);

	/**
	 * Constructor
	 * @param Board to be linked
	 * @param isWhite color of the piece
	 */
	public Bishop(Board Board, boolean isWhite) {
		super(Board, isWhite);
		super.fen = "b";
	}
	
	@Override
	public Move[] getMoves(boolean checking) {
		log.trace("getting moves bishop");
		List<Move> moves = new ArrayList<>();
		Position position = board.getPosition(this);

		// up, right
		for (int j = 1; j < 8; j++) {
			Position testPosition = new Position(position.getX() + j, position.getY() + j);
			if (testPosition.getX() < 8 && testPosition.getY() < 8) {
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
		//down, right
		for (int j = 1; j < 8; j++) {
			Position testPosition = new Position(position.getX() + j, position.getY() - j);
			if (testPosition.getX() < 8 && testPosition.getY() >= 0) {
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
		// down, left
		for (int j = 1; j < 8; j++) {
			Position testPosition = new Position(position.getX() - j, position.getY() - j);
			if (testPosition.getX() >= 0 && testPosition.getY() >= 0) {
				Piece testLocation = board.getBoardMap().get(testPosition);
				if(testLocation == null){
					Move testMove = new Move(position, testPosition);
					if (!checking || !checkCheckMove(testMove)){
						moves.add(testMove);
					}
				}
				else if (testLocation.isWhite() == isWhite) {
					break;
				}
				else if(testLocation.isWhite() != isWhite){
					Move testMove = new Move(position, testPosition, testPosition);
					if (!checking || !checkCheckMove(testMove)){
						moves.add(testMove);
					}
					break;
				}
			}
			else break;
		}
		// up, left
		for (int j = 1; j < 8; j++) {
			Position testPosition = new Position(position.getX() - j, position.getY() + j);
			if (testPosition.getX() >= 0 && testPosition.getY() < 8) {
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
    public Pieces getEnumValue() {
        return Pieces.BISHOP;
    }

	@Override
	public Image getImage() {
        return getEnumValue().getImage(isWhite);
	}

	@Override
	public Piece getClone(Board board) {
		return new Bishop(board, isWhite);
	}
}
