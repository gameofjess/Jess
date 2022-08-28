package com.gameofjess.javachess.helper.messages;

/**
 * This enum is used to determine a message's type. There are the following rules:
 *
 * SERVERINFO: This message type may be used when there is an event the server wants to announce.
 * This can be anything the other types can't match. This type may only be used in ServerMessage!
 *
 * SERVERERROR: This message type may be used when an error or exception happens. This type may only
 * be used in ServerMessage!
 *
 * BEGINMATCH: This message type may be used to tell the first player, that the second player
 * joined. This type may only be used in ServerMessage!
 *
 * CHATMESSAGE: This message type transmits new chat messages.
 *
 * NEWMOVE: This message type is used to transmit a new move.
 */
public enum MessageType {

    SERVERINFO,
    SERVERERROR,
    BEGINMATCH,
    CHATMESSAGE,
    NEWMOVE,
    USERLIST,
    LOBBYLIST,
    CREATELOBBY,
    JOINLOBBY,
    COLORINFO,
    CHECKMATE;

}
