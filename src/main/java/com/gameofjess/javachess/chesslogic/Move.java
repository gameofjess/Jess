package com.gameofjess.javachess.chesslogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Move {
	/**
	 * This Class represents a Move from a Piece on the Chessboard,
	 * including the Variations: Capture, Castling and Enpassant
	 */

	private static final Logger log = LogManager.getLogger(Move.class);

	Position origin;
	Position destination;
    Position capturePosition = null;
    boolean castling = false;
    boolean enpassant = false;
	String promotion = null;

	/**
	 * Constructor for normal Moves
	 * @param origin
	 * @param destination
	 */
    public Move(Position origin, Position destination) {
		this.origin = origin;
        this.destination = destination;
	}

	/**
	 * Constructor for Enpassant moves
	 * @param origin
	 * @param destination
	 * @param capturePosition
	 * @param enpassant
	 */
    public Move(Position origin, Position destination, Position capturePosition, boolean enpassant) {
        this.origin = origin;
		this.destination = destination;
		this.capturePosition = capturePosition;
		this.enpassant = enpassant;
    }

	/**
	 * Constructor for Capture moves
	 * @param origin
	 * @param destination
	 * @param capturePosition
	 */
    public Move(Position origin, Position destination, Position capturePosition) {
		this.origin = origin;
		this.destination = destination;
        this.capturePosition = capturePosition;
    }

	/**
	 * Constructor for Castling moves
	 * @param origin
	 * @param destination
	 * @param castling
	 */
    public Move(Position origin, Position destination, boolean castling) {
		this.origin = origin;
		this.destination = destination;
        this.castling = castling;
    }

	/**
	 * Constructor for Promotion moves
	 * @param origin
	 * @param destination
	 * @param promotion
	 */
	public Move(Position origin, Position destination, String promotion) {
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
