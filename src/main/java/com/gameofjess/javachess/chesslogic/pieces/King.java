package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.helper.game.Pieces;

import javafx.scene.image.Image;

public class King extends Piece {
	private static final Logger log = LogManager.getLogger(Board.class);

	boolean castling = true;

	/**
	 * Constructor
	 * @param Board
	 * @param isWhite
	 */
	public King(Board Board, boolean isWhite) {
		super(Board, isWhite);
		super.fen = "k";
	}

	/**
	 * Clone constructor
	 * @param Board
	 * @param isWhite
	 * @param rochade
	 */
	public King(Board Board, boolean isWhite, boolean rochade) {
		super(Board, isWhite);
		this.castling = rochade;
	}

	@Override
	public Move[] getMoves(boolean checking) {
		log.trace("getting moves king");
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
		if (castling && board.getBoardMap().get(new Position(1, position.getY())) == null && board.getBoardMap().get(new Position(2, position.getY())) == null) {
			Piece rook = board.getBoardMap().get(new Position(0, position.getY()));
			if (rook instanceof Rook) {
				if (((Rook) rook).rochade) {
					Move test_move = new Move(position, new Position(1, position.getY()), true);
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
		if (castling && board.getBoardMap().get(new Position(6, position.getY())) == null && board.getBoardMap().get(new Position(5, position.getY())) == null
				&& board.getBoardMap().get(new Position(4, position.getY())) == null) {
			Piece rook = board.getBoardMap().get(new Position(7, position.getY()));
			if (rook instanceof Rook) {
				if (((Rook) rook).rochade) {
					Move test_move = new Move(position, new Position(5, position.getY()), true);
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

	@Override
	public void makeMove(Move move) {
		log.debug("current board:\n{}", board);
		log.debug("making move");
		castling = false;

		if (move.getCastling()) {
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
				king.castling = false;

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
				king.castling = false;
			}
			//lang
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
	 * @return
	 */
	public boolean checkCheck(){
		log.trace("check check King");
		Position position = getPosition();
		log.trace(position);

		return board.getBoardMap().values().stream().parallel().anyMatch(piece ->
			Arrays.stream(piece.getMoves(false)).parallel().map(move -> move.getCapturePosition()).filter(position2 -> position2 != null).anyMatch(position2 -> position2.equals(position))
		);
	}

	/**
	 * Check if the King is Checkmate
	 * @return
	 */
	public boolean checkCheckMate(){
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
