package com.gameofjess.javachess.gui.helper.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.helper.game.Pieces;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PromotionSelectView extends VBox {

    private static final Logger log = LogManager.getLogger(PromotionSelectView.class);

    private final ImageView queen;
    private final ImageView knight;
    private final ImageView rook;
    private final ImageView bishop;

    private final Color color;

    public PromotionSelectView(Color color) {
        this.setAlignment(Pos.CENTER);

        Label promotionLabel = new Label("Select promotion:");
        promotionLabel.setFont(new Font("System Bold", 12));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        VBox.setVgrow(hBox, Priority.SOMETIMES);

        this.color = color;

        queen = new ImageView();
        knight = new ImageView();
        rook = new ImageView();
        bishop = new ImageView();

        queen.fitHeightProperty().bind(heightProperty().divide(2));
        queen.setSmooth(true);
        queen.setPreserveRatio(true);

        knight.fitHeightProperty().bind(heightProperty().divide(2));
        knight.setSmooth(true);
        knight.setPreserveRatio(true);

        rook.fitHeightProperty().bind(heightProperty().divide(2));
        rook.setSmooth(true);
        rook.setPreserveRatio(true);

        bishop.fitHeightProperty().bind(heightProperty().divide(2));
        bishop.setSmooth(true);
        bishop.setPreserveRatio(true);

        HBox.setHgrow(queen, Priority.SOMETIMES);
        HBox.setHgrow(knight, Priority.SOMETIMES);
        HBox.setHgrow(rook, Priority.SOMETIMES);
        HBox.setHgrow(bishop, Priority.SOMETIMES);

        hBox.getChildren().addAll(queen, knight, rook, bishop);
        getChildren().addAll(promotionLabel, hBox);
    }

    public void setImages() {
        queen.setImage(Pieces.QUEEN.getImage(color == Color.WHITE));
        knight.setImage(Pieces.KNIGHT.getImage(color == Color.WHITE));
        rook.setImage(Pieces.ROOK.getImage(color == Color.WHITE));
        bishop.setImage(Pieces.BISHOP.getImage(color == Color.WHITE));
    }

    public void setEventHandlerByColumn(int column, EventHandler<MouseEvent> handler) {
        switch (column) {
            case 0 -> queen.setOnMouseClicked(handler);
            case 1 -> knight.setOnMouseClicked(handler);
            case 2 -> rook.setOnMouseClicked(handler);
            case 3 -> bishop.setOnMouseClicked(handler);
            default -> throw new RuntimeException("Invalid column!");
        }
    }

}
