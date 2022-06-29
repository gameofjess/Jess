package com.gameofjess.javachess.gui.controller;

import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import com.gameofjess.javachess.helper.game.Color;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class MenuController extends Controller {

    private static final Logger log = LogManager.getLogger(MenuController.class);

    @FXML
    protected TextField username;
    @FXML
    private Text errorMessage;

    /**
     * Connects to the server.
     *
     * @param address IP address of the server.
     * @param port Port of the server.
     * @param username Username of the client.
     * @param gameController GameController to be used for communication.
     */
    protected boolean connect(String address, int port, String username, GameController gameController) {
        return connect(address, port, username, Color.RANDOM, gameController);
    }

    /**
     * Connects to the server.
     *
     * @param address IP address of the server.
     * @param port Port of the server.
     * @param username Username of the client.
     * @param gameController GameController to be used for communication.
     * @param color Color the user wishes.
     */
    protected boolean connect(String address, int port, String username, Color color, GameController gameController) {
        ConnectionHandler connectionHandler;

        try {
            connectionHandler = new ConnectionHandler(address, port);
        } catch (InvalidHostnameException | InvalidPortException | URISyntaxException e) {
            displayErrorMessage(e.getMessage());
            return false;
        }

        if (username.length() < 4) {
            displayErrorMessage("Invalid or too short username given: \"" + username + "\"");
            return false;
        }

        log.debug("Trying to connect to {} using port {} as {} with color choice {}.", address, port, username, color);
        connectionHandler.setGameController(gameController);
        boolean connected = connectionHandler.connect(username, color);
        gameController.setConnectionHandler(connectionHandler);
        gameController.setUsername(username);
        return connected;
    }

    /**
     * Displays an error message.
     * 
     * @param message Error message to be displayed.
     */
    protected void displayErrorMessage(String message) {
        log.debug("Displaying error message: {}", message);
        errorMessage.setText(message);
        errorMessage.setTextAlignment(TextAlignment.CENTER);

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.seconds(2));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);
        fade.setNode(errorMessage);
        fade.play();
    }

}
