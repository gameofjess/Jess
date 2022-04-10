module SE2StartupProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires Java.WebSocket;
    requires org.apache.logging.log4j;

    opens org.example.javachess.mainpackage;
}
