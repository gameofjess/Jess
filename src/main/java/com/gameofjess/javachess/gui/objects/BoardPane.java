package com.gameofjess.javachess.gui.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class BoardPane extends HBox {

    private static final Logger log = LogManager.getLogger(BoardPane.class);

    private final GridPane boardGrid;

    private final boolean flipped;

    /**
     * Constructs a square BoardPane with square cells. This is a variation of Marv's proposed solution
     * as seen
     * <a href="https://stackoverflow.com/questions/44979700/square-gridpane-of-square-cells">here</a>.
     */
    public BoardPane(boolean isWhite) {
        flipped = isWhite;

        VBox vBox = new VBox();

        vBox.alignmentProperty().set(Pos.CENTER);
        alignmentProperty().set(Pos.CENTER);

        boardGrid = new GridPane();

        final NumberBinding binding = Bindings.min(widthProperty(), heightProperty());

        boardGrid.setMinSize(200, 200);
        vBox.prefWidthProperty().bind(binding);
        vBox.prefHeightProperty().bind(binding);
        vBox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

        vBox.setFillWidth(true);
        VBox.setVgrow(boardGrid, Priority.ALWAYS);

        final int sideLength = 8;
        for (int i = 0; i < sideLength; i++) {
            final ColumnConstraints columnConstraints = new ColumnConstraints(Control.USE_PREF_SIZE, Control.USE_COMPUTED_SIZE, Double.MAX_VALUE);
            columnConstraints.setHgrow(Priority.SOMETIMES);
            boardGrid.getColumnConstraints().add(columnConstraints);

            final RowConstraints rowConstraints = new RowConstraints(Control.USE_PREF_SIZE, Control.USE_COMPUTED_SIZE, Double.MAX_VALUE);
            rowConstraints.setVgrow(Priority.SOMETIMES);
            boardGrid.getRowConstraints().add(rowConstraints);
        }

        vBox.getChildren().add(boardGrid);

        getChildren().add(vBox);

        HBox.setHgrow(this, Priority.ALWAYS);

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                final BoardCell child = new BoardCell();

                if ((i + j) % 2 == 1) {
                    child.changeBackgroundColorToBlack();
                }

                child.maxWidthProperty().bind(boardGrid.widthProperty().divide(8));
                child.maxHeightProperty().bind(boardGrid.heightProperty().divide(8));


                child.minWidthProperty().bind(boardGrid.widthProperty().divide(8));
                child.minHeightProperty().bind(boardGrid.heightProperty().divide(8));



                GridPane.setRowIndex(child, i);
                GridPane.setColumnIndex(child, j);
                boardGrid.getChildren().add(child);
            }
        }
    }

    /**
     * Resets the cell's status.
     */
    public void resetStatus() {
        for (Node n : boardGrid.getChildren()) {
            BoardCell cell = (BoardCell) n;
            cell.resetStatus();
        }
    }

    /**
     * Changes a specific cell's selection status.
     * 
     * @param row Cell's row.
     * @param column Cell's column.
     * @return The selection status after calling the method.
     */
    public boolean changeSelectionStatusByCell(int row, int column) {
        log.debug("Changing selection status for cell at ({}|{})", column, row);
        return getCellByIndex(row, column).changeSelectionStatus();

    }

    /**
     * Changes a specific cell's activation status.
     * 
     * @param status Status to which the activation status shall be set.
     * @param row Cell's row.
     * @param column Cell's column.
     */
    public void setActivationStatusByCell(boolean status, int row, int column) {
        log.debug("Changing activation status to {} for cell at ({}|{})", status, column, row);
        getCellByIndex(row, column).setActivationStatus(status);
    }

    /**
     * Sets a specific cell's selection status.
     * 
     * @param status Status to which the selection status shall be set.
     * @param row Cell's row.
     * @param column Cell's column.
     */
    public void setSelectionStatusByCell(boolean status, int row, int column) {
        log.debug("Changing selection status to {} for cell at ({}|{})", status, column, row);
        getCellByIndex(row, column).setSelectionStatus(status);
    }

    /**
     * Sets a specific cell's check status.
     *
     * @param status Status to which the check status shall be set.
     * @param row Cell's row.
     * @param column Cell's column.
     */
    public void setCheckStatusByCell(boolean status, int row, int column) {
        log.debug("Changing selection status to {} for cell at ({}|{})", status, column, row);
        getCellByIndex(row, column).setCheckStatus(status);
    }

    /**
     * Sets a specific cell's image.
     * 
     * @param img Image to be drawn.
     * @param row Cell's row.
     * @param column Cell's column.
     */
    public void setImageByCell(Image img, int row, int column) {
        log.debug("Setting image for cell at ({}|{})", column, row);
        getCellByIndex(row, column).setImage(img);
    }

    /**
     * Sets a specific cell's event handler for the onClick-Event.
     * 
     * @param handler EventHandler for the onClick-Event.
     * @param row Cell's row.
     * @param column Cell's column.
     */
    public void setPieceEventHandlerByCell(EventHandler<MouseEvent> handler, int row, int column) {
        log.debug("Setting event handler at coordinate ({}|{})", column, row);
        getCellByIndex(row, column).addEventHandlerToPiece(handler);
    }

    /**
     * Resets all the images.
     */
    public void resetImages() {
        log.debug("Resetting images for all cells.");
        for (Node n : boardGrid.getChildren()) {
            BoardCell cell = (BoardCell) n;
            cell.setImage(null);
        }
    }

    /**
     * Resets all the event handlers.
     */
    public void resetEventHandlers() {
        log.debug("Resetting event handlers for all cells.");
        for (Node n : boardGrid.getChildren()) {
            BoardCell cell = (BoardCell) n;
            cell.resetEventHandler();
        }
    }

    /**
     * Gets a specific cell.
     * 
     * @param row Cell's row.
     * @param column Cell's column.
     * @return the cell specified.
     */
    private BoardCell getCellByIndex(int row, int column) {
        BoardCell result = null;
        ObservableList<Node> childrens = boardGrid.getChildren();

        if (flipped) {
            row = Math.abs(row - 7);
            column = Math.abs(column - 7);

            /*
             * switch(row) { case 0 -> row = 7; case 1 -> row = 6; case 2 -> row = 5; case 3 -> row = 4; case 4
             * -> row = 3; case 5 -> row = 2; case 6 -> row = 1; case 7 -> row = 0; }
             * 
             */
        }

        for (Node node : childrens) {
            if (node.hasProperties() && GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                    result = (BoardCell) node;
                }
            }
        }

        return result;
    }
}
