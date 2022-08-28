package com.gameofjess.javachess.client;

import com.gameofjess.javachess.helper.messages.ClientMessage;
import com.gameofjess.javachess.helper.messages.MessageType;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PublicClient extends WebSocketClient {

    public PublicClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] digested = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            Byte[] bytes = new Byte[digested.length];
            int i = 0;
            for (byte b : digested) {
                bytes[i++] = b;
            }

            return Arrays.stream(bytes).collect(StringBuilder::new, (x, y) -> x.append(String.format("%02X", y)), StringBuilder::append).toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        PublicClient pc = new PublicClient(new URI("ws://127.0.0.1:8887"));
        pc.addHeader("username", "Test");
        pc.addHeader("public", "true");
        pc.connectBlocking();
        pc.send(new ClientMessage("TestLobby,TestPassword", MessageType.CREATELOBBY).toJSON());
    }
}
