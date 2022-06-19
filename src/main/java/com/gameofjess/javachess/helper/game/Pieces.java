package com.gameofjess.javachess.helper.game;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.gameofjess.javachess.helper.configuration.StandardConfig;
import com.gameofjess.javachess.helper.configuration.ConfigLoader;

import javafx.scene.image.Image;

public enum Pieces {

    PAWN("bPawn.png", "wPawn.png"), ROOK("bRook.png", "wRook.png"), KNIGHT("bKnight.png", "wKnight.png"), BISHOP("bBishop.png", "wBishop.png"), KING("bKing.png",
            "wKing.png"), QUEEN("bQueen.png", "wQueen.png");

    private final Image blackImage;
    private final Image whiteImage;

    Pieces(String blackImageFileName, String whiteImageFileName) {
        StandardConfig config;
        try {
            config = (StandardConfig) new ConfigLoader().loadConfig(new File("config.json"), StandardConfig.class);
        } catch (IOException e) {
            config = new StandardConfig();
        }

        String path;

        if (!config.getIconPath().equals("")) {
            path = config.getIconPath();
            File blackImageFile = new File(path + blackImageFileName);
            blackImage = new Image(Objects.requireNonNull(blackImageFile.toURI().toString()));
            File whiteImageFile = new File(path + whiteImageFileName);
            whiteImage = new Image(Objects.requireNonNull(whiteImageFile.toURI().toString()));
        } else {
            path = "/icons/";
            blackImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + blackImageFileName)));
            whiteImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + whiteImageFileName)));
        }
    }

    public Image getImage(boolean isWhite) {
        if (isWhite) {
            return whiteImage;
        } else {
            return blackImage;
        }
    }



}
