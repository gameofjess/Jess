package org.example.javachess.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ServerBuilderTest {

    /**
     * Tests the ServerBuilder-Class
     */
    @Test
    void serverInstantiationTest() {
        ServerBuilder sb = new ServerBuilder();
        sb.setHost("127.0.0.1");
        sb.setPort(10000);
        Server server = sb.build();

        assertEquals(server.getPort(), 10000);
        assertTrue(server.getAddress().getHostName().contains("localhost"));
    }
}
