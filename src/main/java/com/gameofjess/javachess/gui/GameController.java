package com.gameofjess.javachess.gui;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.gui.helper.objects.BoardPane;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class GameController {
    @FXML
    GridPane main;
    Board board;
    BoardPane boardPane;

    public void initialize() {
        board = new Board();
        boardPane = new BoardPane();
        main.add(boardPane, 1, 1);
        setupPieces();
    }

    public void setupPieces() {
        board.initialize();
        board.getBoardMap().entrySet().parallelStream().forEach(entry -> {
            Position pos = entry.getKey();
            Image icon = entry.getValue().getImage();
            boardPane.setImageByCell(icon, pos.getY(), pos.getX());
        });
    }
}
