package org.example.javachess.gui;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Position;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class GameController {
    @FXML
    GridPane chessBoard;
    Board board;

    public void initialize() {
        System.out.println(chessBoard);
        board = new Board();
        setupPieces();
    }

    public void setupPieces() {
        board.initialize();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                String fen = board.board.get(new Position(x, y)) == null ? "" : board.board.get(new Position(x, y)).toString();
                System.out.print(fen);
                TextArea field = (TextArea) (chessBoard.getChildren().get(y * 8 + x));
                field.setText(fen);
            }
        }
    }
}
