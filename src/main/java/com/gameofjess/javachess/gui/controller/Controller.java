package com.gameofjess.javachess.gui.controller;

import com.gameofjess.javachess.gui.scenes.SceneFactory;
import com.gameofjess.javachess.gui.scenes.SceneType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Controller {

    private static final Logger log = LogManager.getLogger(Controller.class);

    /**
     * Set the scene to the host scene.
     *
     * @throws IOException If the corresponding fxml file is not found.
     */

    public MenuController switchHostScene() throws IOException {
        return (MenuController) switchScene(SceneType.HOST);
    }

    /**
     * Set the scene to the join scene.
     *
     * @throws IOException If the corresponding fxml file is not found.
     */

    public MenuController switchJoinScene() throws IOException {
        return (MenuController) switchScene(SceneType.JOIN);
    }

    /**
     * Set the scene to the public server scene.
     *
     * @throws IOException If the corresponding fxml file is not found.
     */

    public MenuController switchPublicScene() throws IOException {
        return (MenuController) switchScene(SceneType.PUBLIC);
    }

    /**
     * Switches to pregenerated gameScene and sets onCloseRequest-EventHandler.
     *
     * @param scene Scene to switch to.
     */
    void switchGameScene(Scene scene, GameController gameController) {
        Stage stage = getStage();

        stage.setScene(scene);

        stage.setMinWidth(750);
        stage.setMinHeight(750);

        log.debug("Setting on close request!");
        stage.setOnCloseRequest(windowEvent -> {
            gameController.closeConnection("Application closed!");
        });

        log.debug("Switched scene to {}", scene);
    }

    /**
     * Set the scene to the type given.
     *
     * @param type Type of scene.
     * @throws IOException If the fxml file cannot be loaded.
     */

    private Controller switchScene(SceneType type) throws IOException {
        SceneFactory factory = new SceneFactory(type);
        Scene scene = factory.getScene().getFXScene();
        Stage stage = getStage();

        stage.setScene(scene);

        stage.setMinWidth(400);
        stage.setMinHeight(600);

        stage.setWidth(400);
        stage.setHeight(600);

        log.debug("Switched scene to {}", type.toString());
        return factory.getController();
    }

    private Stage getStage() {
        return (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
    }

}
