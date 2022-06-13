package com.gameofjess.javachess.helper.game;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.gameofjess.javachess.helper.configuration.Config;
import com.gameofjess.javachess.helper.configuration.ConfigHandler;

import javafx.scene.image.Image;

public enum Pieces {

    PAWN("bPawn.png", "wPawn.png"), ROOK("bRook.png", "wRook.png"), KNIGHT("bKnight.png", "wKnight.png"), BISHOP("bBishop.png", "wBishop.png"), KING("bKing.png",
            "wKing.png"), QUEEN("bQueen.png", "wQueen.png");

    private final Image blackImage;
    private final Image whiteImage;

    Pieces(String blackImageFileName, String whiteImageFileName) {
        Config config;
        try {
            config = new ConfigHandler().loadConfig(new File("config.json"));
        } catch (IOException e) {
            config = new Config();
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
