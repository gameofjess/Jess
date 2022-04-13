package org.example.javachess.chess_logic;


import org.example.javachess.chess_logic.pieces.Piece;

public class Move {
    public int destinationX;
    public int destinationY;
    public Piece capture = null;
    public Move rochade = null;

    public Move(int destinationX, int destinationY){
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public Move(int destinationX, int destinationY, Piece capture){
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.capture = capture;
    }

    public Move(int destinationX, int destinationY, Move rochade){
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.rochade = rochade;
    }
}
