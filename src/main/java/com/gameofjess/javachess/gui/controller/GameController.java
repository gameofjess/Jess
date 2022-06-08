package com.gameofjess.javachess.gui.controller;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.chesslogic.pieces.Piece;
import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.gui.helper.objects.BoardPane;
import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.helper.messages.ClientMessage;
import com.gameofjess.javachess.helper.messages.MessageType;
import com.gameofjess.javachess.helper.messages.ServerMessage;
import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameController extends Controller {
    private static final Logger log = LogManager.getLogger(GameController.class);
    @FXML
    private GridPane main;
    @FXML
    private TextField chatField;
    @FXML
    private TextArea chatHistory;
    @FXML
    private Text whiteUsername;
    @FXML
    private Text blackUsername;
    /**
     * Board model
     */
    private Board board;
    /**
     * Board view
     */
    private BoardPane boardPane;
    /**
     * ConnectionHandler for communication
     */
    private ConnectionHandler connectionHandler;
    /**
     * Chosen username
     */
    private String username;
    /**
     * Color assigned by server
     */
    private Color color;
    /**
     * Reflects whether it is the user's turn or the opponent's turn.
     */
    private boolean locked;

    /**
     * Initializes the game GUI.
     */
    public void initialize() {
        board = new Board();
        boardPane = new BoardPane();
        setBoardMessage("Waiting for opponent...");
    }

    /**
     * Renders all pieces on the board. Therefor, it sets up EventHandlers for the onClick-Event and
     * displays the pieces' images.
     */
    private void renderPieces() {
        setupPieceHandler();
        drawPieces();
    }

    /**
     * Sets up onClick-EventHandlers for all displayed pieces.
     */
    private void setupPieceHandler() {
        boardPane.resetEventHandlers();
        log.debug("Setting up piece handler");
        board.getBoardMap().entrySet().parallelStream().filter(entry -> {
            Piece piece = board.getBoardMap().get(entry.getKey());
            return (piece.isWhite() && color == Color.WHITE) || (!piece.isWhite() && color == Color.BLACK);
        }).forEach(entry -> {
            Position pos = entry.getKey();
            Piece piece = board.getBoardMap().get(pos);
            Move[] possibleMoves = piece.getMoves();
            boardPane.setPieceEventHandlerByCell(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    log.debug("Piece at Position ({}|{}) Clicked", pos.getX(), pos.getY());

                    boardPane.resetStatus();

                    if (boardPane.changeSelectionStatusByCell(pos.getY(), pos.getX())) {
                        // Add destination markers when selecting cell
                        for (Move m : possibleMoves) {
                            int destX = m.getDestination().getX();
                            int destY = m.getDestination().getY();

                            boardPane.setActivationStatusByCell(true, destY, destX);
                            if (!locked) {
                                boardPane.setPieceEventHandlerByCell(new EventHandler<MouseEvent>() {
                                    // Add event handlers to destination markers
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        log.debug("Destination at ({}|{}) clicked", destX, destY);
                                        Piece piece = board.getBoardMap().get(pos);
                                        piece.makeMove(m);
                                        boardPane.resetStatus();

                                        sendMessage(new ClientMessage(m));

                                        locked = true;

                                        renderPieces();
                                        setupPieceHandler();

                                    }
                                }, destY, destX);
                            }
                        }
                    } else {
                        // Remove destination markers when deselecting cell.
                        for (Move m : possibleMoves) {
                            int destX = m.getDestination().getX();
                            int destY = m.getDestination().getY();

                            boardPane.setActivationStatusByCell(false, destY, destX);
                        }
                    }


                }
            }, pos.getY(), pos.getX());
        });

    }

    /**
     * Draws the pieces' images.
     */
    private void drawPieces() {
        boardPane.resetImages();

        board.getBoardMap().entrySet().parallelStream().forEach(entry -> {
            Position pos = entry.getKey();
            Image icon = entry.getValue().getImage();
            boardPane.setImageByCell(icon, pos.getY(), pos.getX());
        });
    }

    /**
     * Sets ConnectionHandler used to send and receive messages.
     * 
     * @param connectionHandler ConnectionHandler that should be used to send messages from the GUI to
     *        the server.
     */
    void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    /**
     * Sets username used to send and receive messages.
     *
     * @param username Username that should be used.
     */
    void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sends a chat message.
     * 
     * @param event GUI ActionEvent
     */
    public void sendChatMessage(ActionEvent event) {
        String message = chatField.getText();
        ClientMessage cmsg = new ClientMessage(message, MessageType.CHATMESSAGE);
        sendMessage(cmsg);
        chatField.clear();
    }

    /**
     * Receives a chat message.
     *
     */
    public void receiveMessage(ServerMessage serverMessage) {
        String message = serverMessage.getMessage();
        String username = serverMessage.getUsername();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String formattedDate = dateFormat.format(serverMessage.getTime());
        switch (serverMessage.getType()) {
            case CHATMESSAGE -> {
                chatHistory.setText(chatHistory.getText() + formattedDate + " - " + username + ": " + message + "\n");
            }
            case NEWMOVE -> {
                Move m = new Gson().fromJson(message, Move.class);

                board.getBoardMap().get(m.getOrigin()).makeMove(m);

                locked = !locked;

                renderPieces();
                setupPieceHandler();
            }
            case SERVERINFO -> {
                chatHistory.setText(chatHistory.getText() + formattedDate + " - INFO: " + message + "\n");
            }

            case SERVERERROR -> {
                chatHistory.setText(chatHistory.getText() + formattedDate + " - INFO: " + message + "\n");
                endGame("Ended game due to server error!");
            }

            case COLORINFO -> {
                this.color = Color.valueOf(message);
                log.debug("Got assigned color {}!", color.name());
                locked = color == Color.BLACK;
            }

            case USERLIST -> {
                log.debug("Received user list!");
                Object[] users = new Gson().fromJson(message, Object[].class);
                for (Object o : users) {
                    String name = (String) o;
                    if (!name.equals(this.username)) {
                        log.debug("Got other name: {}!", name);
                        if (color != null) {
                            if (color == Color.BLACK) {
                                log.debug("Setting white username to {}!", name);
                                whiteUsername.setText(name);
                                log.debug("Setting black username to {}!", this.username);
                                blackUsername.setText(this.username);
                                blackUsername.setUnderline(true);
                            } else {
                                log.debug("Setting black username to {}!", name);
                                blackUsername.setText(name);
                                log.debug("Setting white username to {}!", this.username);
                                whiteUsername.setText(this.username);
                                whiteUsername.setUnderline(true);
                            }
                        }
                    }
                }
            }
            case BEGINMATCH -> {
                Platform.runLater(() -> {
                    Optional<Node> textOptional =
                            main.getChildren().stream().parallel().filter(child -> child.idProperty().get() != null && child.idProperty().get().equals("boardText")).findAny();
                    textOptional.ifPresent(node -> main.getChildren().remove(node));

                    main.getChildren().remove(1, 1);
                    main.add(boardPane, 1, 1);
                    board.initialize();
                    renderPieces();
                });

                chatHistory.setText(chatHistory.getText() + formattedDate + " - INFO: " + message + "\n");
            }
            default -> {
                throw new IllegalArgumentException("Received message is not of any recognized type!");
            }
        }


    }

    /**
     * Sends a message via the ConnectionHandler
     * 
     * @param clientMessage ClientMessage to be sent.
     */
    private void sendMessage(ClientMessage clientMessage) {
        connectionHandler.send(clientMessage);
    }

    /**
     * Removes the board and sets message that is shown in the boards' place.
     * 
     * @param message Message to be shown.
     */
    public void endGame(String message) {
        Platform.runLater(() -> {
            main.getChildren().remove(boardPane);
            setBoardMessage(message);
        });
    }

    /**
     * Sets message that is shown in the boards' place.
     * 
     * @param message Message to be shown.
     */
    private void setBoardMessage(String message) {
        Optional<Node> textOptional =
                main.getChildren().stream().parallel().filter(child -> child.idProperty().get() != null && child.idProperty().get().equals("boardText")).findAny();
        if (textOptional.isPresent()) {
            log.debug("Changing game information to {}", message);
            ((Label) textOptional.get()).setText(message);
        } else {
            log.debug("Adding game information: {}", message);
            Label text = new Label(message.toUpperCase(Locale.ROOT));
            text.setStyle("-fx-font-weight: 900; -fx-font-size: 16;");
            GridPane.setHalignment(text, HPos.CENTER);
            text.setId("boardText");

            main.add(text, 1, 1);
        }
    }
}
