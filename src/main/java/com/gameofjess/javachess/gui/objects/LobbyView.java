package com.gameofjess.javachess.gui.objects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LobbyView extends HBox {


    private final Text name;
    private final Text playerCount;
    private final Button joinButton;

    LobbyView(String name, String[] players, EventHandler<ActionEvent> buttonEvent) {
        this.name = new Text(name);
        this.playerCount = new Text(players.length + "/2");
        this.joinButton = new Button();

        this.name.setFont(Font.font("System", FontWeight.BOLD, 16));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox joinBox = new HBox();
        joinBox.setSpacing(20);
        joinBox.setPadding(new Insets(10));
        joinBox.setAlignment(Pos.CENTER_RIGHT);
        joinBox.getChildren().addAll(playerCount, joinButton);

        joinButton.setOnAction(buttonEvent);
        joinButton.setText("JOIN");
        joinButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-cursor: hand;");
        joinButton.setPadding(new Insets(10, 10, 7, 7));

        this.setPadding(new Insets(15));
        this.setAlignment(Pos.CENTER_LEFT);

        this.setBackground(new Background(new BackgroundFill(Color.valueOf("#FFFFFF"), new CornerRadii(10), Insets.EMPTY)));
        this.setBorder(new Border(new BorderStroke(Color.valueOf("#EFEFEF"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));

        getChildren().addAll(this.name, spacer, joinBox);
    }

}
