package org.example.javachess.chesslogic;


import org.example.javachess.chesslogic.pieces.Piece;

public class Move {
    public int destinationX;
    public int destinationY;
    public Piece capture = null;
    public boolean rochade = false;
    public boolean enpassant = false;

    public Move(int destinationX, int destinationY) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public Move(boolean enpassant, int destinationX, int destinationY) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public Move(int destinationX, int destinationY, Piece capture) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.capture = capture;
    }

    public Move(int destinationX, int destinationY, boolean rochade) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.rochade = rochade;
    }
}
