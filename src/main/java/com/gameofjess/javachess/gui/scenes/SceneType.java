package com.gameofjess.javachess.gui.scenes;

/**
 * This enum specifies the possible scenes to construct with the SceneFactory.
 */
public enum SceneType {
    JOIN("Join"), HOST("Host"), GAME("Game"), PUBLIC("Public");

    private final String name;

    SceneType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
