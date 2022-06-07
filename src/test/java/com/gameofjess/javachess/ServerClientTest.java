package com.gameofjess.javachess;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.gui.controller.GameController;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import com.gameofjess.javachess.server.Server;
import com.gameofjess.javachess.server.ServerBuilder;

public class ServerClientTest {

    Logger log = LogManager.getLogger(ServerClientTest.class);

    static Server testServer;
    static int port = 9887;

    @BeforeAll
    static void beforeAll() {
        testServer = new ServerBuilder().build();
    }

    @BeforeEach
    void beforeEach() throws InterruptedException {
        if (testServer.getServerStatus())
            testServer.stop();
        port++;
        testServer = new ServerBuilder().setPort(port).build();
        testServer.setReuseAddr(true);
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
        testServer.stop();
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

        testConnection1.connect("TooManyTestUser1");
        testConnection2.connect("TooManyTestUser2");
        testConnection3.connect("TooManyTestUser3");

        await().atMost(5, TimeUnit.SECONDS).until(() -> Arrays.stream(testServer.getUsers()).toList().contains("TooManyTestUser1"), equalTo(true));
        await().atMost(5, TimeUnit.SECONDS).until(() -> Arrays.stream(testServer.getUsers()).toList().contains("TooManyTestUser2"), equalTo(true));
        await().atMost(5, TimeUnit.SECONDS).until(testConnection3::getConnectionStatus, equalTo(false));
    }

}
