package org.example.javachess.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.javachess.helper.messages.ServerMessage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    private final static Logger log = LogManager.getLogger(Client.class);

    Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.debug("Successfully connected!");
    }

    @Override
    public void onMessage(String message) {
        log.debug("Received message: " + message);
        ServerMessage smsg = new ServerMessage(message);
        handleServerMessage(smsg);
    }

    @Override
    public void onClose(int exitCode, String reason, boolean remote) {
        log.info("Connection was terminated with exit code " + exitCode + ". Reason: " + reason);
    }

    @Override
    public void onError(Exception e) {
        log.error("An error occurred in the client instance: " + e.getMessage());
    }

    /**
     * Handles received messages according to their type.
     * @param msg ServerMessage to be handled.
     */
    private void handleServerMessage(ServerMessage msg){
        switch (msg.getType()){
            case CHATMESSAGE -> {
                log.debug("Received new chat message from " + msg.getUsername() +  ":" + msg.getMessage());
                //TO BE IMPLEMENTED
            }
            case NEWMOVE -> {
                log.debug("Received new move from " + msg.getUsername() +  ":" + msg.getMessage());
                //TO BE IMPLEMENTED
            }
            case SERVERERROR -> {
                log.error("Received new server error: " + msg.getMessage());
                //TO BE IMPLEMENTED
            }
            case SERVERINFO -> {
                log.info("Received new server info: " + msg.getMessage());
                //TO BE IMPLEMENTED
            }
        }
    }
}
