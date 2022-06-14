package com.gameofjess.javachess.gui.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.gui.scenes.SceneFactory;
import com.gameofjess.javachess.gui.scenes.SceneType;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class JoinMenuController extends MenuController {

    private static final Logger log = LogManager.getLogger(JoinMenuController.class);

    @FXML
    protected TextField address;

    /**
     * Gets user inputs and calls the connect method.
     *
     */
    public void joinGame() throws IOException {
        final String[] splittedAddress = address.getText().split(":");
        final String host;
        final int port;

        if (splittedAddress.length == 2) {
            host = splittedAddress[0];
            port = Integer.parseInt(splittedAddress[1]);
            log.debug("Parsed address {} and port {}.", host, port);
        } else if (splittedAddress.length == 1) {
            host = splittedAddress[0];
            port = 8887;
            log.debug("Parsed address {}. Default port {} will be used.", host, port);
        } else {
            log.error("Invalid address information!");
            return;
        }

        String usernameString = username.getText();

        log.debug("Joining server on {}:{} with username {}", host, port, usernameString);
        log.debug("Switching to game scene.");

        SceneFactory sceneFactory = new SceneFactory(SceneType.GAME);
        Scene gameScene = sceneFactory.getScene().getFXScene();
        GameController gameController = (GameController) sceneFactory.getController();

        if (connect(host, port, usernameString, gameController)) {
            switchGameScene(gameScene, gameController);
        }
    }


}
