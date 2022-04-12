package org.example.javachess.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServerBuilderTest {

    /**
     * Tests the ServerBuilder-Class
     */
    @Test
    void serverInstantiationTest() {
        ServerBuilder sb = new ServerBuilder();
        sb.setPort(8888);
        sb.setHost("localhost");
        Server server = sb.build();

        assertEquals(server.getPort(), 8888);
        assertTrue(server.getAddress().getHostName().contains("localhost"));
    }
}
