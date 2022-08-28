package com.gameofjess.javachess.gui.scenes;

import com.gameofjess.javachess.gui.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * This class provides an easy-to-use wrapper for the corresponding JavaFX-Scene.
 */
public class HostScene implements IScene {
    private static final Logger log = LogManager.getLogger(HostScene.class);
    private static final URL fxmlFileURL = HostScene.class.getClassLoader().getResource("fxml/host.fxml");
    private final Controller controller;
    private final Scene scene;

    /**
     * Constructs a new HostScene.
     * 
     * @throws IOException as seen in the FXMLLoader's load method.
     */
    HostScene() throws IOException {
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
    public Scene getFXScene() {
        return scene;
    }

    /**
     * Gets the corresponding controller.
     * 
     * @return corresponding controller.
     */
    public Controller getController() {
        return controller;
    }
}
