package com.gameofjess.javachess.helper.game;

import java.util.Objects;

import javafx.scene.image.Image;

public enum Pieces {

    PAWN("bPawn.png", "wPawn.png"), ROOK("bRook.png", "wRook.png"), KNIGHT("bKnight.png", "wKnight.png"), BISHOP("bBishop.png", "wBishop.png"), KING("bKing.png",
            "wKing.png"), QUEEN("bQueen.png", "wQueen.png");

    private final Image blackImage;
    private final Image whiteImage;

    Pieces(String blackImageFileName, String whiteImageFileName) {
        String path = "/icons/";

        blackImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + blackImageFileName)));
        whiteImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + whiteImageFileName)));
    }

    public Image getImage(boolean isWhite) {
        if (isWhite) {
            return whiteImage;
        } else {
            return blackImage;
        }
    }



}
