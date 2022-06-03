package com.gameofjess.javachess.gui;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.gui.helper.objects.BoardPane;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class GameController {
    @FXML
    GridPane main;
    // GridPane chessBoard;
    Board board;
    BoardPane boardPane;


    public void initialize() {
        // System.out.println(chessBoard);
        board = new Board();
        boardPane = new BoardPane();
        main.add(boardPane, 1, 1);
        setupPieces();
    }

    public void setupPieces() {
        board.initialize();
        // for (int y = 0; y < 8; y++) {
        // for (int x = 0; x < 8; x++) {
        // String fen = board.board.get(new Position(x, y)) == null ? "" : board.board.get(new Position(x,
        // y)).toString();
        // TextArea field = (TextArea) (boardPane.getChildren().get(y * 8 + x));
        // field.setText(fen);
        // }
        // }
    }
}
