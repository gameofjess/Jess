package com.gameofjess.javachess.gui.scenes;

public enum SceneType {
    JOIN("Join"), HOST("Host"), GAME("Game");

    private String name;

    SceneType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
