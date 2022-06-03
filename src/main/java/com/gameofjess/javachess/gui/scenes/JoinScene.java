package com.gameofjess.javachess.gui.scenes;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.gui.controller.IController;
import com.gameofjess.javachess.gui.controller.MenuController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class JoinScene {
    private static final Logger log = LogManager.getLogger(JoinScene.class);
    private static final URL fxmlFileURL = JoinScene.class.getClassLoader().getResource("menu.fxml");
    private final IController controller;
    private final Scene scene;

    JoinScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(fxmlFileURL));
        scene = new Scene(loader.load());
        log.debug("Switching scene to " + fxmlFileURL.toExternalForm());
        controller = loader.getController();
    }

    Scene getScene() {
        return scene;
    }

    public MenuController getController() {
        return (MenuController) controller;
    }
}
