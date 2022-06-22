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

public class Knight extends Piece {
	private static final Logger log = LogManager.getLogger(Knight.class);

	/**
	 * Constructor
	 * @param Board to be linked
	 * @param isWhite color of the piece
	 */
    public Knight(Board Board, boolean isWhite) {
        super(Board, isWhite);
        super.fen = "n";
    }

	@Override
    public Move[] getMoves(boolean checking) {
		log.trace("getting moves");
        List<Move> moves = new ArrayList<>();
		Position position = board.getPosition(this);


		Position testPosition;
		Piece testLocation;
        
		// up, right
		testPosition = new Position(position.getX() + 1, position.getY() + 2);
		if (testPosition.getX() < 8 && testPosition.getY() < 8) {
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
			else if (testLocation.isWhite != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
        // right, up
		testPosition = new Position(position.getX() + 2, position.getY() + 1);
		if (testPosition.getX() < 8 && testPosition.getY() < 8) {
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
			else if (testLocation.isWhite != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
        //right, down
        testPosition = new Position(position.getX() + 2, position.getY() - 1);
		if (testPosition.getX() < 8 && testPosition.getY() >= 0) {
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
			else if (testLocation.isWhite != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		//down, right
        testPosition = new Position(position.getX() + 1, position.getY() - 2);
		if (testPosition.getX() < 8 && testPosition.getY() >= 0) {
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
			else if (testLocation.isWhite != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		//down, right
        testPosition = new Position(position.getX() - 1, position.getY() - 2);
		if (testPosition.getX() >= 0 && testPosition.getY() >= 0) {
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
			else if (testLocation.isWhite != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		//left, down
        testPosition = new Position(position.getX() - 2, position.getY() - 1);
		if (testPosition.getX() >= 0 && testPosition.getY() >= 0) {
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
			else if (testLocation.isWhite != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		//left, up
        testPosition = new Position(position.getX() - 2, position.getY() + 1);
		if (testPosition.getX() >= 0 && testPosition.getY() < 8) {
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
			else if (testLocation.isWhite != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		//up, left
        testPosition = new Position(position.getX() - 1, position.getY() + 2);
		if (testPosition.getX() >= 0 && testPosition.getY() < 8) {
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
			else if (testLocation.isWhite != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
        return moves.toArray(new Move[0]);
    }

    @Override
    public Pieces getEnumValue() {
        return Pieces.KNIGHT;
    }

    @Override
    public Image getImage() {
        return getEnumValue().getImage(isWhite);
    }

	@Override
	public Piece getClone(Board board) {
		return new Knight(board, isWhite);
	}

}
