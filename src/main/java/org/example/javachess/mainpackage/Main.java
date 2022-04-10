package org.example.javachess.mainpackage;

import org.example.javachess.chess_logic.*;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.javachess.chess_logic.board;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //TODO: Implement JavaFX startup
        /*

        URL fxmlFileUrl = getClass().getClassLoader().getResource("main.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlFileUrl));
        primaryStage.setTitle("JavaChess");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

         */
    }

    public static void main(String[] args) {
		board.initialize();
		System.out.println(board.pieces);
    }
}