package com.gameofjess.javachess.gui.objects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LobbyList extends ScrollPane {

    private static final Logger log = LogManager.getLogger(LobbyList.class);

    private final VBox lobbyBox;

    public LobbyList() {
        this.getStylesheets().add("scrollbar.css");

        this.setBackground(new Background(new BackgroundFill(Color.valueOf("#EFEFEF"), new CornerRadii(10), Insets.EMPTY)));
        //this.setBorder(new Border(new BorderStroke(Color.valueOf("#EFEFEF"), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));

        VBox.setVgrow(this, Priority.ALWAYS);
        this.setFitToWidth(true);

        this.lobbyBox = new VBox();
        lobbyBox.setFillWidth(true);
        lobbyBox.setSpacing(20);
        lobbyBox.setPadding(new Insets(10, 0, 10, 10));

        this.setContent(lobbyBox);
    }

    public void addLobbyView(String name, String[] players, EventHandler<ActionEvent> buttonEvent) {
        log.debug("Adding LobbyView with name {} and players {}.", name, players);
        lobbyBox.getChildren().add(new LobbyView(name, players, buttonEvent));
    }

    public void clear() {
        log.debug("Clearing lobby list!");
        lobbyBox.getChildren().clear();
    }

}
