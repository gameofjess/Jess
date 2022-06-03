package org.example.javachess.gui.helper.objects;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoardPane extends HBox {

    private static final Logger log = LogManager.getLogger(BoardPane.class);

    private final GridPane boardGrid;
    private final VBox vBox;

    public BoardPane() {
        vBox = new VBox();

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

    public void resetActivationStatus() {
        for (Node n : boardGrid.getChildren()) {
            BoardCell cell = (BoardCell) n;
            cell.changeActivationStatus(false);
        }
    }

    public void setActivationStatusByCell(boolean status, int row, int column) {
        log.debug("Activating at coordinate ({}|{})", column, row);
        getCellByIndex(row, column).changeActivationStatus(status);
    }

    public void setImageByCell(Image img, int row, int column) {
        getCellByIndex(row, column).setImage(img);
    }

    public void setPieceEventHandlerByCell(EventHandler<MouseEvent> handler, int row, int column) {
        getCellByIndex(row, column).addEventHandlerToPiece(handler);
    }

    private BoardCell getCellByIndex(int row, int column) {
        BoardCell result = null;
        ObservableList<Node> childrens = boardGrid.getChildren();

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
