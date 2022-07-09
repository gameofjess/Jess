package com.gameofjess.javachess.chesslogic;

import com.gameofjess.javachess.chesslogic.pieces.Queen;
import com.gameofjess.javachess.chesslogic.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {
    @Test
    void testEquals() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(1,0);
        Position pos3 = new Position(2,0);
        Position pos4 = new Position(3, 0);
        assertTrue(new Move(pos1, pos2).equals(new Move(pos1, pos2)));
        assertTrue(new Move(pos1, pos2, pos3).equals(new Move(pos1, pos2, pos3)));
        assertTrue(new Move(pos1, pos2, pos3, true).equals(new Move(pos1, pos2, pos3, true)));
        assertTrue(new Move(pos1, pos2, true, pos3).equals(new Move(pos1, pos2, true, pos3)));
        assertTrue(new Move(pos1, pos2, Queen.class.toString()).equals(new Move(pos1, pos2, Queen.class.toString())));
        assertFalse(new Move(pos1, pos2).equals(new Move(pos1, pos4)));
        assertFalse(new Move(pos1, pos2, pos3).equals(new Move(pos1, pos2, pos4)));
        assertFalse(new Move(pos1, pos2, pos3, true).equals(new Move(pos1, pos2, pos3, false)));
        assertFalse(new Move(pos1, pos2, true, pos3).equals(new Move(pos1, pos2, true, pos4)));
        assertFalse(new Move(pos1, pos2, Queen.class.toString()).equals(new Move(pos1, pos2, Rook.class.toString())));
    }
}