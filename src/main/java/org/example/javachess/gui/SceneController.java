package org.example.javachess.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SceneController {
    private Stage stage;
    private Parent root;
    private Scene scene;

    public void switchHostScene(ActionEvent event) throws IOException {
        URL fxmlFileUrl = getClass().getClassLoader().getResource("host.fxml");
        switchScene(fxmlFileUrl, event);
    }

    public void switchJoinScene(ActionEvent event) throws IOException {
        URL fxmlFileUrl = getClass().getClassLoader().getResource("menu.fxml");
        switchScene(fxmlFileUrl, event);
    }

    private void switchScene(URL fxmlFileUrl, ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(fxmlFileUrl));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
