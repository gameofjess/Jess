package org.example.javachess.server;


import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.Level;
import org.example.javachess.client.ConnectionHandler;
import org.example.javachess.extensions.AssertLoggedExtension;
import org.example.javachess.helper.exceptions.InvalidHostnameException;
import org.example.javachess.helper.exceptions.InvalidPortException;
import org.java_websocket.server.WebSocketServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class ServerCommandListenerTest {

    @RegisterExtension
    AssertLoggedExtension assertLogged = new AssertLoggedExtension();

    /**
     * Tests the stop command
     *
     * @see org.example.javachess.server.ServerCommandListener#parseCommand(String)
     */
    @Test
    void stopTest() throws InvalidHostnameException, URISyntaxException, InvalidPortException {
        Random random = new Random();
        int port = random.nextInt(1000, 65535);

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.start();

        ConnectionHandler testConnection = new ConnectionHandler("127.0.0.1", port);
        testConnection.connect("TestUser");

        String command = "stop";
        InputStream inputStream =
                new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        boolean isDisonnected = false;
        // This waits one seconds if the client gets disconnected, if not, the test fails.
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 1000) {
            if (!testConnection.getConnectionStatus()) {
                isDisonnected = true;
                break;
            }
        }

        assertTrue(isDisonnected);
        assertEquals(0, testServer.getConnections().size());
    }

    /**
     * Tests the list command
     *
     * @see org.example.javachess.server.ServerCommandListener#parseCommand(String)
     */
    @Test
    void listTest() throws InvalidHostnameException, URISyntaxException, InvalidPortException {
        Random random = new Random();
        int port = random.nextInt(1000, 65535);

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.start();

        ConnectionHandler testConnection1 = new ConnectionHandler("127.0.0.1", port);
        testConnection1.connect("TestUser");
        ConnectionHandler testConnection2 = new ConnectionHandler("127.0.0.1", port);
        testConnection2.connect("TestUser2");

        String command = "list\nstop";
        InputStream inputStream =
                new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 1000) {
            if (!testConnection1.getConnectionStatus()) {
                break;
            }
        }

        assertLogged.assertLogged("The following users are connected: TestUser, TestUser2",
                Level.INFO);
    }

    /**
     * Tests the start command.
     *
     * @see org.example.javachess.server.ServerCommandListener#parseCommand(String)
     */
    @Test
    void startTest() throws NoSuchFieldException, IllegalAccessException {
        Random random = new Random();
        int port = random.nextInt(1000, 65535);

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.start();

        String command = "stop\nstart";
        InputStream inputStream =
                new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        Field isClosedField = WebSocketServer.class.getDeclaredField("isclosed");
        isClosedField.setAccessible(true);

        assertFalse(((AtomicBoolean) isClosedField.get(testServer)).get());
    }

    @Test
    void restartTest() throws NoSuchFieldException, IllegalAccessException {
        Random random = new Random();
        int port = random.nextInt(1000, 65535);

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.start();

        String command = "restart";
        InputStream inputStream =
                new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        Field isClosedField = WebSocketServer.class.getDeclaredField("isclosed");
        isClosedField.setAccessible(true);

        assertFalse(((AtomicBoolean) isClosedField.get(testServer)).get());
    }

}
