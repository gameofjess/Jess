package com.gameofjess.javachess.gui;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.chesslogic.pieces.Piece;
import com.gameofjess.javachess.gui.helper.objects.BoardPane;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    Map<Position, Piece> oldBoardMap;

    public void initialize() {
        board = new Board();
        boardPane = new BoardPane();
        main.add(boardPane, 1, 1);
        board.initialize();
        oldBoardMap = new HashMap<Position, Piece>();
        renderPieces();
    }

    private void renderPieces() {
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
                        int destX = m.destination.getX();
                        int destY = m.destination.getY();

                        boardPane.setActivationStatusByCell(true, destY, destX);
                        boardPane.setPieceEventHandlerByCell(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                log.info("Destination Clicked");
                                Piece piece = board.getBoardMap().get(pos);
                                oldBoardMap = board.getBoardMap();
                                piece.makeMove(m);
                                boardPane.resetStatus();

                                updatePosition(m.destination);
                                updatePosition(pos);

                            }
                        }, destY, destX);
                    }
                }
            }, pos.getY(), pos.getX());
        });

    }


    private void drawPieces() {

        boardPane.resetImages();

        board.getBoardMap().entrySet().parallelStream().forEach(entry -> {
            Position pos = entry.getKey();
            Image icon = entry.getValue().getImage();
            boardPane.setImageByCell(icon, pos.getY(), pos.getX());
        });
    }

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
}
