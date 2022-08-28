package com.gameofjess.javachess.gui.controller;

import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.gui.objects.CreateLobbyView;
import com.gameofjess.javachess.gui.objects.LobbyList;
import com.gameofjess.javachess.gui.objects.PasswordView;
import com.gameofjess.javachess.gui.scenes.SceneFactory;
import com.gameofjess.javachess.gui.scenes.SceneType;
import com.gameofjess.javachess.helper.configuration.ConfigLoader;
import com.gameofjess.javachess.helper.configuration.StandardConfig;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import com.gameofjess.javachess.helper.messages.ClientMessage;
import com.gameofjess.javachess.helper.messages.Message;
import com.gameofjess.javachess.helper.messages.MessageType;
import com.gameofjess.javachess.helper.publicserver.Lobby;
import com.gameofjess.javachess.helper.publicserver.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.framing.CloseFrame;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class PublicServerJoinMenuController extends MenuController {

    private static final Logger log = LogManager.getLogger(PublicServerJoinMenuController.class);

    private StandardConfig config;
    private ConnectionHandler connectionHandler;
    private String usernameString;

    private LobbyList lobbyList;
    private PasswordView passwordView;
    private CreateLobbyView createLobbyView;

    @FXML
    private VBox mainBox;
    @FXML
    private GridPane main;

    public void initialize() {
        lobbyList = new LobbyList();


    }


    public void receiveMessage(Message msg) {
        log.trace("Receiving message of type {}!", msg.getType());
        switch (msg.getType()) {
            case LOBBYLIST -> {
                String json = msg.getMessage();
                List<Lobby> lobbyList = new Gson().fromJson(json, TypeToken.getParameterized(List.class, Lobby.class).getType());
                for (Lobby lobby : lobbyList) {
                    Platform.runLater(() -> this.lobbyList
                            .addLobbyView(lobby.getName(), lobby.getUserList().stream().map(User::getUsername)
                                            .toArray(String[]::new),
                                    event -> {
                                        this.mainBox.getChildren().clear();
                                        passwordView = new PasswordView();
                                        passwordView.addEventListener(buttonEvent -> this.connectionHandler.send(new ClientMessage(lobby.getUuid() + "," + passwordView.getPassword(), MessageType.JOINLOBBY)));
                                        this.mainBox.getChildren().add(passwordView);
                                    }));
                }
            }
            case JOINLOBBY, CREATELOBBY -> {
                Platform.runLater(() -> {
                    try {
                        SceneFactory sceneFactory = new SceneFactory(SceneType.GAME);
                        Scene gameScene = sceneFactory.getScene().getFXScene();
                        GameController gameController = (GameController) sceneFactory.getController();
                        connectionHandler.setGameController(gameController);
                        switchGameScene(gameScene, gameController);
                        gameController.setConnectionHandler(connectionHandler);
                        gameController.setUsername(usernameString);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                        return;
                    }
                });
            }
            default -> {
                displayErrorMessage("Received message of unknown type - ignoring it.");
            }
        }
    }

    private boolean connect(String address, int port, String username) {
        try {
            connectionHandler = new ConnectionHandler(address, port, true);
        } catch (InvalidHostnameException | InvalidPortException | URISyntaxException e) {
            displayErrorMessage(e.getMessage());
            return false;
        }

        if (username.length() < 4) {
            displayErrorMessage("Invalid or too short username given: \"" + username + "\"");
            return false;
        }

        log.debug("Trying to connect to public server {} using port {} as {}.", address, port, username);
        connectionHandler.setMenuController(this);
        return connectionHandler.connect(username);
    }

    public void join(ActionEvent actionEvent) {
        try {
            config = (StandardConfig) new ConfigLoader().loadConfig(new File("config.json"), StandardConfig.class);
        } catch (IOException e) {
            log.error("Could not read config file. Proceeding to use default values!");
            config = new StandardConfig();
        }

        final String[] splittedAddress = config.getPublicServerHostname().split(":");
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
            log.fatal("Invalid address information in config!");
            return;
        }

        usernameString = username.getText();

        if (connect(host, port, usernameString)) {

            Stage stage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            assert stage != null;
            stage.setOnCloseRequest(event -> {
                this.connectionHandler.disconnect(CloseFrame.GOING_AWAY, "");
            });

            HBox buttonBox = new HBox();

            Button backButton = new Button();
            backButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-cursor: hand;");
            backButton.setFont(Font.font("System", FontWeight.BOLD, 16));
            backButton.setText("EXIT");
            backButton.setOnAction(event -> {
                this.connectionHandler.disconnect(CloseFrame.GOING_AWAY, "");
                try {
                    switchPublicScene();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            Button createLobbyButton = new Button();
            createLobbyButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-cursor: hand;");
            createLobbyButton.setFont(Font.font("System", FontWeight.BOLD, 16));
            createLobbyButton.setText("CREATE LOBBY");
            createLobbyButton.setOnAction(event -> {
                this.mainBox.getChildren().clear();

                createLobbyView = new CreateLobbyView();
                createLobbyView.addEventListener(createLobbyEvent -> {
                    this.connectionHandler.send(new ClientMessage(createLobbyView.getLobbyName() + "," + createLobbyView.getPassword(), MessageType.CREATELOBBY));
                });

                this.mainBox.getChildren().add(createLobbyView);
            });

            Button refreshButton = new Button();
            refreshButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-cursor: hand;");
            refreshButton.setFont(Font.font("System", FontWeight.BOLD, 16));
            refreshButton.setText("REFRESH");
            refreshButton.setOnAction(event -> {
                lobbyList.clear();
                connectionHandler.send(new ClientMessage("", MessageType.LOBBYLIST));
            });

            buttonBox.getChildren().addAll(backButton, createLobbyButton, refreshButton);
            buttonBox.setAlignment(Pos.BOTTOM_CENTER);
            buttonBox.setSpacing(20);
            buttonBox.setPadding(new Insets(20));

            main.getRowConstraints().get(0).setPercentHeight(10);

            main.add(buttonBox, 1, 0);

            mainBox.getChildren().clear();

            mainBox.setPadding(new Insets(0, 0, 10, 0));

            mainBox.getChildren().add(lobbyList);
        }
    }

    @Override
    public MenuController switchJoinScene() throws IOException {
        if (connectionHandler != null) {
            this.connectionHandler.disconnect(CloseFrame.GOING_AWAY, "");
        }
        return super.switchJoinScene();
    }

    @Override
    public MenuController switchHostScene() throws IOException {
        if (connectionHandler != null) {
            this.connectionHandler.disconnect(CloseFrame.GOING_AWAY, "");
        }
        return super.switchHostScene();
    }

    ;
}
