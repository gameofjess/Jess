package com.gameofjess.javachess.helper.publicserver;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.helper.game.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Lobby {

    private static final Logger log = LogManager.getLogger(Lobby.class);

    private final UUID uuid;

    private final List<User> userList = new ArrayList<>();
    private final String name;
    private final transient String passwordHash;
    private final transient Board board;

    Lobby(UUID uuid, String name, String password) {
        this.uuid = uuid;
        this.name = name;
        this.passwordHash = hashPassword(password);
        this.board = new Board();
    }

    public boolean checkMove(Move m) {
        return board.isMoveValid(m);
    }

    public void makeMove(Move m) {
        log.trace("Making move from {} to {} in lobby {}", m.getOrigin(), m.getDestination(), uuid);
        board.getBoardMap().get(m.getOrigin()).makeMove(m);
    }

    public Color checkCheckMate() {
        if (board.getKingBlack().checkCheckMate()) {
            return Color.BLACK;
        } else if (board.getKingWhite().checkCheckMate()) {
            return Color.WHITE;
        }
        return null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public List<User> getUserList() {
        return Collections.unmodifiableList(userList);
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public boolean addUser(User user, String password) {
        if (hashPassword(password).equals(passwordHash)) {
            userList.add(user);

            if (userList.size() == 1) {
                log.debug("Assigning random color to user {}!", user.getUuid());
                user.setGameColor(getRandomColor());
            } else {
                log.debug("Assigning available color to user {}.", user.getUuid());
                Color otherColor = userList.stream().filter(u -> !u.equals(user)).findFirst().orElseThrow().getGameColor();
                if (otherColor == Color.WHITE) {
                    user.setGameColor(Color.BLACK);
                } else {
                    user.setGameColor(Color.WHITE);
                }
            }

            return true;
        }
        return false;
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

    /**
     * @return random value of Color-enum.
     */
    private Color getRandomColor() {
        if (new Random().nextBoolean()) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    public void removeUser(User user) {
        userList.remove(user);
    }
}
