package org.example.javachess.server;


import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.example.javachess.client.ConnectionHandler;
import org.example.javachess.extensions.AssertLoggedExtension;
import org.example.javachess.helper.exceptions.InvalidHostnameException;
import org.example.javachess.helper.exceptions.InvalidPortException;
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
        int port = 2001;

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.setReuseAddr(true);
        testServer.start();

        ConnectionHandler testConnection = new ConnectionHandler("127.0.0.1", port);
        testConnection.connect("TestUser");

        String command = "stop";
        InputStream inputStream =
                new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        await().atMost(5, TimeUnit.SECONDS).until(testConnection::getConnectionStatus, equalTo(false));

        assertEquals(0, testServer.getConnections().size());
    }

    /**
     * Tests the list command
     *
     * @see org.example.javachess.server.ServerCommandListener#parseCommand(String)
     */
    @Test
    void listTest() throws InvalidHostnameException, URISyntaxException, InvalidPortException {
        int port = 2002;

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.setReuseAddr(true);
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

        await().atMost(5, TimeUnit.SECONDS).until(testConnection1::getConnectionStatus, equalTo(false));

        assertLogged.assertLogged("The following users are connected: TestUser, TestUser2", Level.INFO);
    }

    /**
     * Tests the start command.
     *
     * @see org.example.javachess.server.ServerCommandListener#parseCommand(String)
     */
    @Test
    void startTest() {
        int port = 2003;

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.setReuseAddr(true);
        testServer.start();

        String command = "stop\nstart";
        InputStream inputStream =
                new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        assertTrue(testServer.getServerStatus());
    }

    /**
     * Tests the restart command.
     *
     * @see org.example.javachess.server.ServerCommandListener#parseCommand(String)
     */
    @Test
    void restartTest() {
        int port = 2004;

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.setReuseAddr(true);
        testServer.start();

        String command = "restart";
        InputStream inputStream =
                new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        assertTrue(testServer.getServerStatus());
    }

    /**
     * Tests the exit command.
     *
     * @see org.example.javachess.server.ServerCommandListener#parseCommand(String)
     */
    @Test
    void exitTest() {
        Server testServer = new ServerBuilder().build();

        String command = "exit";
        InputStream inputStream = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        await().atMost(1, TimeUnit.SECONDS).until(commandListenerThread::isInterrupted, equalTo(true));
    }

    /**
     * Tests the message logged whenever an unknown command is called.
     *
     * @see org.example.javachess.server.ServerCommandListener#parseCommand(String)
     */
    @Test
    void unknownCommandTest() {
        int port = 5001;

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.setReuseAddr(true);
        testServer.start();

        String command = "unknownCommand";
        InputStream inputStream = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        assertLogged.assertLogged("Unknown command: unknownCommand", Level.INFO);
    }

    /**
     * Tests the start command when a server is already started.
     *
     * @see org.example.javachess.server.ServerCommandListener#parseCommand(String)
     */
    @Test
    void alreadyStartedTest() {
        int port = 5002;

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.setReuseAddr(true);
        testServer.start();

        String command = "start";
        InputStream inputStream = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        assertLogged.assertLogged("Server is already started!", Level.ERROR);
    }

}
