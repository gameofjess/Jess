package com.gameofjess.javachess.server;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.gameofjess.javachess.helper.messages.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.helper.messages.ClientMessage;
import com.gameofjess.javachess.helper.messages.MessageType;
import com.gameofjess.javachess.helper.messages.ServerMessage;
import com.google.gson.Gson;

public class Server extends WebSocketServer {

    private static final Logger log = LogManager.getLogger(Server.class);
    /**
     * saves the usernames mapped to the specific UUID of the WebSocket-instance.
     */
    private final Map<UUID, String> users = new ConcurrentHashMap<>();

    /**
     * saves the colors mapped to the specific UUID of the WebSocket-instance.
     */
    private final Map<UUID, Color> gameColors = new ConcurrentHashMap<>();

    /**
     * saves whether the server is opened or closed.
     */
    private boolean isOpen = false;

    /**
     * saves the board used to check Moves
     * 
     * @see #handleClientMessage(Message, WebSocket)
     */
    private final Board board;

    /**
     * Instantiates a Server and initializes the Board-instance used to check Moves.
     * 
     * @param address Address to listen on.
     */
    Server(InetSocketAddress address) {
        super(address);
        board = new Board();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        log.info("New connection from {}.", webSocket.getRemoteSocketAddress());

        UUID u = UUID.randomUUID();
        log.debug("Attaching unique ID {}.", u);
        webSocket.setAttachment(u);

        if (users.size() > 1) {
            log.warn("Refusing connection because there already are two players!");
            webSocket.close(CloseFrame.UNEXPECTED_CONDITION, "There already are two players!");
            return;
        }

        log.trace("Connection header is being parsed");

        if (!parseUsername(webSocket, clientHandshake) || !parseColor(webSocket, clientHandshake)) {
            return;
        }

        log.info("User with username {} got assigned color {}!", users.get(u), gameColors.get(u));

        if (users.size() == 2) {
            beginMatch(users.get(u));
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int exitCode, String reason, boolean remote) {
        UUID u = webSocket.getAttachment();
        String username = users.get(u);

        if (username == null) {
            log.info("Connection to client from {} disconnected!", webSocket.getRemoteSocketAddress());
            return;
        }

        log.info(" Connection to client {} from {} disconnected!", username, webSocket.getRemoteSocketAddress());
        Message msg = new ServerMessage(MessageType.SERVERINFO, "Client " + username + " disconnected!");
        broadcast(msg.toJSON());
        log.info("Connection from {} was terminated with exit code {}. Reason: {}", webSocket.getRemoteSocketAddress(), exitCode, reason);
        for (WebSocket ws : getConnections()) {
            ws.close(CloseFrame.GOING_AWAY, "Opponent " + username + " disconnected!");
        }
        users.clear();
        gameColors.clear();
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        UUID u = webSocket.getAttachment();
        log.debug("Received message from {} with username {}: {}", webSocket.getRemoteSocketAddress().getAddress(), users.get(u), message);
        Message cmsg = new ClientMessage(message);
        handleClientMessage(cmsg, webSocket);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        String message = "An error ({}) occurred in the server instance: " + e.getMessage();
        log.error(message, e.getClass().getSimpleName());
        broadcastServerError(CloseFrame.UNEXPECTED_CONDITION, message);

        final StringBuilder stackTraceBuilder = new StringBuilder();
        Arrays.stream(e.getStackTrace()).forEach(line -> stackTraceBuilder.append("\n").append(line));
        log.debug("Stacktrace of error: {}", stackTraceBuilder.toString());
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
    public boolean getServerStatus() {
        return isOpen;
    }

    /**
     * @return String-Array with all current usernames.
     */
    public String[] getUsers() {
        return users.values().toArray(new String[0]);
    }

    /**
     * <p>
     * Handles received messages according to their type.
     * </p>
     *
     * <p>
     * CHATMESSAGE: Is taken apart into username and message and broadcast as a ServerMessage.
     * </p>
     * <p>
     * NEWMOVE: Is taken apart into username and the transmitted Move-instance. Then, the method checks
     * if the Move is valid or not. Afterwards it is sent to the other connected WebSocket.
     * </p>
     * 
     * @param cmsg ClientMessage to be handled.
     * @param webSocket WebSocket that sent the Message.
     */
    private void handleClientMessage(Message cmsg, WebSocket webSocket) {
        UUID webSocketUUID = webSocket.getAttachment();
        String username = users.get(webSocketUUID);
        Date sentDate = cmsg.getTime();

        switch (cmsg.getType()) {
            case CHATMESSAGE -> {
                log.info("New chat message received: {}", cmsg.getMessage());

                Message msg =
                        new ServerMessage(username, MessageType.CHATMESSAGE, sentDate, cmsg.getMessage());
                broadcast(msg.toJSON());
            }
            case NEWMOVE -> {
                log.info("Client {} has made a new move", username);

                Move m = new Gson().fromJson(cmsg.getMessage(), Move.class);


                if (board.isMoveValid(m)) {
                    log.debug("Move from {} was found valid!", username);
                    board.getBoardMap().get(m.getOrigin()).makeMove(m);
                    Message msg = new ServerMessage(username, MessageType.NEWMOVE, sentDate, cmsg.getMessage());

                    getConnections().parallelStream().filter(ws -> !(ws.equals(webSocket))).forEach(ws -> ws.send(msg.toJSON()));

                    if (board.getKingBlack().checkCheckMate()) {
                        gameColors.entrySet().stream().filter(entry -> entry.getValue() == Color.WHITE).findFirst().ifPresent(winnerEntry -> {
                            String winnerUsername = users.get(winnerEntry.getKey());
                            broadcast(new ServerMessage(winnerUsername, MessageType.CHECKMATE, "").toJSON());
                        });
                        getConnections().forEach(ws -> ws.close(CloseFrame.NORMAL));
                    } else if (board.getKingWhite().checkCheckMate()) {
                        gameColors.entrySet().stream().filter(entry -> entry.getValue() == Color.BLACK).findFirst().ifPresent(winnerEntry -> {
                            String winnerUsername = users.get(winnerEntry.getKey());
                            broadcast(new ServerMessage(winnerUsername, MessageType.CHECKMATE, "").toJSON());
                        });
                        getConnections().forEach(ws -> ws.close(CloseFrame.NORMAL));
                    }
                } else {
                    log.info("Move from {} was found invalid. Closing game!", username);
                    broadcastServerError(CloseFrame.UNEXPECTED_CONDITION, "Invalid move made by " + username + "! Closing game!");
                }
            }
        }
    }

    /**
     * Sends the error message to all clients and closes the connections.
     * 
     * @param closeCode Close code to close the connection with.
     * @param errorMessage Error message to send to all clients.
     */
    private void broadcastServerError(int closeCode, String errorMessage) {
        Message msg = new ServerMessage(MessageType.SERVERERROR, errorMessage);
        broadcast(msg.toJSON());
        for (WebSocket ws : this.getConnections()) {
            ws.close(closeCode, errorMessage);
        }
    }

    /**
     * @return random value of Color-enum.
     */
    private Color getRandomColor() {
        if (new Random().nextBoolean()) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    /**
     * Parses the username header.
     * 
     * @return Whether it was successful or not.
     */
    private boolean parseUsername(WebSocket webSocket, ClientHandshake clientHandshake) {
        UUID u = webSocket.getAttachment();
        if (clientHandshake.hasFieldValue("username")) {
            String username = clientHandshake.getFieldValue("username");
            if (users.containsValue(username)) {
                log.info("Username {} already exists! Closing connection.", username);
                webSocket.send(new ServerMessage(MessageType.SERVERERROR, "Username already in use!").toJSON());
                webSocket.close(CloseFrame.REFUSE, "Username is already used!");
            } else {
                log.info("Username of new user is {} ", username);
                users.put(u, username);
            }
            return true;
        } else {
            log.warn("Client with invalid username field tried to connect to the server!");
            webSocket.close(CloseFrame.REFUSE, "Invalid information!");
            return false;
        }
    }

    /**
     * Parses the color header.
     * 
     * @return Whether it was successful or not.
     */
    private boolean parseColor(WebSocket webSocket, ClientHandshake clientHandshake) {
        UUID u = webSocket.getAttachment();
        if (clientHandshake.hasFieldValue("color")) {
            Color color = Color.valueOf(clientHandshake.getFieldValue("color"));
            if (users.size() == 1) {
                log.debug("User with username {} from {} is connecting with color choice {}", users.get(u), webSocket.getRemoteSocketAddress(), color.name());
                switch (color) {
                    case BLACK -> gameColors.put(u, Color.BLACK);
                    case WHITE -> gameColors.put(u, Color.WHITE);
                    case RANDOM -> gameColors.put(u, getRandomColor());
                }
            } else {
                log.debug("Ignoring custom color choice {} of user {} from {}.", color.name(), users.get(u), webSocket.getRemoteSocketAddress());

                UUID otherUUID = ((ConcurrentHashMap<UUID, Color>) gameColors).keys().nextElement();
                if (gameColors.get(otherUUID) == Color.WHITE) {
                    gameColors.put(u, Color.BLACK);
                } else {
                    gameColors.put(u, Color.WHITE);
                }
            }
            return true;
        } else {
            log.info("Invalid color header!");
            webSocket.send(new ServerMessage(MessageType.SERVERERROR, "Invalid color header!").toJSON());
            webSocket.close(CloseFrame.REFUSE, "Invalid color header!");
            users.remove(u);
            return false;
        }
    }

    /**
     * Sends all messages needed to begin the match.
     * 
     * @param joinedUser Username of user that joined last.
     */
    private void beginMatch(String joinedUser) {
        //board.initialize();
        Message msg = new ServerMessage(MessageType.BEGINMATCH, "Player " + joinedUser + " joined. The match begins!");
        users.forEach((uuid, username) -> {
            WebSocket webSocket = getWebSocketByUUID(uuid);
            if (webSocket != null) {
                Message colorInfo = new ServerMessage(MessageType.COLORINFO, gameColors.get(uuid).name());
                Message userList = new ServerMessage(MessageType.USERLIST, new Gson().toJson(users.values().toArray()));
                webSocket.send(colorInfo.toJSON());
                webSocket.send(userList.toJSON());
            }
        });
        broadcast(msg.toJSON());
        log.info("Match begins.");
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
}
