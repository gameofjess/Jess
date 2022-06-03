package com.gameofjess.javachess.mainpackage;

import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            URL fxmlFileUrl = getClass().getClassLoader().getResource("play.fxml");
            // URL cssFileUrl = getClass().getClassLoader().getResource("application.css");
            Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlFileUrl));
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(cssFileUrl.toExternalForm());
            stage.setTitle("Hello World");
            stage.setScene(scene);
            stage.setMinHeight(600);
            stage.setMinWidth(400);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
