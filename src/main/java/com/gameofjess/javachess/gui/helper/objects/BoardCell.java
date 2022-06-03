package com.gameofjess.javachess.gui.helper.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class BoardCell extends StackPane {
    private static final Logger log = LogManager.getLogger(BoardCell.class);

    private ImageView piece;
    String style;

    public BoardCell() {
        piece = new ImageView();
        style = "";

        piece.setSmooth(true);
        piece.fitHeightProperty().bind(heightProperty());
        piece.fitWidthProperty().bind(widthProperty());

        this.getChildren().add(piece);
    }

    public void setImage(Image img) {
        piece.setImage(img);
        piece.setPreserveRatio(true);
    }

    public void changeActivationStatus(boolean status) {
        if (status) {
            this.setStyle("-fx-background-color: #00FF00;");
        } else {
            this.setStyle(style);
        }
    }

    public void selectedCell(boolean status) {
        log.debug("Cell changed {}", status);
        if (status) {
            this.setStyle("-fx-background-color: #0000FF;");
        } else {
            this.setStyle(style);
        }
    }

    public void changeBackgroundColorToBlack() {
        this.style = "-fx-background-color: #3D4753;";
        this.setStyle(style);
    }

    public void addEventHandlerToPiece(EventHandler<MouseEvent> handler) {
        log.debug("Setting event handler to piece");
        this.setOnMouseClicked(handler);
    }

    public void resetEventHandler() {
        this.setOnMouseClicked(null);
    }
}
