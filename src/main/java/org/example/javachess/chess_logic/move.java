package org.example.javachess.chess_logic;


import org.example.javachess.chess_logic.pieces.piece;

public class move {
    public int destinationX;
    public int destinationy;
    public piece capture = null;
    public move rochade = null;

    public move(int destinationX, int destinationy){
        this.destinationX = destinationX;
        this.destinationy = destinationy;
    }

    public move(int destinationX, int destinationy, piece capture){
        this.destinationX = destinationX;
        this.destinationy = destinationy;
        this.capture = capture;
    }

    public move(int destinationX, int destinationy, move rochade){
        this.destinationX = destinationX;
        this.destinationy = destinationy;
        this.rochade = rochade;
    }
}
