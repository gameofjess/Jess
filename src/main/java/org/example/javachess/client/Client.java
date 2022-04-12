package org.example.javachess.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    private final static Logger log = LogManager.getLogger(Client.class);

    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.debug("Successfuly connected!");
    }

    @Override
    public void onMessage(String message) {
        log.debug("Received message: " + message);
    }

    @Override
    public void onClose(int exitCode, String reason, boolean remote) {
        log.info("Connection was termintated with exit code " + exitCode + ". Reason: " + reason);
    }

    @Override
    public void onError(Exception e) {
        log.error("An error occurred in the client instance: " + e.getMessage());
    }
}
