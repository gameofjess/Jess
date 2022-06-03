package org.example.javachess.gui;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Position;
import org.example.javachess.gui.helper.objects.BoardPane;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
<<<<<<< HEAD
        // for (int y = 0; y < 8; y++) {
        // for (int x = 0; x < 8; x++) {
        // String fen = board.board.get(new Position(x, y)) == null ? "" : board.board.get(new Position(x,
        // y)).toString();
        // TextArea field = (TextArea) (boardPane.getChildren().get(y * 8 + x));
        // field.setText(fen);
        // }
        // }
=======
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                String fen = board.board.get(new Position(x, y)) == null ? "" : board.board.get(new Position(x, y)).toString();
                System.out.print(fen);
                TextField field = (TextField) (chessBoard.getChildren().get(y * 8 + x));
                field.setText(fen);
            }
        }
>>>>>>> 30ae370fbbc4b2780997181962075cd609a5c6c1
    }
}
