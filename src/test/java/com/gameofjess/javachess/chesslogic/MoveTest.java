package com.gameofjess.javachess.chesslogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.gameofjess.javachess.chesslogic.pieces.Queen;
import com.gameofjess.javachess.chesslogic.pieces.Rook;

class MoveTest {
    @Test
    void testEquals() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,0);
        Position pos3 = new Position(2,0);
        Position pos4 = new Position(3, 0);
        assertEquals(new Move(pos1, pos2), new Move(pos1, pos2));
        assertEquals(new Move(pos1, pos2, pos3), new Move(pos1, pos2, pos3));
        assertEquals(new Move(pos1, pos2, pos3, true), new Move(pos1, pos2, pos3, true));
        assertEquals(new Move(pos1, pos2, true, pos3), new Move(pos1, pos2, true, pos3));
        assertEquals(new Move(pos1, pos2, Queen.class.toString()), new Move(pos1, pos2, Queen.class.toString()));
        assertNotEquals(new Move(pos1, pos2), new Move(pos1, pos4));
        assertNotEquals(new Move(pos1, pos2, pos3), new Move(pos1, pos2, pos4));
        assertNotEquals(new Move(pos1, pos2, pos3, true), new Move(pos1, pos2, pos3, false));
        assertNotEquals(new Move(pos1, pos2, true, pos3), new Move(pos1, pos2, true, pos4));
        assertNotEquals(new Move(pos1, pos2, Queen.class.toString()), new Move(pos1, pos2, Rook.class.toString()));
    }
}