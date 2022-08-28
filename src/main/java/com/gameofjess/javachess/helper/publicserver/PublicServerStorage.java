package com.gameofjess.javachess.helper.publicserver;

import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.helper.game.Color;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class PublicServerStorage {

    private static final Logger log = LogManager.getLogger(PublicServerStorage.class);


    private final List<User> userList = new CopyOnWriteArrayList<>();
    private final List<Lobby> lobbyList = new CopyOnWriteArrayList<>();

    public UUID createUser(String username) {
        UUID uuid = UUID.randomUUID();
        userList.add(new User(username, uuid));
        return uuid;
    }

    public void removeUser(UUID uuid) {
        User user = getUserByUUID(uuid);
        log.debug("Removing user {} with UUID {}!", user.getUsername(), uuid);
        userList.remove(user);
    }

    public void removeLobby(UUID lobbyUUID) {
        Lobby lobby = getLobbyByUUID(lobbyUUID);
        log.debug("Removing lobby {} with UUID {}!", lobby.getName(), lobbyUUID);
        lobbyList.remove(lobby);
    }

    public boolean addUserToLobby(UUID UserUUID, UUID lobbyUUID, String password) {
        User user = getUserByUUID(UserUUID);
        Lobby lobby = getLobbyByUUID(lobbyUUID);
        return lobby.addUser(user, password);
    }

    public UUID createLobby(String name, String password) {
        UUID uuid = UUID.randomUUID();
        lobbyList.add(new Lobby(uuid, name, password));
        log.debug("Successfully created lobby with name {} and assigned UUID {}!", name, uuid);
        return uuid;
    }

    public boolean checkMoveInLobby(UUID lobbyUUID, Move m) {
        Lobby l = getLobbyByUUID(lobbyUUID);
        return l.checkMove(m);
    }

    public Color checkCheckMateInLobby(UUID lobbyUUID) {
        Lobby l = getLobbyByUUID(lobbyUUID);
        return l.checkCheckMate();
    }

    public void makeMove(UUID lobbyUUID, Move m) {
        Lobby l = getLobbyByUUID(lobbyUUID);
        l.makeMove(m);
    }

    public String getLobbiesJSON() {
        return new Gson().toJson(lobbyList);
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(userList);
    }

    public boolean doesUsernameExist(String username) {
        return userList.parallelStream().anyMatch(user -> user.getUsername().equals(username));
    }

    public boolean doesLobbyNameExist(String name) {
        return lobbyList.parallelStream().anyMatch(lobby -> lobby.getName().equals(name));
    }

    public String getUsernameByUUID(UUID userUUID) {
        User u = userList.parallelStream().filter(user -> user.getUuid().equals(userUUID)).findFirst().orElse(null);
        if (u != null) return u.getUsername();
        return null;
    }

    public List<User> getUsersByLobbyUUID(UUID lobbyUUID) {
        Lobby l = lobbyList.parallelStream().filter(lobby -> lobby.getUuid().equals(lobbyUUID)).findFirst().orElse(null);
        if (l != null) {
            return l.getUserList();
        }
        return null;
    }

    public UUID getLobbyUUIDByUserUUID(UUID userUUID) {
        User u = userList.parallelStream().filter(user -> user.getUuid().equals(userUUID)).findFirst().orElse(null);
        if (u != null) {
            Lobby l = lobbyList.parallelStream().filter(lobby -> lobby.getUserList().contains(u)).findFirst().orElse(null);
            if (l != null) {
                return l.getUuid();
            }
        }
        return null;
    }

    private User getUserByUUID(UUID uuid) {
        return userList.parallelStream()
                .filter(user -> user.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    private Lobby getLobbyByUUID(UUID uuid) {
        return lobbyList.parallelStream()
                .filter(lobby -> lobby.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

}
