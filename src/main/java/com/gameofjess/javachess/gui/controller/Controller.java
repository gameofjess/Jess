package com.gameofjess.javachess.gui.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.gui.scenes.SceneFactory;
import com.gameofjess.javachess.gui.scenes.SceneType;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Controller {

    private static final Logger log = LogManager.getLogger(Controller.class);

    /**
     * Set the scene to the host scene.
     *
     * @param event GUI ActionEvent
     * @throws IOException If the corresponding fxml file is not found.
     */

    public MenuController switchHostScene(ActionEvent event) throws IOException {
        return (MenuController) switchScene(SceneType.HOST, event);
    }

    /**
     * Set the scene to the join scene.
     *
     * @param event GUI ActionEvent
     * @throws IOException If the corresponding fxml file is not found.
     */

    public MenuController switchJoinScene(ActionEvent event) throws IOException {
        return (MenuController) switchScene(SceneType.JOIN, event);
    }

    /**
     * Set the scene to the game scene.
     *
     * @param event GUI ActionEvent
     * @throws IOException If the corresponding fxml file is not found.
     */
    public GameController switchGameScene(ActionEvent event) throws IOException {
        return (GameController) switchScene(SceneType.GAME, event);
    }

    /**
     * Set the scene to the type given.
     *
     * @param type Type of scene.
     * @param event GUI ActionEvent.
     * @throws IOException If the fxml file cannot be loaded.
     */

    private Controller switchScene(SceneType type, ActionEvent event) throws IOException {
        SceneFactory factory = new SceneFactory(type);
        Scene scene = factory.getScene();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        log.debug("Switched scene to {}", type.toString());
        return factory.getController();
    }

    void switchScene(Scene scene, ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        log.debug("Switched scene to {}", scene);
    }

}
