package com.gameofjess.javachess.gui.scenes;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.gui.controller.Controller;
import com.gameofjess.javachess.gui.controller.GameController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * This class provides an easy-to-use wrapper for the corresponding JavaFX-Scene.
 */
public class GameScene {

    private static final Logger log = LogManager.getLogger(GameScene.class);
    private static final URL fxmlFileURL = GameScene.class.getClassLoader().getResource("play.fxml");
    private final Controller controller;
    private final Scene scene;

    /**
     * Constructs a new GameScene.
     * 
     * @throws IOException as seen in the FXMLLoader's load method.
     */
    GameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(fxmlFileURL));
        scene = new Scene(loader.load());
        log.debug("Switching scene to {}.", fxmlFileURL.toExternalForm());
        controller = loader.getController();
    }

    /**
     * Gets the JavaFX-Scene object.
     * 
     * @return JavaFX-Scene
     */
    Scene getScene() {
        return scene;
    }

    /**
     * Gets the corresponding controller.
     * 
     * @return corresponding controller.
     */
    public GameController getController() {
        return (GameController) controller;
    }

}
