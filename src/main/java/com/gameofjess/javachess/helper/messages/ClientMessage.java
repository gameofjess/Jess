package com.gameofjess.javachess.helper.messages;

import java.util.Date;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Move;
import com.google.gson.Gson;

/**
 * This class serves as a mean to transfer messages from clients to the server.
 */
public class ClientMessage implements Message {

    private static final Logger log = LogManager.getLogger(ClientMessage.class);

    private final String message;
    private Date time;
    private final MessageType type;

    /**
     * Constructs a ClientMessage from a message, a time and a MessageType
     * 
     * @param message Message to be sent.
     * @param time Custom time of creation.
     * @param type Type of ClientMessage.
     */
    public ClientMessage(String message, Date time, MessageType type) {
        this(message, type);
        log.debug("Adding date to constructed ClientMessage");
        this.time = time;
    }

    /**
     * Constructs a ClientMessage from a message and a MessageType. The time of creation is set to the
     * actual time of creation.
     * 
     * @param message Message to be sent.
     * @param type Type of ClientMessage.
     */
    public ClientMessage(String message, MessageType type) {
        log.debug("Constructing ClientMessage with message and message type.");
        if (type == MessageType.SERVERINFO || type == MessageType.SERVERERROR || type == MessageType.BEGINMATCH || type == MessageType.USERLIST || type == MessageType.COLORINFO) {
            throw new IllegalArgumentException("A ClientMessage may not be of type " + type);
        }
        this.message = message;
        this.type = type;
        this.time = new Date();
    }

    /**
     * Constructs a ClientMessage from a Move.
     * 
     * @param m Move to be sent.
     */
    public ClientMessage(Move m) {
        this(new Gson().toJson(m), MessageType.NEWMOVE);
        log.debug("Constructing ClientMessage from Move Object");
    }

    /**
     * Constructs a ClientMessage from JSON.
     */
    public ClientMessage(String json) {
        this(new Gson().fromJson(json, ClientMessage.class).getMessage(), new Gson().fromJson(json, ClientMessage.class).getTime(),
                new Gson().fromJson(json, ClientMessage.class).getType());
        log.debug("Constructing ClientMessage from JSON");
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Date getTime() {
        return time;
    }

    @Override
    public String toJSON() {
        return new Gson().toJson(this);
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClientMessage that = (ClientMessage) o;
        return Objects.equals(message, that.message) && Objects.equals(time.toString(), that.time.toString()) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, time, type);
    }
}
