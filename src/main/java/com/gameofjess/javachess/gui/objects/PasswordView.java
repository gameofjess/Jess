package com.gameofjess.javachess.gui.objects;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PasswordView extends VBox {

    private static final Logger log = LogManager.getLogger(PasswordView.class);

    private final Text errorMessage;
    private final Button joinButton;
    private final PasswordField passwordField;

    public PasswordView() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);

        Text logo = new Text("Jess");
        logo.setFont(Font.font("System", FontWeight.BOLD, 64));

        Label passwordLabel = new Label("PASSWORD");
        passwordLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        passwordLabel.setPrefSize(216, 17);
        VBox.setMargin(passwordLabel, new Insets(10, 0, 0, 0));

        HBox passwordBox = new HBox();
        passwordBox.setAlignment(Pos.TOP_CENTER);

        passwordField = new PasswordField();
        HBox.setMargin(passwordField, new Insets(0, 10, 0, 0));
        passwordField.setPadding(new Insets(7));
        passwordField.setFont(Font.font(18));
        passwordField.setStyle("-fx-background-color: #EEECEC;");
        passwordField.setPrefSize(143, 31);

        joinButton = new Button("JOIN");
        joinButton.setPadding(new Insets(7, 10, 7, 10));
        joinButton.setFont(Font.font("System", FontWeight.BOLD, 18));
        joinButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-cursor: hand;");

        passwordBox.getChildren().addAll(passwordField, joinButton);

        HBox errorMessageBox = new HBox();
        errorMessageBox.setAlignment(Pos.CENTER);

        errorMessage = new Text();
        errorMessage.setFont(Font.font("System", FontWeight.BOLD, 13));
        errorMessage.setStyle("-fx-fill: red;");

        errorMessageBox.getChildren().add(errorMessage);

        passwordLabel.setLabelFor(passwordField);

        this.getChildren().addAll(logo, passwordLabel, passwordBox, errorMessageBox);
    }

    public void addEventListener(EventHandler<ActionEvent> eventHandler) {
        passwordField.setOnAction(eventHandler);
        joinButton.setOnAction(eventHandler);
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void displayErrorMessage(String message) {
        log.debug("Displaying error message: {}", message);
        errorMessage.setText(message);
        errorMessage.setTextAlignment(TextAlignment.CENTER);

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.seconds(2));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);
        fade.setNode(errorMessage);
        fade.play();
    }

}
