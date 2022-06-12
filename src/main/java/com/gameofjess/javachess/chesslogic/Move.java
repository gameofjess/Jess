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
    boolean rochade = false;
    boolean enpassant = false;
	String promotion = null;

    public Move(Position origin, Position destination) {
		//log.debug("Creating move object");
		this.origin = origin;
        this.destination = destination;
	}

    public Move(Position origin, Position destination, Position capturePosition, boolean enpassant) {
		//log.debug("Creating move object with enpassant");
        this.origin = origin;
		this.destination = destination;
		this.capturePosition = capturePosition;
		this.enpassant = enpassant;
    }

    public Move(Position origin, Position destination, Position capturePosition) {
		//log.debug("Creating move object with capture");
		this.origin = origin;
		this.destination = destination;
        this.capturePosition = capturePosition;
    }

    public Move(Position origin, Position destination, boolean rochade) {
		//log.debug("Creating move object with castling");
		this.origin = origin;
		this.destination = destination;
        this.rochade = rochade;
    }

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

	public boolean getRochade() {
		return rochade;
	}

	public boolean getEnpassant() {
		return enpassant;
	}

	/**
	 * @return the promotion
	 */
	public String getPromotion() {
		return promotion;
	}

	/**
	 * @return the origin
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
                        && rochade == test.getRochade() && enpassant == test.getEnpassant();
            } else if (capturePosition == null && test.getCapturePosition() == null) {
                return origin.equals(test.getOrigin()) && destination.equals(test.getDestination()) && rochade == test.getRochade() && enpassant == test.getEnpassant();
			}
		}
        return false;
	}
	
}
