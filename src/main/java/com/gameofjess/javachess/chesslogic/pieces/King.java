package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.helper.game.Pieces;

import javafx.scene.image.Image;

public class King extends Piece {
	private static final Logger log = LogManager.getLogger(Board.class);

	private boolean castling = true;

	/**
	 * Constructor
	 * @param Board linked Board
	 * @param isWhite Color o the Piece
	 */
	public King(Board Board, boolean isWhite) {
		super(Board, isWhite);
		super.fen = "k";
	}

	/**
	 * Clone constructor
	 * @param Board Cloned board
	 * @param isWhite Color of the generated Piece
	 * @param castling Castling status of the generated Piece
	 */
	public King(Board Board, boolean isWhite, boolean castling) {
		super(Board, isWhite);
		this.castling = castling;
	}

	@Override
	public Move[] getMoves(boolean checking) {
		log.trace("getting moves");
		List<Move> moves = new ArrayList<>();
		Position position = board.getPosition(this);

		Position testPosition;
		Piece testLocation;
		// up
		testPosition = new Position(position.getX(), position.getY() + 1);
		testLocation = board.getBoardMap().get(testPosition);
		if (position.getY() + 1 < 8 && (testLocation == null || testLocation.isWhite != isWhite)) {
			testPosition = new Position(position.getX(), position.getY() + 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
					if (!checking || !checkCheckMove(testMove)) {
						moves.add(testMove);
					}
			} else if (testLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		// up, right
		testPosition = new Position(position.getX() + 1, position.getY() + 1);
		testLocation = board.getBoardMap().get(testPosition);
		if (position.getX() + 1 < 8 && position.getY() + 1 < 8 && (testLocation == null || testLocation.isWhite != isWhite)) {
			testPosition = new Position(position.getX() + 1, position.getY() + 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
					if (!checking || !checkCheckMove(testMove)) {
						moves.add(testMove);
					}
			} else if (testLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		// right
		testPosition = new Position(position.getX() + 1, position.getY());
		testLocation = board.getBoardMap().get(testPosition);
		if (position.getX() + 1 < 8 && (testLocation == null || testLocation.isWhite != isWhite)) {
			testPosition = new Position(position.getX() + 1, position.getY());
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
					if (!checking || !checkCheckMove(testMove)) {
						moves.add(testMove);
					}
			} else if (testLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		// down, right
		testPosition = new Position(position.getX() + 1, position.getY() - 1);
		testLocation = board.getBoardMap().get(testPosition);
		if (position.getX() + 1 < 8 && position.getY() - 1 >= 0 && (testLocation == null || testLocation.isWhite != isWhite)) {
			testPosition = new Position(position.getX() + 1, position.getY() - 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
					if (!checking || !checkCheckMove(testMove)) {
						moves.add(testMove);
					}
			} else if (testLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		// down
		testPosition = new Position(position.getX(), position.getY() - 1);
		testLocation = board.getBoardMap().get(testPosition);
		if (position.getY() - 1 >= 0 && (testLocation == null || testLocation.isWhite != isWhite)) {
			testPosition = new Position(position.getX(), position.getY() - 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
					if (!checking || !checkCheckMove(testMove)) {
						moves.add(testMove);
					}
			} else if (testLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		// down, left
		testPosition = new Position(position.getX() - 1, position.getY() - 1);
		testLocation = board.getBoardMap().get(testPosition);
		if (position.getX() - 1 >= 0 && position.getY() - 1 >= 0 && (testLocation == null || testLocation.isWhite != isWhite)) {
			testPosition = new Position(position.getX() - 1, position.getY() - 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
					if (!checking || !checkCheckMove(testMove)) {
						moves.add(testMove);
					}
			} else if (testLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		// links
		testPosition = new Position(position.getX() - 1, position.getY());
		testLocation = board.getBoardMap().get(testPosition);
		if (position.getX() - 1 >= 0 && (testLocation == null || testLocation.isWhite != isWhite)) {
			testPosition = new Position(position.getX() - 1, position.getY());
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
					if (!checking || !checkCheckMove(testMove)) {
						moves.add(testMove);
					}
			} else if (testLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		// up, left
		testPosition = new Position(position.getX() - 1, position.getY() + 1);
		testLocation = board.getBoardMap().get(testPosition);
		if (position.getX() - 1 >= 0 && position.getY() + 1 < 8 && (testLocation == null || testLocation.isWhite != isWhite)) {
			testPosition = new Position(position.getX() - 1, position.getY() + 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
					if (!checking || !checkCheckMove(testMove)) {
						moves.add(testMove);
					}
			} else if (testLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}


		// castling short
		if (castling && board.getBoardMap().get(new Position(1, position.getY())) == null && board.getBoardMap().get(new Position(2, position.getY())) == null) {
			Position rookPosition = new Position(0, position.getY());
			Piece rook = board.getBoardMap().get(rookPosition);
			if (rook instanceof Rook) {
				if (((Rook) rook).isCastling()) {
					Move test_move = new Move(position, new Position(1, position.getY()), true, rookPosition);
					if (checkCheckMove(test_move) || !checking){
						moves.add(test_move);
					}
				}
			}
		}

		//castling long
		if (castling && board.getBoardMap().get(new Position(4, position.getY())) == null && board.getBoardMap().get(new Position(5, position.getY())) == null
				&& board.getBoardMap().get(new Position(6, position.getY())) == null) {
			Position rookPosition = new Position(7, position.getY());
			Piece rook = board.getBoardMap().get(rookPosition);
			if (rook instanceof Rook) {
				if (((Rook) rook).isCastling()) {
					Move test_move = new Move(position, new Position(5, position.getY()), true, rookPosition);
					if (checkCheckMove(test_move) || !checking){
						moves.add(test_move);
					}
				}
			}
		}

		return moves.toArray(new Move[0]);
	}

	@Override
	public void makeMove(Move move) {
		log.debug("making move");
		castling = false;

		if (move.getCastling() != null) {
				Rook rook = (Rook) board.getBoardMap().get(move.getCastling());
				King king = (King) board.getBoardMap().get(move.getOrigin());
				board.boardMapRemove(move.getOrigin());
				board.boardMapRemove(move.getCastling());
				board.boardMapAdd(move.getDestination(), king);
				board.boardMapAdd(move.getCastling(), rook);
				rook.setCastling(false);
				this.castling = false;
		}

		if (move.getCapturePosition() != null) {
			board.capture(move.getCapturePosition());
		}
		board.boardMapRemove(board.getPosition(this));
		board.boardMapAdd(move.getDestination(), this);
		log.debug("current board:\n{}", board);
	}

    @Override
    public Pieces getEnumValue() {
        return Pieces.KING;
    }

	@Override
	public Image getImage() {
        return getEnumValue().getImage(isWhite);
	}

	/**
	 * Check if the King is in Check
	 * @return boolean check status
	 */
	public boolean checkCheck(){
		log.trace("check check King");
		Position position = getPosition();
		log.trace(position);

		return board.getBoardMap().values().stream().parallel().anyMatch(piece ->
			Arrays.stream(piece.getMoves(false)).parallel().map(Move::getCapturePosition).filter(Objects::nonNull).anyMatch(position2 -> position2.equals(position))
		);
	}

	/**
	 * Check if the King is Checkmate
	 * @return boolean Checkmate
	 */
	public boolean checkCheckMate(){
		log.trace("Checking mate");
		return board.getBoardMap().values().parallelStream().allMatch(piece ->
			piece.isWhite != this.isWhite || piece.getMoves().length == 0
		);
	}

	@Override
	public Piece getClone(Board board) {
		King king = new King(board, isWhite, castling);
		if (isWhite) {
			board.setKingWhite(king);
		}
		else{
			board.setKingBlack(king);
		}
		return king;
	}
}
