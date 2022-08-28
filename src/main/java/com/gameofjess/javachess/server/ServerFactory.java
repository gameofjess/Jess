package com.gameofjess.javachess.server;

import com.gameofjess.javachess.helper.configuration.ConfigLoader;
import com.gameofjess.javachess.helper.configuration.StandardConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * This builder is used to build a Server.
 */

public class ServerFactory {

    private static final Logger log = LogManager.getLogger(ServerFactory.class);

    private int port;
    private String host;
    private final boolean publicServer;

    public ServerFactory(boolean publicServer) {
        StandardConfig config;
        try {
            config = (StandardConfig) new ConfigLoader().loadConfig(new File("config.json"), StandardConfig.class);
        } catch (IOException e) {
            log.error("Could not read config file. Proceeding to use default values!");
            config = new StandardConfig();
        }

        port = config.getDefaultPort();
        host = config.getDefaultHostname();
        this.publicServer = publicServer;
    }

    /**
     * Sets the port the server should listen on.
     *
     * @param port Server port
     * @return ServerBuilder
     */
    public ServerFactory setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Sets the host the server should listen on. Needed on machines that have several ip addresses.
     *
     * @param host host address
     * @return ServerBuilder
     */

    public ServerFactory setHost(String host) {
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
        if (!publicServer) {
            if (host == null) {
                server = new PrivateServer(new InetSocketAddress(port));
            } else {
                server = new PrivateServer(new InetSocketAddress(host, port));
            }
        } else {

            if (host == null) {
                server = new PublicServer(new InetSocketAddress(port));
            } else {
                server = new PublicServer(new InetSocketAddress(host, port));
            }
        }
        server.setReuseAddr(true);
        return server;
    }

}
