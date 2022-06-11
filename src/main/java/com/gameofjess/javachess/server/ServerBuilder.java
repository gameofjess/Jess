package com.gameofjess.javachess.server;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.helper.configuration.Config;
import com.gameofjess.javachess.helper.configuration.ConfigHandler;

/**
 * This builder is used to build a Server.
 */

public class ServerBuilder {

    private static final Logger log = LogManager.getLogger(ServerBuilder.class);

    private int port;
    private String host;

    public ServerBuilder() {
        Config config;
        try {
            config = new ConfigHandler().loadConfig(new File("config.json"));
        } catch (IOException e) {
            log.error("Could not read config file. Proceeding to use default values!");
            config = new Config();
        }

        port = config.getDefaultPort();
        host = config.getDefaultHostname();
    }

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
