package com.gameofjess.javachess.client;

import com.gameofjess.javachess.gui.controller.GameController;
import com.gameofjess.javachess.gui.controller.PublicServerJoinMenuController;
import com.gameofjess.javachess.helper.configuration.ConfigLoader;
import com.gameofjess.javachess.helper.configuration.StandardConfig;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.helper.messages.Message;
import com.gameofjess.javachess.helper.messages.MessageType;
import com.gameofjess.javachess.helper.publicserver.EncryptionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionHandler {

    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    private final Client client;
    private final boolean publicConnection;
    private final Queue<Message> messageQueue = new LinkedBlockingQueue<>();

    private GameController gameController;

    private PublicServerJoinMenuController menuController;

    public ConnectionHandler(String host, int port) throws InvalidHostnameException, URISyntaxException, InvalidPortException {
        this(host, port, false);
    }

    /**
     * Constructs a new connectionHandler that will construct a Client.
     *
     * @param host Host to connect to.
     * @param port Port the host is listening on.
     * @throws InvalidHostnameException if the hostname is invalid.
     * @throws InvalidPortException     if the port is invalid.
     * @throws URISyntaxException       if the given string violates RFC 2396 in any other way.
     */
    public ConnectionHandler(String host, int port, boolean publicConnection)
            throws InvalidHostnameException, InvalidPortException, URISyntaxException {
        String regexHostname =
                "^(([a-zA-Z]{1})|([a-zA-Z]{1}[a-zA-Z]{1})|([a-zA-Z]{1}[0-9]{1})|([0-9]{1}[a-zA-Z]{1})|([a-zA-Z0-9][a-zA-Z0-9-_]{1,61}[a-zA-Z0-9]))\\.([a-zA-Z]{2,6}|[a-zA-Z0-9-]{2,30}\\.[a-zA-Z]{2,3})$";
        String regexIP =
                "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

        this.publicConnection = publicConnection;

        StandardConfig config;
        try {
            config = (StandardConfig) new ConfigLoader().loadConfig(new File("config.json"), StandardConfig.class);
        } catch (IOException e) {
            log.error("Could not read config file. Proceeding to use default values!");
            config = new StandardConfig();
        }

        if (host.matches(regexHostname) || host.matches(regexIP)) {
            if (port <= 65535 && port >= 1024) {
                String schema;
                if (publicConnection) {
                    schema = "wss://";
                } else {
                    schema = "ws://";
                }
                client = new Client(new URI(schema + host + ":" + port), this);

                if (publicConnection) {
                    EncryptionManager encryptionManager = new EncryptionManager();
                    if (config.getIgnoreCertificateAuthenticity()) {
                        client.setSocketFactory(encryptionManager.getInsecureSSLContext().getSocketFactory());
                    } else {
                        client.setSocketFactory(encryptionManager.getDefaultSSLContext().getSocketFactory());
                    }
                }
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
    public void send(Message msg) {
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
            log.info("Attempting to connect to {}.", client.getURI());
            log.debug("Attempting to assign username {}.", username);
            client.addHeader("username", username);

            if (publicConnection) {
                log.debug("Attempting to assign public flag.");
                client.addHeader("public", "true");
            } else {
                log.debug("Attempting to assign color {}.", Color.RANDOM);
                client.addHeader("color", Color.RANDOM.name());
            }

            boolean connected = client.connectBlocking(1000, TimeUnit.MILLISECONDS);
            if (connected) {
                log.info("Connected successfully!");
            }
            return connected;
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * Establishes the connection for the host user.
     *
     * @param username Username to be used.
     * @param color Color the user wishes.
     * @return If the connection is established successfully the method will return true, otherwise it
     *         returns false;
     */
    public boolean connect(String username, Color color) {
        try {
            log.info("Attempting to connect to {}.", client.getURI());

            log.debug("Attempting to assign username {}.", username);
            client.addHeader("username", username);

            log.debug("Attempting to assign color {}.", color.name());
            client.addHeader("color", color.name());

            boolean connected = client.connectBlocking(1, TimeUnit.SECONDS);

            if (connected) {
                log.info("Connected successfully!");
            } else {
                client.getSocket().close();
            }

            return connected;
        } catch (InterruptedException | IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * @return true if the client is connected, false if it is not.
     */
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

    /**
     * Sets the GameController used for communication.
     *
     * @param gameController GameController to be used.
     */
    public void setGameController(GameController gameController) {
        this.menuController = null;
        this.gameController = gameController;

        if (!messageQueue.isEmpty()) {
            messageQueue.forEach(gameController::receiveMessage);
        }
    }

    /**
     * Sets the PublicServerJoinMenuController used for communication.
     *
     * @param menuController PublicServerJoinMenuController to be used.
     */
    public void setMenuController(PublicServerJoinMenuController menuController) {
        this.menuController = menuController;
    }

    /**
     * Handles received messages according to their type.
     *
     * @param msg Message to be handled.
     */
    void handleMessage(Message msg) {
        log.debug("Received {} from {}: {}", msg.getType().name(), msg.getUsername(), msg.getMessage());
        if (gameController != null) {
            log.trace("Relaying received message to GameController!");
            gameController.receiveMessage(msg);
        } else {
            if (msg.getType() == MessageType.BEGINMATCH
                    || msg.getType() == MessageType.COLORINFO
                    || msg.getType() == MessageType.USERLIST
                    || msg.getType() == MessageType.NEWMOVE
                    || msg.getType() == MessageType.CHECKMATE) {
                log.trace("Queueing received message for GameController!");
                messageQueue.add(msg);
            } else {
                log.trace("Relaying received message to MenuController!");
                menuController.receiveMessage(msg);
            }
        }
    }

    void sendDisconnectInformation(String message) {
        if (gameController != null) {
            log.debug("Delivering disconnect information to GameController!");
            gameController.endGame(message);
        } else {
            log.trace("Not delivering disconnect information to GameController. Disconnecting in lobby view.");
        }
    }
}
