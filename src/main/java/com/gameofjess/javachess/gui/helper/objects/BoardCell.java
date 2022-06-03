package com.gameofjess.javachess.gui.helper.objects;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class BoardCell extends StackPane {

    private Pane marker;
    private ImageView piece;

    public BoardCell() {
        marker = new Pane();
        piece = new ImageView();

        piece.setSmooth(true);
        piece.fitHeightProperty().bind(heightProperty());
        piece.fitWidthProperty().bind(widthProperty());

        this.getChildren().add(piece);
        this.getChildren().add(marker);
    }

    public void setImage(Image img) {
        piece.setImage(img);
        piece.setPreserveRatio(true);
    }

    public void changeActivationStatus(boolean status) {
        if (status) {
            marker.setStyle("-fx-background-color: #00FF00; -fx-opacity: 0.1;");
        } else {
            marker.setStyle("");
        }
    }

    public void changeBackgroundColorToBlack() {
        this.setStyle("-fx-background-color: #000000;");
    }

    public void addEventHandlerToPiece(EventHandler<MouseEvent> handler) {
        piece.setOnMouseClicked(handler);
    }

}
