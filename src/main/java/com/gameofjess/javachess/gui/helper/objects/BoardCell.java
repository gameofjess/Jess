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

    private boolean selected = false;

    private final ImageView piece;
    String style;

    /**
     * Constructs a BoardCell.
     */
    public BoardCell() {
        piece = new ImageView();
        style = "";

        piece.setSmooth(true);
        piece.fitHeightProperty().bind(heightProperty());
        piece.fitWidthProperty().bind(widthProperty());

        this.getChildren().add(piece);
    }

    /**
     * Sets the Image that is drawn on a BoardCell.
     * 
     * @param img Image that shall be displayed.
     */
    public synchronized void setImage(Image img) {
        piece.setImage(img);
        piece.setPreserveRatio(true);
    }

    /**
     * Changes the cells activation status. If it is active, the BoardCell displays that it is a
     * possible move to make with the selected piece.
     * 
     * @param status true/false
     */
    public void setActivationStatus(boolean status) {
        if (status) {
            this.setStyle("-fx-background-color: #00FF00;");
        } else {
            this.setStyle(style);
        }
    }

    /**
     * Changes the cells selection status. If it is active, the BoardCell displays that it is the
     * selected piece.
     * 
     * @param status true/false
     */
    public void setSelectionStatus(boolean status) {
        if (status) {
            this.setStyle("-fx-background-color: #0000FF;");
        } else {
            this.setStyle(style);
        }
    }

    /**
     * Switches the selection status.
     * 
     * @return Whether the selection status after calling this method is true or false.
     */
    public boolean changeSelectionStatus() {
        if (selected) {
            selected = false;
            this.setStyle(style);
        } else {
            selected = true;
            this.setStyle("-fx-background-color: #0000FF;");
        }
        return selected;
    }

    /**
     * Changes the BoardCell's background color to black.
     */
    public void changeBackgroundColorToBlack() {
        this.style = "-fx-background-color: #3D4753;";
        this.setStyle(style);
    }

    /**
     * Adds an event handler.
     * 
     * @param handler EventHandler for the onClickEvent.
     */
    public void addEventHandlerToPiece(EventHandler<MouseEvent> handler) {
        this.setOnMouseClicked(handler);
    }

    /**
     * Resets the EventHandler.
     */
    public void resetEventHandler() {
        this.setOnMouseClicked(null);
    }
}
