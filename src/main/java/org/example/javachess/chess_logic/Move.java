package org.example.javachess.chess_logic;


import org.example.javachess.chess_logic.pieces.Piece;

public class Move {
    public int destinationX;
    public int destinationy;
    public Piece capture = null;
    public Move rochade = null;

    public Move(int destinationX, int destinationy){
        this.destinationX = destinationX;
        this.destinationy = destinationy;
    }

    public Move(int destinationX, int destinationy, Piece capture){
        this.destinationX = destinationX;
        this.destinationy = destinationy;
        this.capture = capture;
    }

    public Move(int destinationX, int destinationy, Move rochade){
        this.destinationX = destinationX;
        this.destinationy = destinationy;
        this.rochade = rochade;
    }
}
