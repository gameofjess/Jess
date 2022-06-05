package com.gameofjess.javachess.gui.scenes;

import java.io.IOException;

import com.gameofjess.javachess.gui.controller.Controller;

import javafx.scene.Scene;

/**
 * This factory is used to construct the various scenes of the application.
 */
public class SceneFactory {
    private final SceneType type;
    private Controller controller;

    public SceneFactory(SceneType type) {
        this.type = type;
    }

    public Scene getScene() throws IOException {
        switch (type) {
            case JOIN -> {
                JoinScene scene = new JoinScene();
                controller = scene.getController();
                return scene.getScene();
            }
            case HOST -> {
                HostScene scene = new HostScene();
                controller = scene.getController();
                return scene.getScene();
            }
            case GAME -> {
                GameScene scene = new GameScene();
                controller = scene.getController();
                return scene.getScene();
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * @return the controller to the corresponding scene.
     */
    public Controller getController() {
        return controller;
    }
}
