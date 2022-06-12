package com.gameofjess.javachess.gui.helper.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

/**
 * View for single captured pieces.
 */
public class CapturedPieceView extends HBox {

    private static final Logger log = LogManager.getLogger(CapturedPieceView.class);

    private final ImageView pieceImage;

    private final Label countLabel;

    private int count;

    /**
     * Constructs a CapturedPieceView with the given Image.
     * 
     * @param img Image to be used.
     */
    public CapturedPieceView(Image img) {
        log.trace("Constructing a single captured piece display.");
        this.pieceImage = new ImageView(img);
        pieceImage.setPreserveRatio(true);
        pieceImage.setSmooth(true);
        pieceImage.fitHeightProperty().bind(heightProperty().divide(2));
        pieceImage.fitWidthProperty().bind(widthProperty().divide(2));

        HBox.setHgrow(pieceImage, Priority.SOMETIMES);

        alignmentProperty().set(Pos.CENTER);

        this.countLabel = new Label();
        this.countLabel.setFont(new Font("System", 12));
        count = 0;

        updateCount();
        this.getChildren().add(pieceImage);
        this.getChildren().add(countLabel);
    }

    /**
     * Adds a captured piece to the counter.
     */
    public void add() {
        log.trace("Increasing count to: {}", count++);
        updateCount();
    }

    /**
     * Updates the count label.
     */
    private void updateCount() {
        String text = "x " + count;
        log.trace("Updating label text to: {}", text);
        this.countLabel.setText(text);
    }

}
