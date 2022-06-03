package com.gameofjess.javachess.chesslogic;


import com.gameofjess.javachess.chesslogic.pieces.Piece;

public class Move {
	/**
	 * This Class represents a Move from a Piece on the Chessboard,
	 * including the Variations: Capture, Castling and Enpassant
	 */

//TODO geter seters

	public Position destination;
    public Piece capture = null;
    public boolean rochade = false;
    public boolean enpassant = false;

    public Move(Position destination) {
        this.destination = destination;
	}

    public Move(boolean enpassant, Position destination) {
        this.destination = destination;
		this.enpassant = enpassant;
    }

    public Move(Position destination, Piece capture) {
		this.destination = destination;
        this.capture = capture;
    }

    public Move(Position destination, boolean rochade) {
		this.destination = destination;
        this.rochade = rochade;
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
	public Piece getCapture() {
		return capture;
	}

	public boolean getRochade() {
		return rochade;
	}

	public boolean getEnpassant() {
		return enpassant;
	}
}
