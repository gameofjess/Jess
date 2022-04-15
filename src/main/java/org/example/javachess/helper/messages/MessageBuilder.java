package org.example.javachess.helper.messages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * This class is used to have a general method of constructing ClientMessages and ServerMessages.
 */
public class MessageBuilder {

    private static final Logger log = LogManager.getLogger(MessageBuilder.class);

    private String username;
    private String message;
    private String json;
    private Date time;
    private MessageType type;

    public MessageBuilder setJson(String json){
        this.json = json;
        return this;
    }

    public MessageBuilder setType(MessageType type){
        this.type = type;
        return this;
    }

    public MessageBuilder setDate(Date time){
        this.time = time;
        return this;
    }

    public MessageBuilder setUsername(String username){
        this.username = username;
        return this;
    }

    public MessageBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Builds a ServerMessage
     * @return A ServerMessage as long as a message, a type and a username are given or a JSON is given. Otherwise, the method will throw an exception.
     */
    public ServerMessage buildServerMessage(){
        if(message != null && type != null && username != null){
            if(time != null){
                return new ServerMessage(username, type, time, message);
            }
            return new ServerMessage(username, type, message);
        } else if (json != null){
            return new ServerMessage(json);
        } else {
            throw new IllegalArgumentException("Not enough parameters have been set!");
        }
    }

    /**
     * Builds a ClientMessage.
     * @return A ClientMessage as long as a message and a type are given or a JSON is given. Otherwise, the method will throw an exception.
     */
    public ClientMessage buildClientMessage(){
        if(message != null && type != null){
            if(time != null){
                return new ClientMessage(message, time, type);
            }
            return new ClientMessage(message, type);
        } else if (json != null){
            return new ClientMessage(json);
        } else {
            throw new IllegalArgumentException("Not enough parameters have been set!");
        }
    }
}
