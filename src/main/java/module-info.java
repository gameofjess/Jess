open module JavaChess {
    // JavaFX
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
	requires transitive javafx.graphics;

    // Log4j
    requires org.apache.logging.log4j;

    // Java-WebSocket API
    requires Java.WebSocket;

    // Gson JSON API
    requires com.google.gson;
    requires java.xml;

    // Apache Commons Collections
    requires org.apache.commons.collections4;

    exports com.gameofjess.javachess.chesslogic;
    exports com.gameofjess.javachess.chesslogic.pieces;
    exports com.gameofjess.javachess.client;
    exports com.gameofjess.javachess.helper.argumentparsing;
    exports com.gameofjess.javachess.helper.exceptions;
    exports com.gameofjess.javachess.helper.messages;
    exports com.gameofjess.javachess.mainpackage;
    exports com.gameofjess.javachess.server;
    exports com.gameofjess.javachess.gui.controller;
    exports com.gameofjess.javachess.helper.configuration;
}