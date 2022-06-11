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

import javafx.scene.image.Image;

public class King extends Piece {
	private static final Logger log = LogManager.getLogger(Board.class);

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
		log.debug("getting moves king");
		List<Move> moves = new ArrayList<Move>();
		Position position = board.getPosition(this);

		Position testposition;
		Piece testlocation;
		// hoch
		testposition = new Position(position.getX(), position.getY() + 1);
		testlocation = board.getBoardMap().get(testposition);
		if (position.getY() + 1 < 8 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX(), position.getY() + 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
			} else if (testlocation.isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// hoch rechts
		testposition = new Position(position.getX() + 1, position.getY() + 1);
		testlocation = board.getBoardMap().get(testposition);
		if (position.getX() + 1 < 8 && position.getY() + 1 < 8 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() + 1, position.getY() + 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
			} else if (testlocation.isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// rechts
		testposition = new Position(position.getX() + 1, position.getY());
		testlocation = board.getBoardMap().get(testposition);
		if (position.getX() + 1 < 8 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() + 1, position.getY());
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
			} else if (testlocation.isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// runter rechts
		testposition = new Position(position.getX() + 1, position.getY() - 1);
		testlocation = board.getBoardMap().get(testposition);
		if (position.getX() + 1 < 8 && position.getY() - 1 >= 0 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() + 1, position.getY() - 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
			} else if (testlocation.isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// runter
		testposition = new Position(position.getX(), position.getY() - 1);
		testlocation = board.getBoardMap().get(testposition);
		if (position.getY() - 1 >= 0 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX(), position.getY() - 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
			} else if (testlocation.isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// runter links
		testposition = new Position(position.getX() - 1, position.getY() - 1);
		testlocation = board.getBoardMap().get(testposition);
		if (position.getX() - 1 >= 0 && position.getY() - 1 >= 0 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() - 1, position.getY() - 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
			} else if (testlocation.isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// links
		testposition = new Position(position.getX() - 1, position.getY());
		testlocation = board.getBoardMap().get(testposition);
		if (position.getX() - 1 >= 0 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() - 1, position.getY());
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
			} else if (testlocation.isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// hoch links
		testposition = new Position(position.getX() - 1, position.getY() + 1);
		testlocation = board.getBoardMap().get(testposition);
		if (position.getX() - 1 >= 0 && position.getY() + 1 < 8 && (testlocation == null || testlocation.isWhite != isWhite)) {
			testposition = new Position(position.getX() - 1, position.getY() + 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
			} else if (testlocation.isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}


		// rochade kurz
		if (rochade && board.getBoardMap().get(new Position(5, position.getY())) == null && board.getBoardMap().get(new Position(6, position.getY())) == null) {
			Piece rook = board.getBoardMap().get(new Position(7, position.getY()));
			if (rook instanceof Rook) {
				if (((Rook) rook).rochade) {
					Move test_move = new Move(position, new Position(6, position.getY()), true);
					if (isWhite) {
						if (!checking || true) {
							moves.add(test_move);
						}
					} else {
						if (!checking || true) {
							moves.add(test_move);
						}
					}
				}
			}
		}

		//rochade lang
		if (rochade && board.getBoardMap().get(new Position(1, position.getY())) == null && board.getBoardMap().get(new Position(2, position.getY())) == null
				&& board.getBoardMap().get(new Position(3, position.getY())) == null) {
			Piece rook = board.getBoardMap().get(new Position(0, position.getY()));
			if (rook instanceof Rook) {
				if (((Rook) rook).rochade) {
					Move test_move = new Move(position, new Position(2, position.getY()), true);
					if (isWhite) {
						if (!checking || true) {
							moves.add(test_move);
						}
					} else {
						if (!checking || true) {
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
			// kurz
			if (move.getDestination().getX() == 6) {
				Position rookpos = new Position(7, move.getDestination().getY());
				Position kingpos = new Position(4, move.getDestination().getY());
				Rook rook = (Rook) board.getBoardMap().get(rookpos);
				King king = (King) board.getBoardMap().get(kingpos);
				board.boardMapRemove(rookpos);
				board.boardMapRemove(kingpos);
				board.boardMapAdd(move.getDestination(), king);
				board.boardMapAdd(new Position(5, move.getDestination().getY()), rook);
				rook.rochade = false;
				king.rochade = false;

			}
			else{
				Position rookpos = new Position(0, move.getDestination().getY());
				Position kingpos = new Position(4, move.getDestination().getY());
				Rook rook = (Rook) board.getBoardMap().get(rookpos);
				King king = (King) board.getBoardMap().get(kingpos);
				board.boardMapRemove(rookpos);
				board.boardMapRemove(kingpos);
				board.boardMapAdd(move.getDestination(), king);
				board.boardMapAdd(new Position(3, move.getDestination().getY()), rook);
				rook.rochade = false;
				king.rochade = false;
			}
			//lang
		}

		if (move.getCapturePosition() != null) {
			board.capture(move.getCapturePosition());
		}
		board.boardMapRemove(board.getPosition(this));
		board.boardMapAdd(move.getDestination(), this);
	}


	// /**
	//  * @return boolean
	//  */
	// public boolean checkCheck() {

	// 	for (Piece piece : board.getBoardMap().values()) {

	// 		for (Move move : piece.getMoves()) {
	// 			if (move.getDestination() == board.getPosition(this)) {
	// 				return true;
	// 			}
	// 		}
	// 	}
	// 	return false;
	// }


	// /**
	//  * @param test_move
	//  * @param test_piece
	//  * @return boolean
	//  */
	// public boolean checkCheck(Move test_move, Piece test_piece) {
	// 	/*
	// 	 * TODO
	// 	 * 
	// 	 * 
	// 	 * List<Piece> piecesCopy = new ArrayList<Piece>();
	// 	 * 
	// 	 * 
	// 	 * // clone board for (Piece piece : Board.pieces) { piecesCopy.add(piece.getClone()); }
	// 	 * 
	// 	 * // simulate move for (Piece piece : piecesCopy) { if (piece.position.getX() ==
	// 	 * test_piece.position.x && piece.position.getY() == test_piece.position.getY()) {
	// 	 * piece.makeMove(test_move); break; } }
	// 	 * 
	// 	 * for (Piece piece : piecesCopy) { for (Move move : piece.getMoves(false)) { if (move.destinationX
	// 	 * == position.getX() && move.destinationY == position.getY()) { return true; } } }
	// 	 */
	// 	return false;
	// }

	@Override
	public Image getImage() {
		if (isWhite) {
			return whiteImage;
		} else {
			return blackImage;
		}
	}

	public boolean checkCheck(){
		log.debug("check check King");
		Position position = getPosition();
		log.debug(position);
		// for (Piece piece : board.getBoardMap().values()) {
		// 	log.debug(position);
		// 	for (Move move : piece.getMoves(false)) {
		// 		if (move.getCapturePosition() != null && move.getCapturePosition().equals(position)) {
		// 			return true;
		// 		}
		// 	}
		// }
		// return false;

		
		return board.getBoardMap().values().parallelStream().anyMatch(piece ->
			Arrays.stream(piece.getMoves(false)).parallel().map(move -> move.getCapturePosition()).filter(position2 -> position2 != null).anyMatch(position2 -> position2.equals(position))
		);
	}

	public boolean checkCheckMate(){
		return board.getBoardMap().values().parallelStream().anyMatch(piece -> 
			piece.isWhite == this.isWhite && piece.getMoves() != null
		);
	}

	@Override
	public Piece getClone(Board board) {
		King king = new King(board, isWhite, rochade);
		if (isWhite) {
			board.setKingWhite(king);
		}
		else{
			board.setKingBlack(king);
		}
		return king;
	}

}
