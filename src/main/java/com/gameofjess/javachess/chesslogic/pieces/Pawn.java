package com.gameofjess.javachess.chesslogic.pieces;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.helper.game.Pieces;

import javafx.scene.image.Image;


public class Pawn extends Piece {
	private static final Logger log = LogManager.getLogger(Pawn.class);

	boolean enpassant = false;

	/** returns if the Pawn can be captured by enpassant
	 * @return the enpassant
	 */
	public boolean isEnpassant() {
		return enpassant;
	}

	/**
	 * Constructor
	 * @param Board to be linked
	 * @param isWhite color of the piece
	 */
	public Pawn(Board Board, boolean isWhite) {
		super(Board, isWhite);
		super.fen = "p";
	}

	/**
	 * Constructor
	 * @param Board to be linked
	 * @param isWhite color of the piece
	 * @param enpassant status
	 */
	public Pawn(Board Board, boolean isWhite, boolean enpassant) {
		super(Board, isWhite);
		this.enpassant = enpassant;
	}

	@Override
	public Move[] getMoves(boolean checking) {
		log.trace("getting moves pawn");
		List<Move> moves = new ArrayList<>();
		Position position = board.getPosition(this);


		Position testPosition;
		Piece testLocation;
		if (isWhite) {
			//one forward
			testPosition = new Position(position.getX(), position.getY() + 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					if (testPosition.getY() == 7) {
						moves.add(new Move(position, testPosition, Bishop.class.getName()));
						moves.add(new Move(position, testPosition, Knight.class.getName()));
						moves.add(new Move(position, testPosition, Queen.class.getName()));
						moves.add(new Move(position, testPosition, Rook.class.getName()));
					}
					else {
						moves.add(testMove);
					}
				}
				//two forward
				testPosition = new Position(position.getX(), position.getY() + 2);
				testLocation = board.getBoardMap().get(testPosition);
				if (position.getY() == 1 && testLocation == null) {
					testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
				}
			}
			//beat right
			testPosition = new Position(position.getX() + 1, position.getY() + 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation != null && !testLocation.isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (testPosition.getY() == 7) {
					moves.add(new Move(position, testPosition, Bishop.class.getName()));
					moves.add(new Move(position, testPosition, Knight.class.getName()));
					moves.add(new Move(position, testPosition, Queen.class.getName()));
					moves.add(new Move(position, testPosition, Rook.class.getName()));
				}
				else if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
			//beat left
			testPosition = new Position(position.getX() - 1, position.getY() + 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation != null && !testLocation.isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					if (testPosition.getY() == 7) {
						moves.add(new Move(position, testPosition, Bishop.class.getName()));
						moves.add(new Move(position, testPosition, Knight.class.getName()));
						moves.add(new Move(position, testPosition, Queen.class.getName()));
						moves.add(new Move(position, testPosition, Rook.class.getName()));
					}
					else {
						moves.add(testMove);
					}
				}
			}

            // enpassant right white
			testPosition = new Position(position.getX() + 1, position.getY() + 1);
			testLocation = board.getBoardMap().get(testPosition);
			Position enpassantPosition = new Position(position.getX() + 1, position.getY());
			Piece enpassantLocation = board.getBoardMap().get(enpassantPosition);

			if (testLocation == null && enpassantLocation instanceof Pawn && ((Pawn) enpassantLocation).isEnpassant()
					&& enpassantLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, enpassantPosition, true);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}

            // enpassant links weiss
			testPosition = new Position(position.getX() - 1, position.getY() + 1);
			testLocation = board.getBoardMap().get(testPosition);
			enpassantPosition = new Position(position.getX() - 1, position.getY());
			enpassantLocation = board.getBoardMap().get(enpassantPosition);

			if (testLocation == null && enpassantLocation instanceof Pawn && ((Pawn) enpassantLocation).isEnpassant()
					&& enpassantLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, enpassantPosition, true);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}

		// SCHWARZ
		else {
			//one up
			testPosition = new Position(position.getX(), position.getY() - 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation == null) {
				Move testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					if (testPosition.getY() == 7) {
						moves.add(new Move(position, testPosition, Bishop.class.getName()));
						moves.add(new Move(position, testPosition, Knight.class.getName()));
						moves.add(new Move(position, testPosition, Queen.class.getName()));
						moves.add(new Move(position, testPosition, Rook.class.getName()));
					}
					else {
						moves.add(testMove);
					}
				}
				//two forward
				testPosition = new Position(position.getX(), position.getY() - 2);
				testLocation = board.getBoardMap().get(testPosition);
				if (position.getY() == 6 && testLocation == null) {
					testMove = new Move(position, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
				}
			}
			//beat right
			testPosition = new Position(position.getX() + 1, position.getY() - 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation != null && testLocation.isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					if (testPosition.getY() == 7) {
						moves.add(new Move(position, testPosition, Bishop.class.getName()));
						moves.add(new Move(position, testPosition, Knight.class.getName()));
						moves.add(new Move(position, testPosition, Queen.class.getName()));
						moves.add(new Move(position, testPosition, Rook.class.getName()));
					}
					else {
						moves.add(testMove);
					}
				}
			}
			//beat left
			testPosition = new Position(position.getX() - 1, position.getY() - 1);
			testLocation = board.getBoardMap().get(testPosition);
			if (testLocation != null && testLocation.isWhite) {
				Move testMove = new Move(position, testPosition, testPosition);
				if (!checking || !checkCheckMove(testMove)) {
					if (testPosition.getY() == 7) {
						moves.add(new Move(position, testPosition, Bishop.class.getName()));
						moves.add(new Move(position, testPosition, Knight.class.getName()));
						moves.add(new Move(position, testPosition, Queen.class.getName()));
						moves.add(new Move(position, testPosition, Rook.class.getName()));
					}
					else {
						moves.add(testMove);
					}
				}
			}
            // enpassant right white
			testPosition = new Position(position.getX() + 1, position.getY() - 1);
			testLocation = board.getBoardMap().get(testPosition);
			Position enpassantPosition = new Position(position.getX() + 1, position.getY());
			Piece enpassantLocation = board.getBoardMap().get(enpassantPosition);

			if (testLocation == null && enpassantLocation instanceof Pawn && ((Pawn) enpassantLocation).isEnpassant()
					&& enpassantLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, enpassantPosition, true);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}

            // enpassant links weiss
			testPosition = new Position(position.getX() - 1, position.getY() - 1);
			testLocation = board.getBoardMap().get(testPosition);
			enpassantPosition = new Position(position.getX() - 1, position.getY());
			enpassantLocation = board.getBoardMap().get(enpassantPosition);

			if (testLocation == null && enpassantLocation instanceof Pawn && ((Pawn) enpassantLocation).isEnpassant()
					&& enpassantLocation.isWhite() != isWhite) {
				Move testMove = new Move(position, testPosition, enpassantPosition, true);
				if (!checking || !checkCheckMove(testMove)) {
					moves.add(testMove);
				}
			}
		}
		return moves.toArray(new Move[0]);
	}

	@Override
	public void makeMove(Move move){
		log.trace("making move");
		enpassant = move.getEnpassant();
		if (move.getCapturePosition() != null) {
			board.capture(move.getCapturePosition());
		}
		if (move.getPromotion() == null){
			enpassant = move.getDestination().getY() - move.getOrigin().getY() != 1;
			board.boardMapRemove(board.getPosition(this));
			board.boardMapAdd(move.getDestination(), this);
		}
		else {
            board.boardMapRemove(getPosition());
			try {
				Piece piece = (Piece) Class.forName(move.getPromotion()).getConstructor(Board.class, boolean.class).newInstance(board, this.isWhite);
                board.boardMapAdd(move.getDestination(), piece);
				log.debug(board);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                throw new RuntimeException(e);
			}
		}
	}

    @Override
    public Pieces getEnumValue() {
        return Pieces.PAWN;
    }

	@Override
	public Image getImage() {
        return getEnumValue().getImage(isWhite);
	}

	@Override
	public Piece getClone(Board board) {
		return new Pawn(board, isWhite, enpassant);
	}


}
