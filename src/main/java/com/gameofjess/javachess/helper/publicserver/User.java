package com.gameofjess.javachess.helper.publicserver;

import com.gameofjess.javachess.helper.game.Color;

import java.util.UUID;

public class User {

    private final String username;
    private transient final UUID uuid;
    private transient Color gameColor;

    User(String username, UUID uuid) {
        this.username = username;
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Color getGameColor() {
        return gameColor;
    }

    public void setGameColor(Color color) {
        this.gameColor = color;
    }
}
