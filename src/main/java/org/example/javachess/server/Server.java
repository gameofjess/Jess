package org.example.javachess.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

public class Server extends WebSocketServer{

    private static final Logger log = LogManager.getLogger(Server.class);

    Server (InetSocketAddress address){
        super(address);
    }


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        log.info("New connection from " + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket webSocket, int exitCode, String reason, boolean remote) {
        log.info("Connection from " + webSocket.getRemoteSocketAddress() + " was termintated with exit code " + exitCode + ". Reason: " + reason);
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        log.debug("Received message from " + webSocket.getRemoteSocketAddress() + ": " + message);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        log.error("An error occurred in the server instance: " + e.getMessage());
    }

    @Override
    public void onStart() {
        log.info("Server successfully started");
    }

}
