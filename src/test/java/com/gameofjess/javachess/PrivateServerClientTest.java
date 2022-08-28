package com.gameofjess.javachess;

import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.gui.controller.GameController;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import com.gameofjess.javachess.server.PrivateServer;
import com.gameofjess.javachess.server.ServerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class PrivateServerClientTest {

    Logger log = LogManager.getLogger(PrivateServerClientTest.class);

    static PrivateServer testServer;
    static int port = 9887;

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
     * Tests whether a connection between a server and a client can be established.
     */
    @Test
    void clientServerConnectTest()
            throws InvalidHostnameException, URISyntaxException, InvalidPortException, InterruptedException {

        log.info("Testing connection of client to server!");

        ConnectionHandler testConnection = new ConnectionHandler("127.0.0.1", port);
        testConnection.setGameController(mock(GameController.class)); // Not necessary, but prevents null pointer exceptions.

        assertTrue(testConnection.connect("TestUser"));

        await().atMost(5, TimeUnit.SECONDS).until(() -> Arrays.stream(testServer.getUsers()).toList().contains("TestUser"), equalTo(true));
    }

    /**
     * Tests whether a connection with a duplicate username gets rejected.
     */
    @Test
    void invalidUsernameTest()
            throws InvalidHostnameException, URISyntaxException, InvalidPortException {

        log.info("Testing client connecting with invalid username.");

        ConnectionHandler testConnection1 = new ConnectionHandler("127.0.0.1", port);
        testConnection1.setGameController(mock(GameController.class)); // Not necessary, but prevents null pointer exceptions.
        ConnectionHandler testConnection2 = new ConnectionHandler("127.0.0.1", port);
        testConnection2.setGameController(mock(GameController.class)); // Not necessary, but prevents null pointer exceptions.

        testConnection1.connect("InvalidTestUser");
        testConnection2.connect("InvalidTestUser");

        await().atMost(5, TimeUnit.SECONDS).until(testConnection2::getConnectionStatus, equalTo(false));
    }

    /**
     * Tests whether a connection gets refused when too many users are connected.
     */
    @Test
    void tooManyUsersTest()
            throws InvalidHostnameException, URISyntaxException, InvalidPortException {

        log.info("Testing connection of too many clients to server!");

        ConnectionHandler testConnection1 = new ConnectionHandler("127.0.0.1", port);
        testConnection1.setGameController(mock(GameController.class)); // Not necessary, but prevents null pointer exceptions.
        ConnectionHandler testConnection2 = new ConnectionHandler("127.0.0.1", port);
        testConnection2.setGameController(mock(GameController.class)); // Not necessary, but prevents null pointer exceptions.
        ConnectionHandler testConnection3 = new ConnectionHandler("127.0.0.1", port);
        testConnection3.setGameController(mock(GameController.class)); // Not necessary, but prevents null pointer exceptions.

        await().atMost(5, TimeUnit.SECONDS).until(() -> testConnection1.connect("TooManyTestUser1"), equalTo(true));
        await().atMost(5, TimeUnit.SECONDS).until(() -> testConnection2.connect("TooManyTestUser2"), equalTo(true));
        await().atMost(5, TimeUnit.SECONDS).until(() -> testConnection3.connect("TooManyTestUser3"), equalTo(true));


        await().atMost(5, TimeUnit.SECONDS).until(() -> Arrays.stream(testServer.getUsers()).toList().contains("TooManyTestUser1"), equalTo(true));
        await().atMost(5, TimeUnit.SECONDS).until(() -> Arrays.stream(testServer.getUsers()).toList().contains("TooManyTestUser2"), equalTo(true));
        await().atMost(5, TimeUnit.SECONDS).until(testConnection3::getConnectionStatus, equalTo(false));
    }

}
