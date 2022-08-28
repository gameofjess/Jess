package com.gameofjess.javachess.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerFactoryTest {

    /**
     * Tests the ServerBuilder-Class
     */
    @Test
    void serverInstantiationTest() {
        ServerFactory sb = new ServerFactory(false);
        sb.setHost("127.0.0.1");
        sb.setPort(10000);
        PrivateServer server = (PrivateServer) sb.build();

        assertEquals(server.getPort(), 10000);
        assertEquals("/127.0.0.1", server.getAddress().getAddress().toString());
    }
}
