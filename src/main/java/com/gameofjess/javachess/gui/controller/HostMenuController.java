package com.gameofjess.javachess.gui.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.gui.scenes.SceneFactory;
import com.gameofjess.javachess.gui.scenes.SceneType;
import com.gameofjess.javachess.helper.configuration.StandardConfig;
import com.gameofjess.javachess.helper.configuration.ConfigLoader;
import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.server.Server;
import com.gameofjess.javachess.server.ServerBuilder;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

public class HostMenuController extends MenuController {

    private static final Logger log = LogManager.getLogger(HostMenuController.class);

    private Color gameColor;
    private StandardConfig config;

    @FXML
    private RadioButton colorBlack;
    @FXML
    private RadioButton colorWhite;
    @FXML
    private RadioButton colorRandom;
    @FXML
    private Text ipAddressText;

    /**
     * Initializes the host menu.
     */
    public void initialize() {
        // Load config

        try {
            config = (StandardConfig) new ConfigLoader().loadConfig(new File("config.json"), StandardConfig.class);
        } catch (IOException e) {
            log.error("Could not read config file. Proceeding to use default values!");
            config = new StandardConfig();
        }

        // Get gameColor

        gameColor = Color.RANDOM;
        log.debug("Set color to default: RANDOM");

        colorWhite.setOnAction(actionEvent -> {
            if (colorBlack.isSelected()) {
                colorBlack.setSelected(false);
            }
            if (colorRandom.isSelected()) {
                colorRandom.setSelected(false);
            }
            gameColor = Color.WHITE;
            log.debug("Set color to: WHITE");
        });

        colorBlack.setOnAction(actionEvent -> {
            if (colorWhite.isSelected()) {
                colorWhite.setSelected(false);
            }
            if (colorRandom.isSelected()) {
                colorRandom.setSelected(false);
            }
            gameColor = Color.BLACK;
            log.debug("Set color to: BLACK");
        });

        colorRandom.setOnAction(actionEvent -> {
            if (colorWhite.isSelected()) {
                colorWhite.setSelected(false);
            }
            if (colorBlack.isSelected()) {
                colorBlack.setSelected(false);
            }
            gameColor = Color.RANDOM;
            log.debug("Set color to: RANDOM");
        });

        // Get ip addresses

        try {
            ipAddressText.setText("");

            // get local ip address

            final String localIPAddress = InetAddress.getLocalHost().getHostAddress();

            // get remote ip address

            Task<String> task = new Task<>() {
                @Override
                protected String call() {
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

    /**
     * Starts the server and calls the connect function to connect to the server.
     *
     */
    public void hostGame() throws IOException {
        String host = config.getDefaultHostname();
        int port = config.getDefaultPort();

        ServerBuilder serverBuilder = new ServerBuilder();

        Server server = serverBuilder.build();
        Thread serverThread = new Thread(server::start);

        serverThread.start();

        SceneFactory sceneFactory = new SceneFactory(SceneType.GAME);
        Scene gameScene = sceneFactory.getScene().getFXScene();

        GameController gameController = (GameController) sceneFactory.getController();
        gameController.setServer(server);

        if (connect(host, port, username.getText(), gameColor, gameController)) {
            switchGameScene(gameScene, gameController);
        }
    }

}
