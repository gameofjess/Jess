package org.example.javachess.helper.messages;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.javachess.chesslogic.Move;

import java.util.Date;

/**
 * This class serves as a mean to transfer messages from clients to the server.
 */
public class ClientMessage implements Message{

    private static final Logger log = LogManager.getLogger(ClientMessage.class);

    private final String message;
    private final Date time;
    private final MessageType type;

    /**
     * Constructs a ClientMessage from a message, a time and a MessageType
     * @param message Message to be sent.
     * @param time Custom time of creation.
     * @param type Type of ClientMessage.
     */
    public ClientMessage(String message, Date time, MessageType type) {
        log.debug("Constructing ClientMessage with message, date and message type.");
        if(type == MessageType.SERVERINFO || type == MessageType.SERVERERROR || type == MessageType.BEGINMATCH){
            throw new IllegalArgumentException("A ClientMessage may not be of type " + type);
        }
        this.message = message;
        this.time = time;
        this.type = type;
    }

    /**
     * Constructs a ClientMessage from a message and a MessageType. The time of creation is set to the actual time of creation.
     * @param message Message to be sent.
     * @param type Type of ClientMessage.
     */
    public ClientMessage(String message, MessageType type) {
        log.debug("Constructing ClientMessage with message and message type.");
        if(type == MessageType.SERVERINFO || type == MessageType.SERVERERROR || type == MessageType.BEGINMATCH){
            throw new IllegalArgumentException("A ClientMessage may not be of type " + type);
        }
        this.message = message;
        this.type = type;
        this.time = new Date();
    }

    /**
     * Constructs a ClientMessage from a Move.
     * @param m Move to be sent.
     */
    public ClientMessage(Move m){
        this.type = MessageType.NEWMOVE;
        this.time = new Date();
        this.message = new Gson().toJson(m);
    }

    /**
     * Constructs a ClientMessage from JSON.
     */
    public ClientMessage(String json){
        log.debug("Constructing ClientMessage from JSON");
        Gson g = new Gson();
        this.message = g.fromJson(json, ClientMessage.class).getMessage();
        this.time = g.fromJson(json, ClientMessage.class).getTime();
        this.type = g.fromJson(json, ClientMessage.class).getType();
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
}
