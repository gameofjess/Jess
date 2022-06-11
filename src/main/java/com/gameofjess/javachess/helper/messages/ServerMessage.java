package com.gameofjess.javachess.helper.messages;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

/**
 * This class serves as a mean to transfer messages from the server to the clients.
 */
public class ServerMessage implements Message {

    private final static Logger log = LogManager.getLogger(ServerMessage.class);

    private final String username;
    private final String message;
    private final Date time;
    private final MessageType type;

    /**
     * Constructs a ServerMessage from a username, a MessageType, a Date and a message.
     *
     * @param type Type of the ServerMessage.
     * @param message Message to be sent.
     * @param time Custom date of creation.
     */
    public ServerMessage(String username, MessageType type, Date time, String message) {
        log.trace("Constructing server message with username {}, type {}, time {} and message \"{}\".", username, type.name(), time.toString(), message);
        this.username = username;
        this.type = type;
        this.time = time;
        this.message = message;
    }

    /**
     * Constructs a ServerMessage from a username, a MessageType and a message.
     * 
     * @param type Type of the ServerMessage.
     * @param message Message to be sent.
     */
    public ServerMessage(String username, MessageType type, String message) {
        log.trace(" Constructing server message with username {}, type {} and message \"{}\".", username, type.name(), message);
        this.username = username;
        this.type = type;
        this.time = new Date();
        this.message = message;
    }

    /**
     * Constructs a ServerMessage from a MessageType and a message.
     * 
     * @param type Type of the ServerMessage.
     * @param message Message to be sent.
     */
    public ServerMessage(MessageType type, String message) {
        log.trace(" Constructing server message with type {} and message \"{}\".", type.name(), message);
        this.username = null;
        this.type = type;
        this.message = message;
        this.time = new Date();
    }

    /**
     * Constructs a ServerMessage from a MessageType and a message. Time of creation can be manually
     * set.
     * 
     * @param type Type of the ServerMessage.
     * @param message Message to be sent.
     */
    public ServerMessage(MessageType type, Date time, String message) {
        log.trace("Constructing server message with type {}, time {} and message \"{}\".", type.name(), time.toString(), message);
        this.username = null;
        this.type = type;
        this.message = message;
        this.time = time;
    }

    /**
     * Constructs a ServerMessage from JSON.
     */
    public ServerMessage(String json) {
        log.trace("Constructing server message with json \"{}\" is ", json);
        Gson g = new Gson();
        message = g.fromJson(json, ServerMessage.class).getMessage();
        time = g.fromJson(json, ServerMessage.class).getTime();
        username = g.fromJson(json, ServerMessage.class).getUsername();
        type = g.fromJson(json, ServerMessage.class).getType();
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
        Gson g = new Gson();
        return g.toJson(this);
    }

    @Override
    public MessageType getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }
}
