package com.gameofjess.javachess.gui.helper.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * BoardOverlay is the overlay displayed when waiting for an opponent or when the game ended.
 */
public class BoardOverlay extends HBox {

    private static final Logger log = LogManager.getLogger(BoardOverlay.class);

    private final Label boardOverlayText;

    private final Button exitButton;

    public BoardOverlay() {
        boardOverlayText = new Label();
        boardOverlayText.setFont(new Font("System Bold", 16));

        exitButton = new Button();
        exitButton.setText("EXIT");
        exitButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-cursor: hand;");

        this.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        VBox vBox = new VBox();

        vBox.setSpacing(15);

        vBox.alignmentProperty().set(Pos.CENTER);
        alignmentProperty().set(Pos.CENTER);

        final NumberBinding binding = Bindings.min(widthProperty(), heightProperty());

        vBox.prefWidthProperty().bind(binding);
        vBox.prefHeightProperty().bind(binding);
        vBox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

        this.idProperty().setValue("boardOverlay");
        this.setBackground(new Background(new BackgroundFill(Color.web("rgba(255, 255, 255, .75)"), CornerRadii.EMPTY, Insets.EMPTY)));

        vBox.getChildren().add(boardOverlayText);
        vBox.getChildren().add(exitButton);
        this.getChildren().add(vBox);
    }

    /**
     * Sets the text displayed on the overlay.
     * 
     * @param message Text to be displayed.
     */
    public void setBoardOverlayText(String message) {
        log.trace("Changing board overlay message to \"{}\"", message);
        this.boardOverlayText.setText(message);
    }

    /**
     * Sets the event handler for the exit button.
     * 
     * @param handler EventHandler that shall be used.
     */
    public void setExitButtonOnActionEventHandler(EventHandler<ActionEvent> handler) {
        log.trace("Setting exit button event handler.");
        exitButton.setOnAction(handler);
    }

}
