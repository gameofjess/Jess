package com.gameofjess.javachess.server;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.junit.jupiter.api.Test;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.chesslogic.pieces.Piece;
import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.helper.messages.ClientMessage;
import com.gameofjess.javachess.helper.messages.MessageType;
import com.gameofjess.javachess.helper.messages.ServerMessage;

public class ServerTest {

    /**
     * Tests the onOpen method
     * 
     * @see Server#onOpen(WebSocket, ClientHandshake)
     */
    @Test
    void onOpenStandardTest() {
        Server testServer = new ServerBuilder().build();

        WebSocket testWS = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS).getAttachment();

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");
        when(testHandshake.hasFieldValue("color")).thenReturn(true);
        when(testHandshake.getFieldValue("color")).thenReturn(Color.RANDOM.name());

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

        WebSocket testWS = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS).getAttachment();

        WebSocket testWS2 = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS2).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS2).getAttachment();

        WebSocket testWS3 = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS3).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS3).getAttachment();

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");
        when(testHandshake.hasFieldValue("color")).thenReturn(true);
        when(testHandshake.getFieldValue("color")).thenReturn(Color.RANDOM.name());

        ClientHandshake testHandshake2 = mock(ClientHandshake.class);
        when(testHandshake2.hasFieldValue("username")).thenReturn(true);
        when(testHandshake2.getFieldValue("username")).thenReturn("TestUser2");
        when(testHandshake2.hasFieldValue("color")).thenReturn(true);
        when(testHandshake2.getFieldValue("color")).thenReturn(Color.RANDOM.name());

        ClientHandshake testHandshake3 = mock(ClientHandshake.class);
        when(testHandshake3.hasFieldValue("username")).thenReturn(true);
        when(testHandshake3.getFieldValue("username")).thenReturn("TestUser3");
        when(testHandshake3.hasFieldValue("color")).thenReturn(true);
        when(testHandshake3.getFieldValue("color")).thenReturn(Color.RANDOM.name());

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

        WebSocket testWS = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS).setAttachment(any());
        doCallRealMethod().when(testWS).getAttachment();
        WebSocket testWS2 = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS2).setAttachment(any());
        doCallRealMethod().when(testWS2).getAttachment();

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");
        when(testHandshake.hasFieldValue("color")).thenReturn(true);
        when(testHandshake.getFieldValue("color")).thenReturn(Color.RANDOM.name());

        ClientHandshake testHandshake2 = mock(ClientHandshake.class);
        when(testHandshake2.hasFieldValue("username")).thenReturn(true);
        when(testHandshake2.getFieldValue("username")).thenReturn("TestUser");
        when(testHandshake2.hasFieldValue("color")).thenReturn(true);
        when(testHandshake2.getFieldValue("color")).thenReturn(Color.RANDOM.name());

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
     * Tests the onOpen method with a client-handshake without a username field
     *
     * @see Server#onOpen(WebSocket, ClientHandshake)
     */
    @Test
    void onOpenNoColorFieldTest() {
        Server testServer = new ServerBuilder().build();

        WebSocket testWS = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS).setAttachment(any());
        doCallRealMethod().when(testWS).getAttachment();

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");
        when(testHandshake.hasFieldValue("color")).thenReturn(false);

        testServer.onOpen(testWS, testHandshake);

        verify(testWS).close(CloseFrame.REFUSE, "Invalid color header!");
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
        when(testHandshake.hasFieldValue("color")).thenReturn(true);
        when(testHandshake.getFieldValue("color")).thenReturn(Color.RANDOM.name());

        ClientHandshake testHandshake2 = mock(ClientHandshake.class);
        when(testHandshake2.hasFieldValue("username")).thenReturn(true);
        when(testHandshake2.getFieldValue("username")).thenReturn("TestUser2");
        when(testHandshake2.hasFieldValue("color")).thenReturn(true);
        when(testHandshake2.getFieldValue("color")).thenReturn(Color.RANDOM.name());

        ArrayList<WebSocket> socketList = new ArrayList<>();
        socketList.add(testWS2);
        when(testServer.getConnections()).thenReturn(socketList);

        testServer.onOpen(testWS, testHandshake);
        testServer.onOpen(testWS2, testHandshake2);

        testServer.onClose(testWS, 0, "Testing...", false);

        verify(testWS2).close(CloseFrame.GOING_AWAY, "Opponent TestUser disconnected!");
    }

    @Test
    void handleClientMessageChatMessageTest() {
        Server testServer = spy(new ServerBuilder().build());

        WebSocket testWS = mock(WebSocketImpl.class);
        when(testWS.getAttachment()).thenReturn(UUID.randomUUID());
        when(testWS.getRemoteSocketAddress()).thenReturn(new InetSocketAddress(1111));

        ClientMessage cmsg = new ClientMessage("TestMessage!", MessageType.CHATMESSAGE);

        testServer.onMessage(testWS, cmsg.toJSON());

        ServerMessage smsg = new ServerMessage(null, MessageType.CHATMESSAGE, cmsg.getMessage());

        verify(testServer).broadcast(smsg.toJSON());
    }

    @Test
    void handleClientMessageNewMoveTest() {
        Server testServer = spy(new ServerBuilder().build());

        WebSocket testWS = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS).getAttachment();
        when(testWS.getRemoteSocketAddress()).thenReturn(new InetSocketAddress(1111));

        WebSocket testWS2 = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS2).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS2).getAttachment();
        when(testWS.getRemoteSocketAddress()).thenReturn(new InetSocketAddress(1111));

        when(testServer.getConnections()).thenReturn(List.of(testWS, testWS2));

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");
        when(testHandshake.hasFieldValue("color")).thenReturn(true);
        when(testHandshake.getFieldValue("color")).thenReturn(Color.RANDOM.name());

        ClientHandshake testHandshake2 = mock(ClientHandshake.class);
        when(testHandshake2.hasFieldValue("username")).thenReturn(true);
        when(testHandshake2.getFieldValue("username")).thenReturn("TestUser2");
        when(testHandshake2.hasFieldValue("color")).thenReturn(true);
        when(testHandshake2.getFieldValue("color")).thenReturn(Color.RANDOM.name());

        testServer.onOpen(testWS, testHandshake);
        testServer.onOpen(testWS2, testHandshake2);

        Board board = new Board();
        board.initialize();

        Piece piece = board.getBoardMap().get(new Position(1, 1));

        ClientMessage cmsg = new ClientMessage(piece.getMoves()[0]);

        testServer.onMessage(testWS, cmsg.toJSON());

        ServerMessage smsg = new ServerMessage("TestUser", MessageType.NEWMOVE, cmsg.getMessage());

        verify(testWS2).send(smsg.toJSON());
    }

    @Test
    void handleClientMessageNewMoveErrorTest() {
        Server testServer = spy(new ServerBuilder().build());

        WebSocket testWS = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS).getAttachment();
        when(testWS.getRemoteSocketAddress()).thenReturn(new InetSocketAddress(1111));

        WebSocket testWS2 = mock(WebSocketImpl.class);
        doCallRealMethod().when(testWS2).setAttachment(any(UUID.class));
        doCallRealMethod().when(testWS2).getAttachment();
        when(testWS.getRemoteSocketAddress()).thenReturn(new InetSocketAddress(1111));

        when(testServer.getConnections()).thenReturn(List.of(testWS, testWS2));

        ClientHandshake testHandshake = mock(ClientHandshake.class);
        when(testHandshake.hasFieldValue("username")).thenReturn(true);
        when(testHandshake.getFieldValue("username")).thenReturn("TestUser");
        when(testHandshake.hasFieldValue("color")).thenReturn(true);
        when(testHandshake.getFieldValue("color")).thenReturn(Color.RANDOM.name());

        ClientHandshake testHandshake2 = mock(ClientHandshake.class);
        when(testHandshake2.hasFieldValue("username")).thenReturn(true);
        when(testHandshake2.getFieldValue("username")).thenReturn("TestUser2");
        when(testHandshake2.hasFieldValue("color")).thenReturn(true);
        when(testHandshake2.getFieldValue("color")).thenReturn(Color.RANDOM.name());

        testServer.onOpen(testWS, testHandshake);
        testServer.onOpen(testWS2, testHandshake2);

        Board board = new Board();
        board.initialize();

        Move move = new Move(new Position(1, 1), new Position(4, 5));

        ClientMessage cmsg = new ClientMessage(move);

        testServer.onMessage(testWS, cmsg.toJSON());

        ServerMessage smsg = new ServerMessage("TestUser", MessageType.NEWMOVE, cmsg.getTime(), cmsg.getMessage());

        verify(testWS2, never()).send(smsg.toJSON());

        ServerMessage errorMessage = new ServerMessage(MessageType.SERVERERROR, cmsg.getTime(), "Invalid move made by TestUser! Closing game!");

        verify(testServer).broadcast(errorMessage.toJSON());
    }


}
