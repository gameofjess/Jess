package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;


public class Pawn extends Piece {

	private static final Image whiteImage = new Image(Objects.requireNonNull(Pawn.class.getResourceAsStream("/icons/wPawn.png")));
	private static final Image blackImage = new Image(Objects.requireNonNull(Pawn.class.getResourceAsStream("/icons/bPawn.png")));

	boolean enpassant = false;

	/**
	 * @return the enpassant
	 */
	public boolean isEnpassant() {
		return enpassant;
	}

	public Pawn(Board Board, boolean isWhite) {
		super(Board, isWhite);
		super.fen = "p";
	}

	public Pawn(Board Board, boolean isWhite, boolean enpassant) {
		super(Board, isWhite);
		this.enpassant = enpassant;
	}


	/**
	 * @param checking
	 * @return Move[]
	 */
	@Override
	public Move[] getMoves(boolean checking) {
		List<Move> moves = new ArrayList<Move>();
		Position position = board.getPosition(this);


		Position testposition;
		Piece testlocation;
		if (isWhite) {
			//eins vor
			testposition = new Position(position.getX(), position.getY() + 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
				//zwei vor
				testposition = new Position(position.getX(), position.getY() + 2);
				testlocation = board.getBoardMap().get(testposition);
				if (position.getY() == 1 && testlocation == null) {
					testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
				}
			}
			//schlagen rechts
			testposition = new Position(position.getX() + 1, position.getY() + 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation != null && !testlocation.isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			//schlagen links
			testposition = new Position(position.getX() - 1, position.getY() + 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation != null && !testlocation.isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}

			// enpassant rechts weiß
			testposition = new Position(position.getX() + 1, position.getY() + 1);
			testlocation = board.getBoardMap().get(testposition);
			Position enpassantposition = new Position(position.getX() + 1, position.getY());
			Piece enpassantlocation = board.getBoardMap().get(enpassantposition);

			if (testlocation == null && enpassantlocation != null && enpassantlocation instanceof Pawn && ((Pawn) enpassantlocation).isEnpassant()
					&& ((Pawn) enpassantlocation).isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, enpassantposition, true);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}

			// enpassant links weiß
			testposition = new Position(position.getX() - 1, position.getY() + 1);
			testlocation = board.getBoardMap().get(testposition);
			enpassantposition = new Position(position.getX() - 1, position.getY());
			enpassantlocation = board.getBoardMap().get(enpassantposition);

			if (testlocation == null && enpassantlocation != null && enpassantlocation instanceof Pawn && ((Pawn) enpassantlocation).isEnpassant()
					&& ((Pawn) enpassantlocation).isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, enpassantposition, true);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}

		// SCHWARZ
		else {
			//eins vor
			testposition = new Position(position.getX(), position.getY() - 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
				//zwei vor
				testposition = new Position(position.getX(), position.getY() - 2);
				testlocation = board.getBoardMap().get(testposition);
				if (position.getY() == 6 && testlocation == null) {
					testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
				}
			}
			//schlagen rechts
			testposition = new Position(position.getX() + 1, position.getY() - 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation != null && testlocation.isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			//schalgen links
			testposition = new Position(position.getX() - 1, position.getY() - 1);
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation != null && testlocation.isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			// enpassant rechts weiß
			testposition = new Position(position.getX() + 1, position.getY() - 1);
			testlocation = board.getBoardMap().get(testposition);
			Position enpassantposition = new Position(position.getX() + 1, position.getY());
			Piece enpassantlocation = board.getBoardMap().get(enpassantposition);

			if (testlocation == null && enpassantlocation != null && enpassantlocation instanceof Pawn && ((Pawn) enpassantlocation).isEnpassant()
					&& ((Pawn) enpassantlocation).isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, enpassantposition, true);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}

			// enpassant links weiß
			testposition = new Position(position.getX() - 1, position.getY() - 1);
			testlocation = board.getBoardMap().get(testposition);
			enpassantposition = new Position(position.getX() - 1, position.getY());
			enpassantlocation = board.getBoardMap().get(enpassantposition);

			if (testlocation == null && enpassantlocation != null && enpassantlocation instanceof Pawn && ((Pawn) enpassantlocation).isEnpassant()
					&& ((Pawn) enpassantlocation).isWhite() != isWhite) {
				Move testmove = new Move(position, testposition, enpassantposition, true);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
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
		enpassant = move.getEnpassant();
		if (move.getCapturePosition() != null) {
			board.capture(move.getCapturePosition());
		}
		if (move.getDestination().getY() - move.getOrigin().getY() != 1) {
			enpassant = true;
		} else {
			enpassant = false;
		}
		board.boardMapRemove(board.getPosition(this));
		board.boardMapAdd(move.getDestination(), this);
		System.out.println(board);
	}

	@Override
	public Image getImage() {
		if (isWhite) {
			return whiteImage;
		} else {
			return blackImage;
		}
	}

	@Override
	public Piece getClone(Board board) {
		return new Pawn(board, isWhite, enpassant);
	}


}
