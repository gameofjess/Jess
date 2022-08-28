package com.gameofjess.javachess.server;

import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public abstract class Server extends WebSocketServer {

    public Server(InetSocketAddress address) {
        super(address);
    }

    /**
     * @return String-Array with all current usernames.
     */
    abstract public String[] getUsers();

    /**
     * @return true, if server is open - false, if not
     */
    abstract public boolean getServerStatus();

}
