module JavaChess {
    // JavaFX
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
	requires transitive javafx.graphics;

    // Log4j
    requires org.apache.logging.log4j;

    // Java-WebSocket API
    requires Java.WebSocket;

    //Gson JSON API
    requires com.google.gson;

    opens com.gameofjess.javachess.chesslogic;
    opens com.gameofjess.javachess.chesslogic.pieces;
    opens com.gameofjess.javachess.client;
    opens com.gameofjess.javachess.gui;
    opens com.gameofjess.javachess.helper.argumentparsing;
    opens com.gameofjess.javachess.helper.exceptions;
    opens com.gameofjess.javachess.helper.messages;
    opens com.gameofjess.javachess.mainpackage;
    opens com.gameofjess.javachess.server;

    exports com.gameofjess.javachess.chesslogic;
    exports com.gameofjess.javachess.chesslogic.pieces;
    exports com.gameofjess.javachess.client;
    exports com.gameofjess.javachess.gui;
    exports com.gameofjess.javachess.helper.argumentparsing;
    exports com.gameofjess.javachess.helper.exceptions;
    exports com.gameofjess.javachess.helper.messages;
    exports com.gameofjess.javachess.mainpackage;
    exports com.gameofjess.javachess.server;
}