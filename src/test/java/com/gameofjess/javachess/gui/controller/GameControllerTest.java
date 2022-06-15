package com.gameofjess.javachess.gui.controller;

import static org.mockito.Mockito.verify;

import com.gameofjess.javachess.helper.messages.ServerMessage;
import org.java_websocket.framing.CloseFrame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.gui.objects.BoardPane;
import com.gameofjess.javachess.gui.objects.CapturedPieceGrid;
import com.gameofjess.javachess.helper.game.Color;
import com.gameofjess.javachess.server.Server;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private Board board;
    @Mock
    private ConnectionHandler connectionHandler;
    @Mock
    private Server server;

    @InjectMocks
    private GameController gameController;
    
    
    @Test
    void closeConnectionTest() throws InterruptedException {
         gameController.closeConnection("SampleReason");
         verify(connectionHandler).disconnect(CloseFrame.GOING_AWAY, "SampleReason");
         verify(server).stop();
    }

    @Test
    void receiveNewMoveTest() {
        gameController.receiveMessage(new ServerMessage());
    }
    
}
