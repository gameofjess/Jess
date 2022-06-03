package com.gameofjess.javachess.gui.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import com.gameofjess.javachess.server.Server;
import com.gameofjess.javachess.server.ServerBuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MenuController implements IController {

    private static final Logger log = LogManager.getLogger(MenuController.class);

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TextField address;
    @FXML
    private TextField username;

    /**
     * Load the host.fxml file and set the scene.
     * 
     * @param event ActionEvent
     * @throws IOException If the host.fxml file cannot be found.
     */

    public void switchHostScene(ActionEvent event) throws IOException {
        URL fxmlFileUrl = getClass().getClassLoader().getResource("host.fxml");
        switchScene(fxmlFileUrl, event);
    }

    /**
     * Load the menu.fxml file and set the scene.
     * 
     * @param event ActionEvent
     * @throws IOException If the file is not found.
     */

    public void switchJoinScene(ActionEvent event) throws IOException {
        URL fxmlFileUrl = getClass().getClassLoader().getResource("menu.fxml");
        switchScene(fxmlFileUrl, event);
    }

    /**
     * Set the scene given the URL of the fxml file.
     * 
     * @param fxmlFileUrl URL of the fxml file.
     * @param event ActionEvent.
     * @throws IOException If the fxml file cannot be loaded.
     */

    private void switchScene(URL fxmlFileUrl, ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(fxmlFileUrl));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        log.debug("Switching scene to " + fxmlFileUrl.toExternalForm());
    }

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
    public void connect(String address, int port, String username) throws InvalidHostnameException, InvalidPortException, URISyntaxException {
        ConnectionHandler connectionHandler = new ConnectionHandler(address, port);
        connectionHandler.connect(username);
        log.debug("Trying to connect to " + address + "using Port " + port + "as " + username);
        boolean connected = connectionHandler.getConnectionStatus();
    }

    /**
     * Starts the server and calls the connect function to connect to the server.
     * 
     * @param event ActionEvent
     * @throws InvalidPortException If the port is invalid.
     */
    public void hostGame(ActionEvent event) throws InvalidHostnameException, InvalidPortException, URISyntaxException {
        String host = "127.0.0.1";
        int port = 8887;

        ServerBuilder serverBuilder = new ServerBuilder();
        serverBuilder.setHost(host);
        serverBuilder.setPort(port);

        Server server = serverBuilder.build();
        Thread serverThread = new Thread(server);
        serverThread.start();

        // Connect to created Server
        connect(host, port, username.getText());
    }

}
