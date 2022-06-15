package com.gameofjess.javachess.client;

import java.net.URI;

import com.gameofjess.javachess.helper.messages.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

import com.gameofjess.javachess.helper.messages.ServerMessage;

public class Client extends WebSocketClient {

    private final static Logger log = LogManager.getLogger(Client.class);

    private final ConnectionHandler connectionHandler;

    /**
     * Constructs a new client that connects to serverURI and uses connectionHandler for communication
     * to the rest of the application.
     * 
     * @param serverUri URI of the server to connect to.
     * @param connectionHandler ConnectionHandler to send messages to.
     */
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
        log.debug("Received message: {}", message);
        Message smsg = new ServerMessage(message);
        connectionHandler.handleMessage(smsg);
    }

    @Override
    public void onClose(int exitCode, String reason, boolean remote) {
        log.info("Connection was terminated with exit code {}. Reason: {}", exitCode, reason);
        switch (exitCode) {
            case CloseFrame.UNEXPECTED_CONDITION, CloseFrame.GOING_AWAY, CloseFrame.REFUSE -> connectionHandler.sendDisconnectInformation(reason);
        }
    }

    @Override
    public void onError(Exception e) {
        log.error("An error occurred in the client instance: {}", e.getMessage());
    }
}
