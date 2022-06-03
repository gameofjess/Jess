package org.example.javachess.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.javachess.helper.exceptions.InvalidHostnameException;
import org.example.javachess.helper.exceptions.InvalidPortException;
import org.example.javachess.helper.messages.ClientMessage;

public class ConnectionHandler {

    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    private final Client client;

    public ConnectionHandler(String host, int port)
            throws InvalidHostnameException, InvalidPortException, URISyntaxException {
        String regexHostname =
                "^(([a-zA-Z]{1})|([a-zA-Z]{1}[a-zA-Z]{1})|([a-zA-Z]{1}[0-9]{1})|([0-9]{1}[a-zA-Z]{1})|([a-zA-Z0-9][a-zA-Z0-9-_]{1,61}[a-zA-Z0-9]))\\.([a-zA-Z]{2,6}|[a-zA-Z0-9-]{2,30}\\.[a-zA-Z]{2,3})$";
        String regexIP =
                "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

        if (host.matches(regexHostname) || host.matches(regexIP)) {
            if (port <= 65535 && port >= 1024) {
                client = new Client(new URI("ws://" + host + ":" + port));
            } else {
                throw new InvalidPortException(String.valueOf(port));
            }
        } else {
            throw new InvalidHostnameException(host);
        }
    }

    /**
     * Sends a message.
     * 
     * @param msg Sends a message to the server.
     */
    public void send(ClientMessage msg) {
        client.send(msg.toJSON());
    }

    /**
     * Establishes the connection.
     * 
     * @param username Username to be used.
     * @return If the connection is established successfully the method will return true, otherwise
     *         it returns false;
     */
    public boolean connect(String username) {
        try {
            log.info("Attempting to connect to " + client.getURI());
            log.debug("Attempting to assign username " + username);
            client.addHeader("username", username);
            boolean connected = client.connectBlocking(40, TimeUnit.MILLISECONDS);
            if (connected) {
                log.info("Connected successfully!");
            }
            return connected;
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getConnectionStatus() {
        return client.isOpen();
    }

    /**
     * Closes the connection
     *
     * @param code Close code (as seen here:
     *        <a href="https://www.javadoc.io/static/org.java-websocket/Java-WebSocket/1.5.3/org/java_websocket/framing/CloseFrame.html">...</a>)
     * @param reason Reason for closing the connection
     */
    public void disconnect(int code, String reason) {
        client.close(code, reason);
    }

}
