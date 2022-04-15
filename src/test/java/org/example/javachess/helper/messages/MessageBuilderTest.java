package org.example.javachess.helper.messages;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MessageBuilderTest {

    @Test
    void serverMessageBuildTest(){
        String username = "sampleUsername";
        MessageType type = MessageType.CHATMESSAGE;
        Date time = new Date();
        String message = "This is a test!";

        ServerMessage sm = new MessageBuilder()
                .setDate(time)
                .setType(type)
                .setUsername(username)
                .setMessage(message)
                .buildServerMessage();


        assertEquals(sm.getMessage(), message);
        assertEquals(sm.getTime(), time);
        assertEquals(sm.getType(), type);
        assertEquals(sm.getUsername(), username);

        ServerMessage fromJSON = new MessageBuilder()
                .setJson(sm.toJSON())
                .buildServerMessage();

        assertEquals(fromJSON.getMessage(), message);
        assertEquals(fromJSON.getTime().toString(), time.toString());
        assertEquals(fromJSON.getType(), type);
        assertEquals(fromJSON.getUsername(), username);
    }

    @Test
    void clientMessageBuildTest(){
        MessageType type = MessageType.CHATMESSAGE;
        Date time = new Date();
        String message = "This is a test!";

        ClientMessage cm = new MessageBuilder()
                .setDate(time)
                .setType(type)
                .setMessage(message)
                .buildClientMessage();

        assertEquals(cm.getMessage(), message);
        assertEquals(cm.getType(), type);
        assertEquals(cm.getTime().toString(), time.toString());

        ClientMessage fromJSON = new MessageBuilder()
                .setJson(cm.toJSON())
                .buildClientMessage();

        assertEquals(fromJSON.getMessage(), message);
        assertEquals(fromJSON.getType(), type);
        assertEquals(fromJSON.getTime().toString(), time.toString());
    }
}
