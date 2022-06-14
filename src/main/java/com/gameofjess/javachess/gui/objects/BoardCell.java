package com.gameofjess.javachess.gui.objects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.helper.configuration.Config;
import com.gameofjess.javachess.helper.configuration.ConfigHandler;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class BoardCell extends StackPane {
    private static final Logger log = LogManager.getLogger(BoardCell.class);

    private final Color blackCellColor;
    private final Color whiteCellColor;
    private final Color activatedCellColor;
    private final Color selectedCellColor;
    private final Color checkCellColor;

    private boolean selected;
    private boolean activated;
    private boolean check;
    private boolean black;
    private final ImageView piece;

    /**
     * Constructs a BoardCell.
     */
    BoardCell() {
        selected = false;
        activated = false;
        check = false;
        black = false;

        Config config;
        try {
            config = new ConfigHandler().loadConfig(new File("config.json"));
        } catch (IOException e) {
            log.error("Could not read config file. Proceeding to use default values!");
            config = new Config();
        }

        blackCellColor = Color.web(config.getBlackCellColor());
        whiteCellColor = Color.web(config.getWhiteCellColor());
        activatedCellColor = Color.web(config.getActivatedCellColor());
        selectedCellColor = Color.web(config.getSelectedCellColor());
        checkCellColor = Color.web(config.getCheckCellColor());

        piece = new ImageView();

        piece.setSmooth(true);
        piece.fitHeightProperty().bind(heightProperty());
        piece.fitWidthProperty().bind(widthProperty());

        this.getChildren().add(piece);

        updateBackground();
    }

    /**
     * Sets the Image that is drawn on a BoardCell.
     * 
     * @param img Image that shall be displayed.
     */
    synchronized void setImage(Image img) {
        piece.setImage(img);
        piece.setPreserveRatio(true);
    }

    /**
     * Changes the cells activation status. If it is active, the BoardCell displays that it is a
     * possible move to make with the selected piece.
     * 
     * @param status true/false
     */
    void setActivationStatus(boolean status) {
        activated = status;
        updateBackground();
    }

    /**
     * Changes the cells selection status. If it is active, the BoardCell displays that it is the
     * selected piece.
     * 
     * @param status true/false
     */
    void setSelectionStatus(boolean status) {
        selected = status;
        updateBackground();
    }

    /**
     * Switches the selection status.
     *
     * @return Whether the selection status after calling this method is true or false.
     */
    boolean changeSelectionStatus() {
        if (selected) {
            selected = false;
        } else {
            selected = true;
        }
        updateBackground();
        return selected;
    }

    /**
     * Changes the cells check status. If it is active, the BoardCell displays that it is in check.
     *
     * @param status true/false
     */
    void setCheckStatus(boolean status) {
        check = status;
        updateBackground();
    }

    /**
     * Resets all cell status.
     *
     */
    void resetStatus() {
        selected = false;
        activated = false;
        check = false;
        updateBackground();
    }

    /**
     * Changes the BoardCell's background color to black.
     */
    void changeBackgroundColorToBlack() {
        this.black = true;
        updateBackground();
    }

    private void updateBackground() {
        List<BackgroundFill> backgroundFillList = new ArrayList<>();
        if (black) {
            backgroundFillList.add(new BackgroundFill(blackCellColor, CornerRadii.EMPTY, Insets.EMPTY));
        } else {
            backgroundFillList.add(new BackgroundFill(whiteCellColor, CornerRadii.EMPTY, Insets.EMPTY));
        }
        if (selected) {
            backgroundFillList.add(new BackgroundFill(selectedCellColor, CornerRadii.EMPTY, Insets.EMPTY));
        }
        if (activated) {
            backgroundFillList.add(new BackgroundFill(activatedCellColor, CornerRadii.EMPTY, Insets.EMPTY));
        }
        if (check) {
            backgroundFillList.add(new BackgroundFill(checkCellColor, new CornerRadii(75, true), Insets.EMPTY));
        }

        this.setBackground(new Background(backgroundFillList.toArray(new BackgroundFill[0])));
    }

    /**
     * Adds an event handler.
     * 
     * @param handler EventHandler for the onClickEvent.
     */
    void addEventHandlerToPiece(EventHandler<MouseEvent> handler) {
        this.setOnMouseClicked(handler);
    }

    /**
     * Resets the EventHandler.
     */
    void resetEventHandler() {
        this.setOnMouseClicked(null);
    }
}
