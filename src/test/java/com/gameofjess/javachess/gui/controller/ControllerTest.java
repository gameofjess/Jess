package com.gameofjess.javachess.gui.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.gameofjess.javachess.gui.scenes.SceneFactory;
import com.gameofjess.javachess.gui.scenes.SceneType;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

@ExtendWith(ApplicationExtension.class)
public class ControllerTest {

    /**
     * Needed to test JavaFX...
     */
    @Start
    private void start(Stage stage) {
        Parent root = new Label("Testing...");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Testing...");
        stage.show();
    }

    /**
     * Tests switching to host and join scene.
     */
    @Test
    void switchHostJoinSceneTest() {
        Platform.runLater(() -> {
            Controller controller = new Controller();
            try {
                assertTrue(controller.switchHostScene() instanceof HostMenuController);
                assertTrue(controller.switchJoinScene() instanceof JoinMenuController);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Tests switching to game scene.
     */
    @Test
    void switchGameSceneTest() {
        Platform.runLater(() -> {
            Controller controller = new Controller();
            Scene scene;
            try {
                scene = new SceneFactory(SceneType.GAME).getScene().getFXScene();
                controller.switchGameScene(scene, new GameController());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            assertEquals(Objects.requireNonNull(Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null)).getScene(), scene);
        });
    }
}
