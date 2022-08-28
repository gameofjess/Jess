package com.gameofjess.javachess.gui.scenes;

import com.gameofjess.javachess.gui.controller.Controller;

import java.io.IOException;

/**
 * This factory is used to construct the various scenes of the application.
 */
public class SceneFactory {
    private final SceneType type;
    private Controller controller;

    /**
     * Constructs a SceneFactory
     * 
     * @param type Type of scene to create
     */
    public SceneFactory(SceneType type) {
        this.type = type;
    }

    public IScene getScene() throws IOException {
        switch (type) {
            case JOIN -> {
                JoinScene scene = new JoinScene();
                controller = scene.getController();
                return scene;
            }
            case HOST -> {
                HostScene scene = new HostScene();
                controller = scene.getController();
                return scene;
            }
            case PUBLIC -> {
                PublicScene scene = new PublicScene();
                controller = scene.getController();
                return scene;
            }
            case GAME -> {
                GameScene scene = new GameScene();
                controller = scene.getController();
                return scene;
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
