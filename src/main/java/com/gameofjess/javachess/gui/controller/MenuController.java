package com.gameofjess.javachess.gui.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import com.gameofjess.javachess.server.Server;
import com.gameofjess.javachess.server.ServerBuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MenuController extends Controller {

    private static final Logger log = LogManager.getLogger(MenuController.class);

    @FXML
    private TextField address;
    @FXML
    private TextField username;

    /**
     * Gets user inputs and calls the connect method.
     *
     * @param event ActionEvent
     * @throws InvalidHostnameException If the hostname is invalid.
     * @throws InvalidPortException If the port is invalid.
     * @throws URISyntaxException If the URI is invalid.
     */
    public void joinGame(ActionEvent event) throws InvalidHostnameException, InvalidPortException, URISyntaxException {
        // TODO spilt port from IP Adress
        // TODO: start connection handler in thread
        connect(address.getText(), 8887, username.getText());
        log.debug("Joining game");
    }

    /**
     * Connects to the server.
     *
     * @param address IP address of the server.
     * @param port Port of the server.
     * @param username Username of the client.
     * @throws InvalidHostnameException If the hostname is invalid.
     * @throws InvalidPortException If the port is invalid.
     * @throws URISyntaxException If the URI is invalid.
     */
    public boolean connect(String address, int port, String username) throws InvalidHostnameException, InvalidPortException, URISyntaxException {
        ConnectionHandler connectionHandler = new ConnectionHandler(address, port);
        log.debug("Trying to connect to " + address + "using Port " + port + "as " + username);
        return connectionHandler.connect(username);
    }

    /**
     * Starts the server and calls the connect function to connect to the server.
     *
     * @param event ActionEvent
     * @throws InvalidPortException If the port is invalid.
     */
    public void hostGame(ActionEvent event) throws InvalidHostnameException, InvalidPortException, URISyntaxException, IOException {
        String host = "127.0.0.1";
        int port = 8887;

        ServerBuilder serverBuilder = new ServerBuilder();
        serverBuilder.setHost(host);
        serverBuilder.setPort(port);

        Server server = serverBuilder.build();
        Thread serverThread = new Thread(server);
        serverThread.start();

        // Connect to created Server and switch to game scene
        if (connect(host, port, username.getText())) {
            switchGameScene(event);
        }
    }

}
