package org.example.javachess;

import org.example.javachess.client.ConnectionHandler;
import org.example.javachess.helper.exceptions.InvalidHostnameException;
import org.example.javachess.helper.exceptions.InvalidPortException;
import org.example.javachess.server.Server;
import org.example.javachess.server.ServerBuilder;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ServerClientTest {

    /**
     * Tests whether a connection between a server and a client can be established.
     */
    @Test
    void clientServerConnectTest() throws InvalidHostnameException, URISyntaxException, InvalidPortException {
        Random random = new Random();
        int port = random.nextInt(1000, 65535);

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.start();

        ConnectionHandler testConnection = new ConnectionHandler("127.0.0.1", port);

        assertTrue(testConnection.connect("TestUser"));

        boolean isConnected = false;

        // This waits one seconds if the client gets connected, if not, the test fails.
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 1000) {
            if (Arrays.stream(testServer.getUsers()).toList().contains("TestUser")) {
                isConnected = true;
                break;
            }
        }
        assertTrue(isConnected);
    }

    /**
     * Tests whether a connection with a duplicate username gets rejected.
     */
    @Test
    void invalidUsernameTest() throws InvalidHostnameException, URISyntaxException, InvalidPortException {
        Random random = new Random();
        int port = random.nextInt(1000, 65535);

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.start();

        ConnectionHandler testConnection1 = new ConnectionHandler("127.0.0.1", port);
        ConnectionHandler testConnection2 = new ConnectionHandler("127.0.0.1", port);

        testConnection1.connect("TestUser");
        testConnection2.connect("TestUser");

        boolean isDisonnected = false;
        // This waits one seconds if the client gets disconnected, if not, the test fails.
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 1000) {
            if (!testConnection2.getConnectionStatus()) {
                isDisonnected = true;
                break;
            }
        }

        assertTrue(isDisonnected);
    }

    @Test
    void tooManyUsersTest() throws InvalidHostnameException, URISyntaxException, InvalidPortException {
        Random random = new Random();
        int port = random.nextInt(1000, 65535);

        Server testServer = new ServerBuilder().setPort(port).build();
        testServer.start();

        ConnectionHandler testConnection1 = new ConnectionHandler("127.0.0.1", port);
        ConnectionHandler testConnection2 = new ConnectionHandler("127.0.0.1", port);
        ConnectionHandler testConnection3 = new ConnectionHandler("127.0.0.1", port);

        testConnection1.connect("TestUser1");
        testConnection2.connect("TestUser2");
        testConnection3.connect("TestUser3");

        boolean isDisonnected = false;
        // This waits one seconds if the client gets disconnected, if not, the test fails.
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 1000) {
            if (!testConnection3.getConnectionStatus()) {
                isDisonnected = true;
                break;
            }
        }

        assertTrue(isDisonnected);
    }

}
