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

    opens org.example.javachess.chesslogic;
    opens org.example.javachess.chesslogic.pieces;
    opens org.example.javachess.client;
    opens org.example.javachess.gui;
    opens org.example.javachess.helper.argumentparsing;
    opens org.example.javachess.helper.exceptions;
    opens org.example.javachess.helper.messages;
    opens org.example.javachess.mainpackage;
    opens org.example.javachess.server;

    exports org.example.javachess.chesslogic;
    exports org.example.javachess.chesslogic.pieces;
    exports org.example.javachess.client;
    exports org.example.javachess.gui;
    exports org.example.javachess.helper.argumentparsing;
    exports org.example.javachess.helper.exceptions;
    exports org.example.javachess.helper.messages;
    exports org.example.javachess.mainpackage;
    exports org.example.javachess.server;
}