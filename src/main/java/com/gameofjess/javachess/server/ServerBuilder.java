package com.gameofjess.javachess.server;

import java.net.InetSocketAddress;

/**
 *
 */

public class ServerBuilder {

    private int port = 8887;
    private String host;

    /**
     * Sets the port the server should listen on.
     * 
     * @param port Server port
     * @return ServerBuilder
     */
    public ServerBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Sets the host the server should listen on. Needed on machines that have several ip addresses.
     * 
     * @param host host address
     * @return ServerBuilder
     */

    public ServerBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Builds the specified server. If no port is given, the server listens on standard port 8887.
     * 
     * @return Server
     */

    public Server build() {
        Server server;
        if (host == null) {
            server = new Server(new InetSocketAddress(port));
        } else {
            server = new Server(new InetSocketAddress(host, port));
        }
        return server;
    }

}
