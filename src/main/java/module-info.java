open module JavaChess {
    // JavaFX
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
	requires transitive javafx.graphics;

    // Log4j
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    // Java-WebSocket API
    requires Java.WebSocket;

    // Gson JSON API
    requires com.google.gson;
    requires java.xml;

    // Apache Commons Collections
    requires org.apache.commons.collections4;
}