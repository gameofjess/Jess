package org.example.javachess.helper.messages;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * This class serves as a mean to transfer messages from the server to the clients.
 */
public class ServerMessage implements Message{

    private final static Logger log = LogManager.getLogger(ServerMessage.class);

    private final String username;
    private final String message;
    private final Date time;
    private final MessageType type;

    ServerMessage (String username, MessageType type, Date time, String message){
        this.username = username;
        this.type = type;
        this.time = time;
        this.message = message;
    }

    /**
     * Constructs a ServerMessage from a username, a MessageType and a message.
     * @param type Type of the ServerMessage.
     * @param message Message to be sent.
     */
    ServerMessage (String username, MessageType type, String message){
        this.username = username;
        this.type = type;
        this.time = new Date();
        this.message = message;
    }

    /**
     * Constructs a ServerMessage from JSON.
     */
    ServerMessage (String json){
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
