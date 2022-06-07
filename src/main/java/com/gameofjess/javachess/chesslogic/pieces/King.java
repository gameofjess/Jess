package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;

public class King extends Piece {

	private static final Image whiteImage = new Image(Objects.requireNonNull(King.class.getResourceAsStream("/icons/wKing.png")));
	private static final Image blackImage = new Image(Objects.requireNonNull(King.class.getResourceAsStream("/icons/bKing.png")));

	boolean rochade = true;

	public King(Board Board, boolean isWhite) {
		super(Board, isWhite);
		super.fen = "k";
	}

	public King(Board Board, boolean isWhite, boolean rochade) {
		super(Board, isWhite);
		this.rochade = rochade;
	}


	/**
	 * @param checking
	 * @return Move[]
	 */
	@Override
	public Move[] getMoves(boolean checking) {
		checking = false;
		List<Move> moves = new ArrayList<Move>();
		Position position = Board.getPosition(this);

		Position testposition;
		Piece testlocation;
		// hoch
		testposition = new Position(position.getX(), position.getY() + 1);
		testlocation = Board.getBoardMap().get(testposition);
		if (position.getY() + 1 < 8 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX(), position.getY() + 1);
			testlocation = Board.getBoardMap().get(testposition);
			if (testlocation == null) {
				moves.add(new Move(position, testposition));
			} else if (testlocation.isWhite() != isWhite) {
				moves.add(new Move(position, testposition, testposition));
			}
		}
		// hoch rechts
		testposition = new Position(position.getX() + 1, position.getY() + 1);
		testlocation = Board.getBoardMap().get(testposition);
		if (position.getX() + 1 < 8 && position.getY() + 1 < 8 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() + 1, position.getY() + 1);
			testlocation = Board.getBoardMap().get(testposition);
			if (testlocation == null) {
				moves.add(new Move(position, testposition));
			} else if (testlocation.isWhite() != isWhite) {
				moves.add(new Move(position, testposition, testposition));
			}
		}
		// rechts
		testposition = new Position(position.getX() + 1, position.getY());
		testlocation = Board.getBoardMap().get(testposition);
		if (position.getX() + 1 < 8 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() + 1, position.getY());
			testlocation = Board.getBoardMap().get(testposition);
			if (testlocation == null) {
				moves.add(new Move(position, testposition));
			} else if (testlocation.isWhite() != isWhite) {
				moves.add(new Move(position, testposition, testposition));
			}
		}
		// runter rechts
		testposition = new Position(position.getX() + 1, position.getY() - 1);
		testlocation = Board.getBoardMap().get(testposition);
		if (position.getX() + 1 < 8 && position.getY() - 1 >= 0 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() + 1, position.getY() - 1);
			testlocation = Board.getBoardMap().get(testposition);
			if (testlocation == null) {
				moves.add(new Move(position, testposition));
			} else if (testlocation.isWhite() != isWhite) {
				moves.add(new Move(position, testposition, testposition));
			}
		}
		// runter
		testposition = new Position(position.getX(), position.getY() - 1);
		testlocation = Board.getBoardMap().get(testposition);
		if (position.getY() - 1 >= 0 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX(), position.getY() - 1);
			testlocation = Board.getBoardMap().get(testposition);
			if (testlocation == null) {
				moves.add(new Move(position, testposition));
			} else if (testlocation.isWhite() != isWhite) {
				moves.add(new Move(position, testposition, testposition));
			}
		}
		// runter links
		testposition = new Position(position.getX() - 1, position.getY() - 1);
		testlocation = Board.getBoardMap().get(testposition);
		if (position.getX() - 1 >= 0 && position.getY() - 1 >= 0 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() - 1, position.getY() - 1);
			testlocation = Board.getBoardMap().get(testposition);
			if (testlocation == null) {
				moves.add(new Move(position, testposition));
			} else if (testlocation.isWhite() != isWhite) {
				moves.add(new Move(position, testposition, testposition));
			}
		}
		// links
		testposition = new Position(position.getX() - 1, position.getY());
		testlocation = Board.getBoardMap().get(testposition);
		if (position.getX() - 1 >= 0 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() - 1, position.getY());
			testlocation = Board.getBoardMap().get(testposition);
			if (testlocation == null) {
				moves.add(new Move(position, testposition));
			} else if (testlocation.isWhite() != isWhite) {
				moves.add(new Move(position, testposition, testposition));
			}
		}
		// hoch links
		testposition = new Position(position.getX() - 1, position.getY() + 1);
		testlocation = Board.getBoardMap().get(testposition);
		if (position.getX() - 1 >= 0 && position.getY() + 1 < 8 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() - 1, position.getY() + 1);
			testlocation = Board.getBoardMap().get(testposition);
			if (testlocation == null) {
				moves.add(new Move(position, testposition));
			} else if (testlocation.isWhite() != isWhite) {
				moves.add(new Move(position, testposition, testposition));
			}
		}


		// rochade rechts
		if (rochade && Board.getBoardMap().get(new Position(5, position.getY())) == null && Board.getBoardMap().get(new Position(6, position.getY())) == null) {
			Piece rook = Board.getBoardMap().get(new Position(7, position.getY()));
			if (rook instanceof Rook) {
				if (((Rook) rook).rochade) {
					Move test_move = new Move(position, new Position(6, position.getY()), true);
					if (isWhite) {
						if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
							moves.add(test_move);
						}
					} else {
						if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
							moves.add(test_move);
						}
					}
				}
			}
		}

		if (rochade && Board.getBoardMap().get(new Position(1, position.getY())) == null && Board.getBoardMap().get(new Position(2, position.getY())) == null
				&& Board.getBoardMap().get(new Position(3, position.getY())) == null) {
			Piece rook = Board.getBoardMap().get(new Position(0, position.getY()));
			if (rook instanceof Rook) {
				if (((Rook) rook).rochade) {
					Move test_move = new Move(position, new Position(2, position.getY()), true);
					if (isWhite) {
						if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
							moves.add(test_move);
						}
					} else {
						if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
							moves.add(test_move);
						}
					}
				}
			}
		}

		return moves.toArray(new Move[moves.size()]);
	}


	/**
	 * @param move
	 */
	@Override
	public void makeMove(Move move) {
		rochade = false;

		if (move.getRochade()) {
			// rechts
			if (move.getDestination().getX() == 6) {
				Position rookpos = new Position(7, move.getDestination().getY());
				Position kingpos = new Position(4, move.getDestination().getY());
				Rook rook = (Rook) Board.getBoardMap().get(rookpos);
				King king = (King) Board.getBoardMap().get(kingpos);
				Board.boardMapRemove(rookpos);
				Board.boardMapRemove(kingpos);
				Board.boardMapAdd(move.getDestination(), king);
				Board.boardMapAdd(new Position(5, move.getDestination().getY()), rook);
				rook.rochade = false;
				king.rochade = false;

			}
		}

		if (move.getCapturePosition() != null) {
			Board.capture(move.getCapturePosition());
		}
		Board.boardMapRemove(Board.getPosition(this));
		Board.boardMapAdd(move.getDestination(), this);
	}


	/**
	 * @return boolean
	 */
	public boolean checkCheck() {

		for (Piece piece : Board.getBoardMap().values()) {

			for (Move move : piece.getMoves()) {
				if (move.getDestination() == Board.getPosition(this)) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * @param test_move
	 * @param test_piece
	 * @return boolean
	 */
	public boolean checkCheck(Move test_move, Piece test_piece) {
		/*
		 * TODO
		 * 
		 * 
		 * List<Piece> piecesCopy = new ArrayList<Piece>();
		 * 
		 * 
		 * // clone board for (Piece piece : Board.pieces) { piecesCopy.add(piece.getClone()); }
		 * 
		 * // simulate move for (Piece piece : piecesCopy) { if (piece.position.getX() ==
		 * test_piece.position.x && piece.position.getY() == test_piece.position.getY()) {
		 * piece.makeMove(test_move); break; } }
		 * 
		 * for (Piece piece : piecesCopy) { for (Move move : piece.getMoves(false)) { if (move.destinationX
		 * == position.getX() && move.destinationY == position.getY()) { return true; } } }
		 */
		return false;
	}

	@Override
	public Image getImage() {
		if (isWhite) {
			return whiteImage;
		} else {
			return blackImage;
		}
	}

}
