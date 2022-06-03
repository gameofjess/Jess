package com.gameofjess.javachess.gui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.chesslogic.pieces.Piece;
import com.gameofjess.javachess.gui.helper.objects.BoardPane;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GameController implements IController {
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

                    if (boardPane.changeSelectedStatusByCell(pos.getY(), pos.getX())) {
                        for (Move m : possibleMoves) {
                            int destX = m.destination.getX();
                            int destY = m.destination.getY();

                            boardPane.setActivationStatusByCell(true, destY, destX);
                            boardPane.setPieceEventHandlerByCell(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    log.info("Destination Clicked");
                                    Piece piece = board.getBoardMap().get(pos);
                                    piece.makeMove(m);
                                    boardPane.resetStatus();

                                    updatePosition(m.destination);
                                    updatePosition(pos);

                                }
                            }, destY, destX);
                        }
                    } else {
                        for (Move m : possibleMoves) {
                            int destX = m.destination.getX();
                            int destY = m.destination.getY();

                            boardPane.setActivationStatusByCell(false, destY, destX);
                        }
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
