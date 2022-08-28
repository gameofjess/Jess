package com.gameofjess.javachess.server;

import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.helper.messages.ClientMessage;
import com.gameofjess.javachess.helper.messages.Message;
import com.gameofjess.javachess.helper.messages.MessageType;
import com.gameofjess.javachess.helper.messages.ServerMessage;
import com.gameofjess.javachess.helper.publicserver.PublicServerStorage;
import com.gameofjess.javachess.helper.publicserver.User;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PublicServer extends Server {

    private static final Logger log = LogManager.getLogger(PublicServer.class);

    private final PublicServerStorage storage = new PublicServerStorage();

    private boolean isOpen = false;


    /**
     * Instantiates a Server.
     *
     * @param address Address to listen on.
     */
    PublicServer(InetSocketAddress address) {
        super(address);
    }


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
        log.info("New connection from {}.", webSocket.getRemoteSocketAddress());

        if (!(handshake.hasFieldValue("public") && handshake.getFieldValue("public").equals("true"))) {
            webSocket.send(new ServerMessage(MessageType.SERVERERROR, "You are trying to join a public server!").toJSON());
            webSocket.close(CloseFrame.REFUSE, "You are trying to join a public server!");
            return;
        }

        if (handshake.hasFieldValue("username")) {
            String username = handshake.getFieldValue("username");
            if (storage.doesUsernameExist(username)) {
                log.info("Username {} already exists! Closing connection.", username);
                webSocket.send(new ServerMessage(MessageType.SERVERERROR, "Username already in use!").toJSON());
                webSocket.close(CloseFrame.REFUSE, "Username is already used!");
                return;
            } else {
                log.info("Username of new user is {} ", username);
                UUID u = storage.createUser(username);
                log.debug("Attaching unique ID {}.", u);
                webSocket.setAttachment(u);
            }
        } else {
            log.warn("Client with invalid username field tried to connect to the server!");
            webSocket.close(CloseFrame.REFUSE, "Invalid information!");
        }

        webSocket.send(new ServerMessage(MessageType.LOBBYLIST, storage.getLobbiesJSON()).toJSON());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        UUID userUUID = conn.getAttachment();
        String username = storage.getUsernameByUUID(userUUID);

        log.info("Connection from {} was terminated with exit code {}. Reason: {}",
                conn.getRemoteSocketAddress(), code, reason);

        UUID lobbyUUID = storage.getLobbyUUIDByUserUUID(userUUID);
        storage.removeUser(userUUID);

        if (lobbyUUID != null) {
            log.debug("Trying to clear lobby of {}.", userUUID);
            List<User> lobbyUsers = storage.getUsersByLobbyUUID(lobbyUUID);
            storage.removeLobby(lobbyUUID);
            if (lobbyUsers.size() != 0) {
                lobbyUsers.forEach(user -> {
                    log.debug("Disconnecting opponent of {} in {}!", userUUID, lobbyUUID);
                    WebSocket webSocket = getWebSocketByUUID(user.getUuid());
                    if (webSocket != null) {
                        webSocket.close(CloseFrame.GOING_AWAY, username + " disconnected!");
                    }
                });
            }
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Message msg = new ClientMessage(message);
        UUID userUUID = conn.getAttachment();
        String username = storage.getUsernameByUUID(userUUID);

        switch (msg.getType()) {
            case CREATELOBBY -> {
                String[] msgArray = msg.getMessage().split(",");
                String name = msgArray[0];
                String password = msgArray[1];
                log.info("User with name {} is trying to create a lobby with name {}!", storage.getUsernameByUUID(conn.getAttachment()), name);
                if (!storage.doesLobbyNameExist(name)) {
                    log.debug("Proceeding to create lobby with name {}!", name);
                    UUID u = storage.createLobby(name, password);
                    storage.addUserToLobby(conn.getAttachment(), u, password);
                    conn.send(new ServerMessage(MessageType.JOINLOBBY, "").toJSON());
                } else {
                    log.info("Lobby name {} already exists!", name);
                    conn.send(new ServerMessage(MessageType.SERVERERROR, "Lobby name already exists!").toJSON());
                }
            }
            case JOINLOBBY -> {
                String[] msgArray = msg.getMessage().split(",");
                UUID uuid = UUID.fromString(msgArray[0]);
                String password = msgArray[1];
                if (storage.getUsersByLobbyUUID(uuid).size() > 1) {
                    conn.send(new ServerMessage(MessageType.SERVERERROR, "Lobby is full!").toJSON());
                    return;
                }
                if (storage.addUserToLobby(conn.getAttachment(), uuid, password)) {
                    conn.send(new ServerMessage(MessageType.JOINLOBBY, "").toJSON());
                    if (storage.getUsersByLobbyUUID(uuid).size() == 2) {
                        beginMatch(storage.getUsernameByUUID(userUUID), storage.getLobbyUUIDByUserUUID(userUUID));
                    }
                } else {
                    conn.send(new ServerMessage(MessageType.SERVERERROR, "Wrong credentials!").toJSON());
                }
            }
            case LOBBYLIST -> conn.send(new ServerMessage(MessageType.LOBBYLIST, storage.getLobbiesJSON()).toJSON());
            case CHATMESSAGE -> {
                UUID lobbyUUID = storage.getLobbyUUIDByUserUUID(userUUID);
                List<User> userList = storage.getUsersByLobbyUUID(lobbyUUID);
                log.debug("Received chat message from user {} in lobby {}!", username, lobbyUUID);
                if (userList != null) {
                    userList.forEach(user -> {
                        WebSocket webSocket = getWebSocketByUUID(user.getUuid());
                        if (webSocket != null) {
                            log.trace("Sending chat message from user {} to user {}!", userUUID, user.getUuid());
                            webSocket.send(new ServerMessage(storage.getUsernameByUUID(userUUID), MessageType.CHATMESSAGE, msg.getMessage()).toJSON());
                        }
                    });
                }
            }
            case NEWMOVE -> {
                UUID lobbyUUID = storage.getLobbyUUIDByUserUUID(userUUID);
                List<User> lobbyUsers = storage.getUsersByLobbyUUID(lobbyUUID);
                Date sentDate = msg.getTime();
                log.info("Client {} in lobby {} has made a new move", username, lobbyUUID);

                Move m = new Gson().fromJson(msg.getMessage(), Move.class);

                if (storage.checkMoveInLobby(lobbyUUID, m)) {
                    log.debug("Move from {} was found valid!", username);

                    storage.makeMove(lobbyUUID, m);
                    Message moveMsg = new ServerMessage(username, MessageType.NEWMOVE, sentDate, msg.getMessage());

                    lobbyUsers.parallelStream().filter(user -> {
                        WebSocket webSocket = getWebSocketByUUID(user.getUuid());
                        if (webSocket != null) {
                            return !webSocket.equals(conn);
                        }
                        return false;
                    }).forEach(user -> {
                        WebSocket webSocket = getWebSocketByUUID(user.getUuid());
                        assert webSocket != null;
                        webSocket.send(moveMsg.toJSON());
                    });

                    Color checkMateColor = storage.checkCheckMateInLobby(lobbyUUID);
                    if (checkMateColor != null) {
                        String winnerUsername = lobbyUsers.stream().filter(user -> user.getGameColor() == checkMateColor).findAny().orElseThrow().getUsername();
                        broadcast(new ServerMessage(winnerUsername, MessageType.CHECKMATE, "").toJSON());
                        lobbyUsers.forEach(user -> {
                            Objects.requireNonNull(getWebSocketByUUID(user.getUuid())).close(CloseFrame.NORMAL);
                        });
                    }
                } else {
                    log.info("Move from {} was found invalid. Closing game!", username);
                    broadcastServerError(CloseFrame.UNEXPECTED_CONDITION, lobbyUUID, "Invalid move made by " + username + "! Closing game!");
                }
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {
        log.info("Server successfully started");
    }

    /**
     * Starts the server and sets boolean isOpen accordingly.
     *
     * @see org.java_websocket.server.WebSocketServer
     */
    @Override
    public void start() {
        isOpen = true;
        super.start();
    }

    /**
     * Stops the server after the specified timeout, closes all connections and sets boolean isOpen
     * accordingly.
     *
     * @param timeout timeout for server stop
     * @throws InterruptedException on Interrupt
     * @see org.java_websocket.server.WebSocketServer
     */
    @Override
    public void stop(int timeout) throws InterruptedException {
        isOpen = false;
        for (WebSocket ws : getConnections()) {
            ws.close(CloseFrame.GOING_AWAY, "Server closed.");
        }
        super.stop(timeout);
    }

    /**
     * Stops the server, closes all connections and sets boolean isOpen accordingly.
     *
     * @throws InterruptedException on Interrupt
     * @see org.java_websocket.server.WebSocketServer
     */
    @Override
    public void stop() throws InterruptedException {
        isOpen = false;
        for (WebSocket ws : getConnections()) {
            ws.close(CloseFrame.GOING_AWAY, "Server closed.");
        }
        super.stop();
    }

    /**
     * @return true, if server is open - false, if not
     */
    @Override
    public boolean getServerStatus() {
        return isOpen;
    }

    /**
     * @return String-Array with all current usernames.
     */
    @Override
    public String[] getUsers() {
        List<User> userList = storage.getUsers();
        return userList.stream().map(User::getUsername).toArray(String[]::new);
    }

    /**
     * @param uuid UUID of WebSocket to look for.
     * @return The WebSocket the given UUID is attached to.
     */
    private WebSocket getWebSocketByUUID(UUID uuid) {
        for (WebSocket webSocket : getConnections()) {
            if (webSocket.getAttachment() == uuid) {
                return webSocket;
            }
        }
        return null;
    }

    /**
     * Sends the error message to all clients in the specified lobby and closes the connections.
     *
     * @param closeCode    Close code to close the connection with.
     * @param lobbyUUID    UUID of lobby.
     * @param errorMessage Error message to send to all clients.
     */
    private void broadcastServerError(int closeCode, UUID lobbyUUID, String errorMessage) {
        Message msg = new ServerMessage(MessageType.SERVERERROR, errorMessage);
        List<User> userList = storage.getUsersByLobbyUUID(lobbyUUID);
        userList.forEach(user -> {
            WebSocket webSocket = getWebSocketByUUID(user.getUuid());
            if (webSocket != null) {
                webSocket.send(msg.toJSON());
                webSocket.close(closeCode, errorMessage);
            } else {
                log.error("WebSocket to user with UUID {} not found!", user.getUuid());
            }
        });
    }

    /**
     * Sends all messages needed to begin the match.
     *
     * @param joinedUser Username of user that joined last.
     */
    private void beginMatch(String joinedUser, UUID lobbyUUID) {
        //board.initialize();
        Message msg = new ServerMessage(MessageType.BEGINMATCH, "Player " + joinedUser + " joined. The match begins!");

        List<User> userList = storage.getUsersByLobbyUUID(lobbyUUID);

        userList.forEach(user -> {
            UUID uuid = user.getUuid();
            WebSocket webSocket = getWebSocketByUUID(uuid);
            if (webSocket != null) {
                webSocket.send(msg.toJSON());
                Message colorInfo = new ServerMessage(MessageType.COLORINFO, user.getGameColor().name());
                Message userListMsg = new ServerMessage(MessageType.USERLIST, new Gson().toJson(storage.getUsersByLobbyUUID(lobbyUUID).stream().map(User::getUsername).toArray()));
                webSocket.send(colorInfo.toJSON());
                webSocket.send(userListMsg.toJSON());
            }
        });
        log.info("Match in lobby {} begins.", lobbyUUID);
    }

    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            PublicServer publicServer = (PublicServer) new ServerFactory(true).build();
            publicServer.start();
        });

        serverThread.start();
    }
}
