package com.gameofjess.javachess.mainpackage;

import java.net.URL;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Logger log = LogManager.getLogger(Main.class);

    /**
     * Starts the GUI.
     */
    @Override
    public void start(Stage stage) {
        try {
            log.debug("Loading menu!");
            URL fxmlFileUrl = getClass().getClassLoader().getResource("menu.fxml");
            Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlFileUrl));
            Scene scene = new Scene(root);

            log.debug("Setting window options.");
            stage.setTitle("Jess");
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/bKnight.png"))));
            stage.setScene(scene);
            stage.setMinHeight(600);
            stage.setMinWidth(400);
            stage.show();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
