package com.gameofjess.javachess.client;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.gameofjess.javachess.helper.messages.ServerMessage;

public class Client extends WebSocketClient {

    private final static Logger log = LogManager.getLogger(Client.class);

    private final ConnectionHandler connectionHandler;

    Client(URI serverUri, ConnectionHandler connectionHandler) {
        super(serverUri);
        this.connectionHandler = connectionHandler;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.debug("Successfully connected!");
    }

    @Override
    public void onMessage(String message) {
        log.debug("Received message: " + message);
        ServerMessage smsg = new ServerMessage(message);
        connectionHandler.handleServerMessage(smsg);
    }

    @Override
    public void onClose(int exitCode, String reason, boolean remote) {
        log.info("Connection was terminated with exit code " + exitCode + ". Reason: " + reason);
    }

    @Override
    public void onError(Exception e) {
        log.error("An error occurred in the client instance: " + e.getMessage());
    }
}
