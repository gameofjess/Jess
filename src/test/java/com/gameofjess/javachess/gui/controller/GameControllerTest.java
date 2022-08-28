package com.gameofjess.javachess.gui.controller;

import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.server.PrivateServer;
import org.java_websocket.framing.CloseFrame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private ConnectionHandler connectionHandler;
    @Mock
    private PrivateServer server;

    @InjectMocks
    private GameController gameController;


    /**
     * Tests the closeConnection method in GameController that is called upon closure of the
     * application.
     */
    @Test
    void closeConnectionTest() throws InterruptedException {
        gameController.closeConnection("SampleReason");
        verify(connectionHandler).disconnect(CloseFrame.GOING_AWAY, "SampleReason");
        verify(server).stop();
    }

}
