package com.gameofjess.javachess.gui.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.gui.scenes.SceneFactory;
import com.gameofjess.javachess.gui.scenes.SceneType;
import com.gameofjess.javachess.helper.configuration.Config;
import com.gameofjess.javachess.helper.configuration.ConfigHandler;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.server.Server;
import com.gameofjess.javachess.server.ServerBuilder;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class MenuController extends Controller {

    private static final Logger log = LogManager.getLogger(MenuController.class);

    private Config config;

    private ConnectionHandler connectionHandler;

    private Color gameColor;

    @FXML
    private Text ipAddressText;
    @FXML
    private TextField address;
    @FXML
    private TextField username;
    @FXML
    private RadioButton colorBlack;
    @FXML
    private RadioButton colorWhite;
    @FXML
    private RadioButton colorRandom;
    @FXML
    public Text errorMessage;

    /**
     * Initializes the menu.
     */
    public void initialize() {

        try {
            config = new ConfigHandler().loadConfig(new File("config.json"));
        } catch (IOException e) {
            log.error("Could not read config file. Proceeding to use default values!");
            config = new Config();
        }

        if (colorBlack != null && colorWhite != null && colorRandom != null) {
            gameColor = Color.RANDOM;
            log.debug("Set color to default: RANDOM");

            colorWhite.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (colorBlack.isSelected()) {
                        colorBlack.setSelected(false);
                    }
                    if (colorRandom.isSelected()) {
                        colorRandom.setSelected(false);
                    }
                    gameColor = Color.WHITE;
                    log.debug("Set color to: WHITE");
                }
            });

            colorBlack.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (colorWhite.isSelected()) {
                        colorWhite.setSelected(false);
                    }
                    if (colorRandom.isSelected()) {
                        colorRandom.setSelected(false);
                    }
                    gameColor = Color.BLACK;
                    log.debug("Set color to: BLACK");
                }
            });

            colorRandom.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (colorWhite.isSelected()) {
                        colorWhite.setSelected(false);
                    }
                    if (colorBlack.isSelected()) {
                        colorBlack.setSelected(false);
                    }
                    gameColor = Color.RANDOM;
                    log.debug("Set color to: RANDOM");
                }
            });
        }

        if (ipAddressText != null) {
            try {
                ipAddressText.setText("");
                final String localIPAddress = InetAddress.getLocalHost().getHostAddress();

                Task<String> task = new Task<>() {
                    @Override
                    protected String call() throws Exception {
                        String remoteIPAddress;
                        if (config.getShowPublicIPAddress()) {
                            try {
                                String ipAddrServer = config.getIPAddressServer();
                                log.debug("Establishing connection to IP-Return server {}.", ipAddrServer);
                                URL url = new URL(ipAddrServer);
                                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                                con.setRequestMethod("GET");
                                con.connect();

                                if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
                                    log.debug("Connection to IP-Return server successful!");
                                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                    remoteIPAddress = br.readLine();
                                } else {
                                    log.error("Connection to IP-Return server failed!");
                                    remoteIPAddress = "Could not establish connection to server!";
                                }
                            } catch (IOException e) {
                                log.error("Connection to IP-Return server failed!");
                                remoteIPAddress = "Could not establish connection to server!";
                            }
                        } else {
                            log.debug("Getting public IP address is disabled in config.");
                            remoteIPAddress = "Disabled in config!";
                        }

                        return "LOCAL IP: " + localIPAddress + "\n" + "PUBLIC IP: " + remoteIPAddress;
                    }
                };

                Thread taskThread = new Thread(task);

                taskThread.start();

                ipAddressText.textProperty().bind(task.valueProperty());

            } catch (IOException e) {
                log.error("Connection to IP-Return server failed!");
                ipAddressText.setStyle("-fx-text-fill: #550000");
                ipAddressText.setText("Could not determine your IP-Address!");
            }
        }
    }

    /**
     * Gets user inputs and calls the connect method.
     *
     * @param event GUI ActionEvent
     */
    public void joinGame(ActionEvent event) throws IOException {
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
        Scene gameScene = sceneFactory.getScene();
        GameController gameController = (GameController) sceneFactory.getController();

        if (connect(host, port, usernameString, gameController)) {
            switchGameScene(gameScene, gameController, event);
        }
    }

    /**
     * Starts the server and calls the connect function to connect to the server.
     *
     * @param event GUI ActionEvent
     */
    public void hostGame(ActionEvent event) throws IOException {
        String host = config.getDefaultHostname();
        int port = config.getDefaultPort();

        ServerBuilder serverBuilder = new ServerBuilder();

        Server server = serverBuilder.build();
        Thread serverThread = new Thread(server::start);

        serverThread.start();

        SceneFactory sceneFactory = new SceneFactory(SceneType.GAME);
        Scene gameScene = sceneFactory.getScene();

        GameController gameController = (GameController) sceneFactory.getController();
        gameController.setServer(server);

        if (connect(host, port, username.getText(), gameColor, gameController)) {
            switchGameScene(gameScene, gameController, event);
        }
    }

    /**
     * Connects to the server.
     *
     * @param address IP address of the server.
     * @param port Port of the server.
     * @param username Username of the client.
     * @param gameController GameController to be used for communication.
     */
    private boolean connect(String address, int port, String username, GameController gameController) {
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
    private boolean connect(String address, int port, String username, Color color, GameController gameController) {

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

    private void displayErrorMessage(String message) {
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
