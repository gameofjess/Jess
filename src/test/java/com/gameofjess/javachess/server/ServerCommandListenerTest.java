package com.gameofjess.javachess.server;


import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.extensions.AssertLoggedExtension;
import com.gameofjess.javachess.gui.controller.GameController;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ServerCommandListenerTest {

    @RegisterExtension
    AssertLoggedExtension assertLogged = new AssertLoggedExtension();


    static PrivateServer testServer;
    static int port = 8887;

    @BeforeAll
    static void beforeAll() {
        testServer = (PrivateServer) new ServerFactory(false).build();
    }

    @BeforeEach
    void beforeEach() throws InterruptedException {
        if (testServer.getServerStatus())
            testServer.stop();
        port++;
        testServer = (PrivateServer) new ServerFactory(false).setPort(port).build();
        testServer.start();
    }

    @AfterAll
    static void afterAll() throws InterruptedException {
        testServer.stop();
    }

    /**
     * Tests the stop command
     *
     * @see ServerCommandListener#parseCommand(String)
     */
    @Test
    void stopTest() throws InvalidHostnameException, URISyntaxException, InvalidPortException {
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
     * @see ServerCommandListener#parseCommand(String)
     */
    @Test
    void listTest() throws InvalidHostnameException, URISyntaxException, InvalidPortException {
        GameController mock = mock(GameController.class); // Not necessary, but prevents NullPointerExceptions

        ConnectionHandler testConnection1 = new ConnectionHandler("127.0.0.1", port);
        testConnection1.setGameController(mock);
        testConnection1.connect("TestUser");
        ConnectionHandler testConnection2 = new ConnectionHandler("127.0.0.1", port);
        testConnection2.setGameController(mock);
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
     * @see ServerCommandListener#parseCommand(String)
     */
    @Test
    void startTest() {
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
     * @see ServerCommandListener#parseCommand(String)
     */
    @Test
    void restartTest() {
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
     * @see ServerCommandListener#parseCommand(String)
     */
    @Test
    void exitTest() {
        String command = "exit";
        InputStream inputStream = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        // await().atMost(1, TimeUnit.SECONDS).until(commandListenerThread::isInterrupted, equalTo(true));
        await().atMost(1, TimeUnit.SECONDS).until(testServer::getServerStatus, equalTo(false));
    }

    /**
     * Tests the message logged whenever an unknown command is called.
     *
     * @see ServerCommandListener#parseCommand(String)
     */
    @Test
    void unknownCommandTest() {
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
     * @see ServerCommandListener#parseCommand(String)
     */
    @Test
    void alreadyStartedTest() {
        String command = "start";
        InputStream inputStream = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

        ServerCommandListener cl = new ServerCommandListener(testServer, inputStream);
        Thread commandListenerThread = new Thread(cl);
        commandListenerThread.start();

        assertLogged.assertLogged("Server is already started!", Level.ERROR);
    }

}
