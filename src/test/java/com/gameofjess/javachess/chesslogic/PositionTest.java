package com.gameofjess.javachess.chesslogic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PositionTest {
    static List<Position> positions = new ArrayList<>();
    @BeforeAll
    static void init(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                positions.add(new Position(i, j));
            }
        }
    }

    @Test
    void getClone() {
        for (Position position: positions) {
            Position testposition = new Position(position.getX(), position.getY());
            assertTrue(position.getX() == testposition.getX() && position.getY() == testposition.getY());
        }
    }

    @Test
    void testEquals() {
        for (Position position: positions) {
            Position testposition = new Position(position.getX(), position.getY());
            assertEquals(position, testposition);
        }
        Position testposition = new Position(-1, -1);
        for (Position position: positions) {
            assertNotEquals(position, testposition);
        }
    }

    @Test
    void testHashCode() {
        for (Position position: positions) {
            Position testposition = new Position(position.getX(), position.getY());
            assertEquals(position.hashCode(), testposition.hashCode());
        }
        Position testposition = new Position(-1, -1);
        for (Position position: positions) {
            assertNotEquals(position.hashCode(), testposition.hashCode());
        }
    }

    @Test
    void getX() {
        for (int i = 0; i < 8; i++) {
            assertEquals(new Position(i, 0).getX(), i);
        }
    }

    @Test
    void getY() {
        for (int i = 0; i < 8; i++) {
            assertEquals(new Position(0, i).getY(), i);
        }
    }

    @Test
    void testToString() {
        for (Position position: positions) {
            assertEquals(position.toString(), "("+position.getX()+","+position.getY()+")");
        }
    }
}