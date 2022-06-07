package com.gameofjess.javachess.gui.controller;

import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.chesslogic.pieces.Piece;
import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.gui.helper.objects.BoardPane;
import com.gameofjess.javachess.helper.messages.ClientMessage;
import com.gameofjess.javachess.helper.messages.MessageType;
import com.gameofjess.javachess.helper.messages.ServerMessage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GameController extends Controller {
    private static final Logger log = LogManager.getLogger(GameController.class);
    @FXML
    private GridPane main;
    @FXML
    private TextField chatField;
    @FXML
    private TextArea chatHistory;
    private Board board;
    private BoardPane boardPane;
    private ConnectionHandler connectionHandler;

    /**
     * Initializes the game GUI.
     */
    public void initialize() {
        board = new Board();
        boardPane = new BoardPane();
        main.add(boardPane, 1, 1);
        board.initialize();
        renderPieces();
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
        log.debug("Setting up piece handler");
        board.getBoardMap().entrySet().parallelStream().forEach(entry -> {
            Position pos = entry.getKey();
            boardPane.setPieceEventHandlerByCell(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    log.debug("Piece Clicked");

                    Move[] possibleMoves = board.getBoardMap().get(pos).getMoves();
                    boardPane.resetStatus();

                    if (boardPane.changeSelectedStatusByCell(pos.getY(), pos.getX())) {
                        for (Move m : possibleMoves) {
                            int destX = m.getDestination().getX();
                            int destY = m.getDestination().getY();

                            boardPane.setActivationStatusByCell(true, destY, destX);
                            boardPane.setPieceEventHandlerByCell(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    log.info("Destination Clicked");
                                    Piece piece = board.getBoardMap().get(pos);
                                    piece.makeMove(m);
                                    boardPane.resetStatus();

                                    updatePosition(m.getDestination());
                                    updatePosition(pos);

                                }
                            }, destY, destX);
                        }
                    } else {
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
     * Updates only one piece and all onClick-EventHandlers.
     * 
     * @param pos Position of piece to update.
     */
    private void updatePosition(Position pos) {
        Piece piece = board.getBoardMap().get(pos);

        if (piece != null) {
            Image icon = piece.getImage();
            boardPane.setImageByCell(icon, pos.getY(), pos.getX());
        } else {
            boardPane.setImageByCell(null, pos.getY(), pos.getX());
        }

        setupPieceHandler();
    }

    /**
     * Sets ConnectionHandler used to send and receive messages.
     * 
     * @param connectionHandler that should be used to send messages from the GUI to the server.
     */
    void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
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
    public void receiveChatMessage(ServerMessage serverMessage) {
        if (serverMessage.getType() == MessageType.CHATMESSAGE) {
            String message = serverMessage.getMessage();
            String username = serverMessage.getUsername();

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String formattedDate = dateFormat.format(serverMessage.getTime());

            chatHistory.setText(chatHistory.getText() + formattedDate + " - " + username + ": " + message + "\n");
        } else {
            throw new IllegalArgumentException("Received chat message that is not of type chat message!");
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
}
