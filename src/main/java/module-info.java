module JavaChess {
    // JavaFX
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    // Log4j
    requires org.apache.logging.log4j;

    // Java-WebSocket API
    requires Java.WebSocket;

    //Gson JSON API
    requires com.google.gson;

    opens org.example.javachess.mainpackage;
    opens org.example.javachess.gui;
}