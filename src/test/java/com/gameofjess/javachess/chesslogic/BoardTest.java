package com.gameofjess.javachess.chesslogic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.gameofjess.javachess.chesslogic.pieces.Piece;

class BoardTest {

    @Test
    void playtest(){
        assertDoesNotThrow(() -> {
            Board board = new Board();
            for (int i = 0; i < 50; i++) {
                List<Piece> pieces = new ArrayList<Piece>(board.getBoardMap().values());
                Piece piece = pieces.get(i% pieces.size());
                if (i%2 == 0 && piece.isWhite() || i%2 == 1 && !piece.isWhite()){
                    Move[] moves = piece.getMoves();
                    if(moves.length > 0){
                        Move move = moves[i % moves.length];
                        assertEquals(piece.getPosition(), move.getOrigin());
                        piece.makeMove(move);
                        assertEquals(piece.getPosition(), move.getDestination());
                    }
                }
            }
        });
    }

    @Test
    void initialization(){
        Board board = new Board();
        assertEquals(board.toString(),
                "(0,0) |  null  \n" +
                "(1,0) |  n  \n" +
                "(2,0) |  b  \n" +
                "(3,0) |  k  \n" +
                "(4,0) |  q  \n" +
                "(5,0) |  b  \n" +
                "(6,0) |  n  \n" +
                "(7,0) |  null  \n" +
                "(0,1) |  p  \n" +
                "(1,1) |  p  \n" +
                "(2,1) |  p  \n" +
                "(3,1) |  p  \n" +
                "(4,1) |  p  \n" +
                "(5,1) |  p  \n" +
                "(6,1) |  p  \n" +
                "(7,1) |  p  \n" +
                "(0,6) |  p  \n" +
                "(1,6) |  p  \n" +
                "(2,6) |  p  \n" +
                "(3,6) |  p  \n" +
                "(4,6) |  p  \n" +
                "(5,6) |  p  \n" +
                "(6,6) |  p  \n" +
                "(7,6) |  p  \n" +
                "(0,7) |  null  \n" +
                "(1,7) |  n  \n" +
                "(2,7) |  b  \n" +
                "(3,7) |  k  \n" +
                "(4,7) |  q  \n" +
                "(5,7) |  b  \n" +
                "(6,7) |  n  \n" +
                "(7,7) |  null  \n");
    }
}