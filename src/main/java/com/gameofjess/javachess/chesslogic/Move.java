package com.gameofjess.javachess.chesslogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Move {
	/**
	 * This Class represents a Move from a Piece on the Chessboard,
	 * including the Variations: Capture, Castling and Enpassant
	 */

	private static final Logger log = LogManager.getLogger(Move.class);

	private Position origin;
	private Position destination;
	private Position capturePosition = null;
	private boolean castling = false;
	private boolean enpassant = false;
	private String promotion = null;

	/**
	 * Constructor for normal Moves
	 * @param origin of the move
	 * @param destination of the move
	 */
    public Move(Position origin, Position destination) {
		log.trace("Creating normal move");
		this.origin = origin;
        this.destination = destination;
	}

	/**
	 * Constructor for Enpassant moves
	 * @param origin of the move
	 * @param destination of the move
	 * @param capturePosition of the piece to be captured
	 * @param enpassant boolean if the move is enpassant
	 */
    public Move(Position origin, Position destination, Position capturePosition, boolean enpassant) {
		log.trace("Creating enpassant move");
        this.origin = origin;
		this.destination = destination;
		this.capturePosition = capturePosition;
		this.enpassant = enpassant;
    }

	/**
	 * Constructor for Capture moves
	 * @param origin of the move
	 * @param destination of the move
	 * @param capturePosition of the piece to be captured
	 */
    public Move(Position origin, Position destination, Position capturePosition) {
		log.trace("Creating capture move");
		this.origin = origin;
		this.destination = destination;
        this.capturePosition = capturePosition;
    }

	/**
	 * Constructor for Castling moves
	 * @param origin of the move
	 * @param destination of the move
	 * @param castling boolean if castling is happening
	 */
    public Move(Position origin, Position destination, boolean castling) {
		log.trace("Creating castling move");
		this.origin = origin;
		this.destination = destination;
        this.castling = castling;
    }

	/**
	 * Constructor for Promotion moves
	 * @param origin of the move
	 * @param destination of the move
	 * @param promotion class string of the promoted piece
	 */
	public Move(Position origin, Position destination, String promotion) {
		log.trace("Creating promotion move");
		this.origin = origin;
		this.destination = destination;
        this.promotion = promotion;
    }

	/**
	 * @return the destination
	 */
	public Position getDestination() {
		return destination;
	}

	/**
	 * @return the capture
	 */
	public Position getCapturePosition() {
		return capturePosition;
	}

	/**
	 * @return the move from the rook involved in castling or null
	 */
	public boolean getCastling() {
		return castling;
	}

	/**
	 *
	 * @return if a move is an enpassant move
	 */
	public boolean getEnpassant() {
		return enpassant;
	}

	/**
	 * @return the promotion
	 */
	public String getPromotion() {
		return promotion;
	}

    public void changePromotion(String className) {
        this.promotion = className;
    }

	/**
	 * @return the origin of the given move
	 */
	public Position getOrigin() {
		return origin;
	}

	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
			return true;
        } else if (obj instanceof Move test) {
            if (!(capturePosition == null) && !(test.getCapturePosition() == null)) {
                return origin.equals(test.getOrigin()) && destination.equals(test.getDestination()) && capturePosition.equals(test.getCapturePosition())
                        && castling == test.getCastling() && enpassant == test.getEnpassant();
            } else if (capturePosition == null && test.getCapturePosition() == null) {
                return origin.equals(test.getOrigin()) && destination.equals(test.getDestination()) && castling == test.getCastling() && enpassant == test.getEnpassant();
			}
		}
        return false;
	}

}
