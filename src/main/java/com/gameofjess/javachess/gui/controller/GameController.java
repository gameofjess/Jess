package com.gameofjess.javachess.gui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.framing.CloseFrame;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.chesslogic.pieces.*;
import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.gui.helper.objects.BoardOverlay;
import com.gameofjess.javachess.gui.helper.objects.BoardPane;
import com.gameofjess.javachess.gui.helper.objects.CapturedPieceGrid;
import com.gameofjess.javachess.gui.helper.objects.PromotionSelectView;
import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.helper.messages.ClientMessage;
import com.gameofjess.javachess.helper.messages.MessageType;
import com.gameofjess.javachess.helper.messages.ServerMessage;
import com.gameofjess.javachess.server.Server;
import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

public class GameController extends Controller {
    private static final Logger log = LogManager.getLogger(GameController.class);

    @FXML
    private HBox resignButtonField;
    @FXML
    private GridPane root;
    @FXML
    private GridPane main;
    @FXML
    private TextField chatField;
    @FXML
    private TextArea chatHistory;
    @FXML
    private HBox upperUsernameField;
    @FXML
    private HBox lowerUsernameField;
    @FXML
    public CapturedPieceGrid capturedPiecesGrid;
    /**
     * Board model
     */
    private Board board;
    /**
     * Board view
     */
    private BoardPane boardPane;
    /**
     * Button used to resign
     */
    private Button resignButton;
    /**
     * ConnectionHandler for communication
     */
    private ConnectionHandler connectionHandler;
    /**
     * Server to stop on application closure.
     */
    private Server server;
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
        setBoardMessage("Waiting for opponent...");
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
     * Sets Server to stop on application closure.
     *
     * @param server Server that shall be stopped on application closure.
     */
    void setServer(Server server) {
        this.server = server;
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
            boardPane.setPieceEventHandlerByCell(selectMouseEvent -> {
                log.debug("Piece at Position ({}|{}) Clicked", pos.getX(), pos.getY());

                boolean notSelected = boardPane.changeSelectionStatusByCell(pos.getY(), pos.getX());

                boardPane.resetStatus();
                highlightCheck();

                if (notSelected) {
                    boardPane.setSelectionStatusByCell(true, pos.getY(), pos.getX());
                    // Add destination markers when selecting cell
                    for (Move m : possibleMoves) {
                        if (!locked) {
                            int destX = m.getDestination().getX();
                            int destY = m.getDestination().getY();

                            boardPane.setActivationStatusByCell(true, destY, destX);
                            // Add event handlers to destination markers
                            boardPane.setPieceEventHandlerByCell(moveMouseEvent -> {
                                log.debug("Destination at ({}|{}) clicked", destX, destY);
                                Piece piece1 = board.getBoardMap().get(pos);

                                Piece capturedPiece = board.getBoardMap().get(m.getCapturePosition());
                                if (capturedPiece != null) {
                                    capturedPiecesGrid.add(capturedPiece);
                                }

                                // Handle promotion
                                if (m.getPromotion() != null) {
                                    log.debug("Promotion detected!");

                                    PromotionSelectView promotionSelectView = new PromotionSelectView(color);

                                    GridPane.setHalignment(promotionSelectView, HPos.CENTER);
                                    GridPane.setValignment(promotionSelectView, VPos.BOTTOM);

                                    Task<Void> renderTask = new Task<>() {
                                        @Override
                                        protected Void call() {
                                            promotionSelectView.renderImages();
                                            return null;
                                        }
                                    };

                                    renderTask.setOnSucceeded(e -> {
                                        main.add(promotionSelectView, 1, 0);
                                    });

                                    new Thread(renderTask).start();

                                    promotionSelectView.setEventHandlerByColumn(0, event -> {
                                        log.trace("Chose Queen for promotion!");
                                        m.changePromotion(Queen.class.getName());
                                        main.getChildren().remove(promotionSelectView);

                                        piece1.makeMove(m);

                                        boardPane.resetStatus();
                                        highlightCheck();

                                        sendMessage(new ClientMessage(m));

                                        updateTurnStatus(true);

                                        renderPieces();
                                        setupPieceHandler();
                                    });

                                    promotionSelectView.setEventHandlerByColumn(1, event -> {
                                        log.trace("Chose Knight for promotion!");
                                        m.changePromotion(Knight.class.getName());
                                        main.getChildren().remove(promotionSelectView);

                                        piece1.makeMove(m);

                                        boardPane.resetStatus();
                                        highlightCheck();

                                        sendMessage(new ClientMessage(m));

                                        updateTurnStatus(true);

                                        renderPieces();
                                        setupPieceHandler();
                                    });

                                    promotionSelectView.setEventHandlerByColumn(2, event -> {
                                        log.trace("Chose Rook for promotion!");
                                        m.changePromotion(Rook.class.getName());
                                        main.getChildren().remove(promotionSelectView);

                                        piece1.makeMove(m);

                                        boardPane.resetStatus();
                                        highlightCheck();

                                        sendMessage(new ClientMessage(m));

                                        updateTurnStatus(true);

                                        renderPieces();
                                        setupPieceHandler();
                                    });

                                    promotionSelectView.setEventHandlerByColumn(3, event -> {
                                        log.trace("Chose Bishop for promotion!");
                                        m.changePromotion(Bishop.class.getName());
                                        main.getChildren().remove(promotionSelectView);

                                        piece1.makeMove(m);

                                        boardPane.resetStatus();
                                        highlightCheck();

                                        sendMessage(new ClientMessage(m));

                                        updateTurnStatus(true);

                                        renderPieces();
                                        setupPieceHandler();
                                    });

                                } else {
                                    piece1.makeMove(m);

                                    boardPane.resetStatus();
                                    highlightCheck();

                                    sendMessage(new ClientMessage(m));

                                    updateTurnStatus(true);

                                    renderPieces();
                                    setupPieceHandler();
                                }

                            }, destY, destX);
                        }
                    }
                } else {
                    boardPane.setSelectionStatusByCell(false, pos.getY(), pos.getX());
                    // Remove destination markers when deselecting cell.
                    for (Move m : possibleMoves) {
                        int destX = m.getDestination().getX();
                        int destY = m.getDestination().getY();

                        boardPane.setActivationStatusByCell(false, destY, destX);
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
     * Sets message that is shown in the boards' place.
     *
     * @param message Message to be shown.
     */
    private void setBoardMessage(String message) {
        resignButtonField.getChildren().clear();
        root.getChildren().parallelStream().filter(child -> child.idProperty().get() != null && child.idProperty().get().equals("boardOverlay")).findAny()
                .ifPresentOrElse(boardOverlay -> {
                    log.debug("Changing game information to {}", message);
                    ((BoardOverlay) boardOverlay).setBoardOverlayText(message);
                }, () -> {
                    log.debug("Adding game information: {}", message);
                    BoardOverlay boardOverlay = new BoardOverlay();

                    if (boardPane != null) {
                        boardPane.resetEventHandlers();
                        boardOverlay.prefHeightProperty().bind(boardPane.prefHeightProperty());
                        boardOverlay.prefWidthProperty().bind(boardPane.prefWidthProperty());
                    }

                    boardOverlay.setBoardOverlayText(message);

                    boardOverlay.setExitButtonOnActionEventHandler(event -> {
                        closeConnection(username + " exited!");
                        try {
                            if (server == null) {
                                log.trace("Switching to join scene, because no server had been started!");
                                switchJoinScene();
                            } else {
                                log.trace("Switching to host scene, because server had been started!");
                                switchHostScene();
                            }
                        } catch (IOException e) {
                            log.error("Could not switch scene!");
                        }
                    });

                    root.add(boardOverlay, 0, 0);
                });

        Effect frostEffect = new GaussianBlur(2.5);
        Effect frostEffectCapturedPiecesGrid = new GaussianBlur(10);
        main.setEffect(frostEffect);
        capturedPiecesGrid.setEffect(frostEffectCapturedPiecesGrid);
    }

    /**
     * Changes the lock status which indicates whether it's the player's turn or not. Also updates the
     * turn indicators in the GUI.
     */
    private void updateTurnStatus(boolean locked) {
        this.locked = locked;
        if (locked) {
            upperUsernameField.getChildren().stream().filter(child -> child instanceof Label).findAny().ifPresent(node -> ((Label) node).setUnderline(true));
            lowerUsernameField.getChildren().stream().filter(child -> child instanceof Label).findAny().ifPresent(node -> ((Label) node).setUnderline(false));
        } else {
            upperUsernameField.getChildren().stream().filter(child -> child instanceof Label).findAny().ifPresent(node -> ((Label) node).setUnderline(false));
            lowerUsernameField.getChildren().stream().filter(child -> child instanceof Label).findAny().ifPresent(node -> ((Label) node).setUnderline(true));
        }
    }

    /**
     * Highlight king if in check.
     */
    private void highlightCheck() {
        Board clonedBoard = new Board(board);

        boolean blackKingCheck = clonedBoard.getKingBlack().checkCheck();

        int blackKingRow = clonedBoard.getKingBlack().getPosition().getY();
        int blackKingColumn = clonedBoard.getKingBlack().getPosition().getX();
        boardPane.setCheckStatusByCell(blackKingCheck, blackKingRow, blackKingColumn);

        boolean whiteKingCheck = clonedBoard.getKingWhite().checkCheck();

        int whiteKingRow = clonedBoard.getKingWhite().getPosition().getY();
        int whiteKingColumn = clonedBoard.getKingWhite().getPosition().getX();
        boardPane.setCheckStatusByCell(whiteKingCheck, whiteKingRow, whiteKingColumn);
    }

    /**
     * Removes the board and sets message that is shown in the boards' place.
     *
     * @param message Message to be shown.
     */
    public void endGame(String message) {
        Platform.runLater(() -> {
            setBoardMessage(message);
        });
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
                Platform.runLater(() -> chatHistory.setText(chatHistory.getText() + formattedDate + " - " + username + ": " + message + "\n"));
            }
            case NEWMOVE -> {
                Move m = new Gson().fromJson(message, Move.class);

                Piece capturedPiece = board.getBoardMap().get(m.getCapturePosition());

                if (capturedPiece != null) {
                    Platform.runLater(() -> {
                        capturedPiecesGrid.add(capturedPiece);
                    });
                }

                board.getBoardMap().get(m.getOrigin()).makeMove(m);

                updateTurnStatus(false);

                boardPane.resetStatus();

                highlightCheck();

                renderPieces();
                setupPieceHandler();
            }
            case SERVERINFO -> {
                Platform.runLater(() -> chatHistory.setText(chatHistory.getText() + formattedDate + " - INFO: " + message + "\n"));
            }

            case SERVERERROR -> {
                Platform.runLater(() -> chatHistory.setText(chatHistory.getText() + formattedDate + " - INFO: " + message + "\n"));
                endGame("Ended game due to server error!");
            }

            case COLORINFO -> {
                this.color = Color.valueOf(message);
                log.debug("Got assigned color {}!", color.name());
                updateTurnStatus(color == Color.BLACK);

                Platform.runLater(() -> {
                    boardPane = new BoardPane(color == Color.WHITE);
                    renderPieces();
                    main.add(boardPane, 1, 1);
                });
            }

            case USERLIST -> {
                log.debug("Received user list!");
                Object[] users = new Gson().fromJson(message, Object[].class);
                Platform.runLater(() -> {
                    Arrays.stream(users).filter(user -> !user.equals(this.username)).findAny().ifPresent(user -> {
                        lowerUsernameField.getChildren().forEach(child -> {
                            if (child instanceof Label text) {
                                text.setText(this.username);
                            }
                            if (child instanceof Circle circle) {
                                circle.setStroke(javafx.scene.paint.Color.BLACK);
                                if (color == Color.WHITE) {
                                    circle.setFill(javafx.scene.paint.Color.WHITE);
                                } else {
                                    circle.setFill(javafx.scene.paint.Color.BLACK);
                                }
                            }
                        });
                        upperUsernameField.getChildren().forEach(child -> {
                            if (child instanceof Label text) {
                                text.setText((String) user);
                            }
                            if (child instanceof Circle circle) {
                                circle.setStroke(javafx.scene.paint.Color.BLACK);
                                if (color != Color.WHITE) {
                                    circle.setFill(javafx.scene.paint.Color.WHITE);
                                } else {
                                    circle.setFill(javafx.scene.paint.Color.BLACK);
                                }
                            }
                        });
                    });
                });
            }
            case BEGINMATCH -> {
                Platform.runLater(() -> {
                    root.getChildren().stream().parallel().filter(child -> child.idProperty().get() != null && child.idProperty().get().equals("boardOverlay")).findAny()
                            .ifPresent(node -> root.getChildren().remove(node));

                    resignButton = new Button();
                    resignButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-cursor: hand;");
                    resignButton.setOnAction(event -> resign());
                    resignButton.setText("RESIGN");
                    resignButtonField.getChildren().add(resignButton);

                    main.getChildren().remove(1, 1);

                    main.setEffect(null);
                    capturedPiecesGrid.setEffect(null);

                    chatHistory.setText(chatHistory.getText() + formattedDate + " - INFO: " + message + "\n");
                });
            }
            case CHECKMATE -> {
                Platform.runLater(() -> {
                    endGame(username + " has won!");
                });
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
     * Disconnects from the server and closes the server if started.
     * 
     * @param reason Reason for closing the connection.
     */
    void closeConnection(String reason) {
        this.connectionHandler.disconnect(CloseFrame.GOING_AWAY, reason);

        if (server != null) {
            try {
                server.stop();
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
    }

    /**
     * Closes the connection and switches back to the join scene.
     */
    private void resign() {
        resignButtonField.getChildren().clear();

        VBox vBox = new VBox();
        HBox hBox = new HBox();

        Button yesButton = new Button();
        yesButton.setText("YES");
        yesButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black; -fx-cursor: hand; -fx-stroke: black;");
        Button noButton = new Button();
        noButton.setText("NO");
        noButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-cursor: hand; -fx-stroke: black;");

        yesButton.setOnAction(onActionEvent -> {
            closeConnection(username + " resigned!");
            try {
                if (server == null) {
                    log.trace("Switching to join scene, because no server had been started!");
                    switchJoinScene();
                } else {
                    log.trace("Switching to host scene, because server had been started!");
                    switchHostScene();
                }
            } catch (IOException e) {
                log.error("Could not switch scene!");
            }
        });

        noButton.setOnAction(onActionEvent -> {
            resignButtonField.getChildren().clear();
            resignButtonField.getChildren().add(resignButton);
        });

        Label text = new Label();
        text.setText("Are you sure?");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setStyle("-fx-font-weight: 900;");
        text.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(yesButton, noButton);
        vBox.getChildren().addAll(text, hBox);

        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        vBox.setSpacing(5);
        hBox.setSpacing(5);

        resignButtonField.getChildren().add(vBox);
    }
}
