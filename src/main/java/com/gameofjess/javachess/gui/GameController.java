package com.gameofjess.javachess.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.gui.helper.objects.BoardPane;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GameController {
    private static final Logger log = LogManager.getLogger(GameController.class);
    @FXML
    GridPane main;
    Board board;
    BoardPane boardPane;

    public void initialize() {
        board = new Board();
        boardPane = new BoardPane();
        main.add(boardPane, 1, 1);
        board.initialize();
        updatePieces();
    }

    private void updatePieces() {
        setupPieceHandler();
        drawPieces();
    }

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

                    boardPane.setSelectedByCell(true, pos.getY(), pos.getX());

                    for (Move m : possibleMoves) {
                        int destX = m.destination.x;
                        int destY = m.destination.y;

                        boardPane.setActivationStatusByCell(true, destY, destX);
                    }
                }
            }, pos.getY(), pos.getX());
        });

    }

    private void drawPieces() {
        board.getBoardMap().entrySet().parallelStream().forEach(entry -> {
            Position pos = entry.getKey();
            Image icon = entry.getValue().getImage();
            boardPane.setImageByCell(icon, pos.getY(), pos.getX());
        });
    }
}
