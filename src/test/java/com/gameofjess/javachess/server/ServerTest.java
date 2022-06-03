package com.gameofjess.javachess.server;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.junit.jupiter.api.Test;

public class ServerTest {

    /**
     * Tests the onOpen method
     * 
     * @see Server#onOpen(WebSocket, ClientHandshake)
     */
    @Test
    void onOpenStandardTest() {
        Server testServer = new ServerBuilder().build();

        WebSocket testWS = mock(WebSocket.class);

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");

        testServer.onOpen(testWS, testHandshake);

        assertTrue(Arrays.stream(testServer.getUsers()).toList().contains("TestUser"));
    }

    /**
     * Tests the onOpen method for handling of more than two users.
     * 
     * @see Server#onOpen(WebSocket, ClientHandshake)
     */
    @Test
    void onOpenTooManyUsersTest() {
        Server testServer = new ServerBuilder().build();

        WebSocket testWS = mock(WebSocket.class);
        WebSocket testWS2 = mock(WebSocket.class);
        WebSocket testWS3 = mock(WebSocket.class);

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");

        ClientHandshake testHandshake2 = mock(ClientHandshake.class);
        when(testHandshake2.hasFieldValue("username")).thenReturn(true);
        when(testHandshake2.getFieldValue("username")).thenReturn("TestUser2");

        ClientHandshake testHandshake3 = mock(ClientHandshake.class);
        when(testHandshake3.hasFieldValue("username")).thenReturn(true);
        when(testHandshake3.getFieldValue("username")).thenReturn("TestUser3");

        testServer.onOpen(testWS, testHandshake);

        assertTrue(Arrays.stream(testServer.getUsers()).toList().contains("TestUser"));

        verify(testWS, never()).close(CloseFrame.UNEXPECTED_CONDITION, "There already are two players!");

        testServer.onOpen(testWS2, testHandshake2);

        assertTrue(Arrays.stream(testServer.getUsers()).toList().contains("TestUser2"));

        verify(testWS2, never()).close(CloseFrame.UNEXPECTED_CONDITION, "There already are two players!");

        testServer.onOpen(testWS3, testHandshake3);

        verify(testWS3).close(CloseFrame.UNEXPECTED_CONDITION, "There already are two players!");
    }

    /**
     * Tests the onOpen method for handling duplicate usernames
     * 
     * @see Server#onOpen(WebSocket, ClientHandshake)
     */
    @Test
    void onOpenDuplicateUsernameTest() {
        Server testServer = new ServerBuilder().build();

        WebSocket testWS = mock(WebSocket.class);
        WebSocket testWS2 = mock(WebSocket.class);

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");

        ClientHandshake testHandshake2 = mock(ClientHandshake.class);
        when(testHandshake2.hasFieldValue("username")).thenReturn(true);
        when(testHandshake2.getFieldValue("username")).thenReturn("TestUser");

        testServer.onOpen(testWS, testHandshake);
        assertTrue(Arrays.stream(testServer.getUsers()).toList().contains("TestUser"));
        verify(testWS, never()).close(CloseFrame.REFUSE, "Username is already used!");

        testServer.onOpen(testWS2, testHandshake2);
        verify(testWS2).close(CloseFrame.REFUSE, "Username is already used!");
    }

    /**
     * Tests the onOpen method with a client-handshake without a username field
     * 
     * @see Server#onOpen(WebSocket, ClientHandshake)
     */
    @Test
    void onOpenNoUsernameFieldTest() {
        Server testServer = new ServerBuilder().build();

        WebSocket testWS = mock(WebSocket.class);

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(false);

        testServer.onOpen(testWS, testHandshake);

        verify(testWS).close(CloseFrame.REFUSE, "Invalid information!");
    }

    /**
     * Tests the onClose method
     * 
     * @see Server#onOpen(WebSocket, ClientHandshake)
     */
    @Test
    void onCloseStandardTest() {
        Server testServer = spy(new ServerBuilder().build());

        WebSocket testWS = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS).getAttachment();

        WebSocket testWS2 = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS2).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS2).getAttachment();

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");

        ClientHandshake testHandshake2 = mock(ClientHandshake.class);
        when(testHandshake2.hasFieldValue("username")).thenReturn(true);
        when(testHandshake2.getFieldValue("username")).thenReturn("TestUser2");

        ArrayList<WebSocket> socketList = new ArrayList<>();
        socketList.add(testWS2);
        when(testServer.getConnections()).thenReturn(socketList);

        testServer.onOpen(testWS, testHandshake);
        testServer.onOpen(testWS2, testHandshake2);

        testServer.onClose(testWS, 0, "Testing...", false);

        verify(testWS2).close(CloseFrame.GOING_AWAY);
    }


}
