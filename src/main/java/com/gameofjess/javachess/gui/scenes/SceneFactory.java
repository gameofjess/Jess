package com.gameofjess.javachess.gui.scenes;

import java.io.IOException;

import com.gameofjess.javachess.gui.controller.IController;

import javafx.scene.Scene;

public class SceneFactory {
    private final SceneType type;
    private IController controller;

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


    public IController getController() {
        return controller;
    }
}
