package org.example.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;
import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;
import org.example.javachess.chesslogic.Position;

public class Bishop extends Piece {

    public Bishop(Board Board, boolean isWhite) {
        super(Board, isWhite);
        super.fen = "b";
    }

    @Override
    public Move[] getMoves(boolean checking) {
		checking = false;
        List<Move> moves = new ArrayList<Move>();
		Position position = Board.getPosition(this);

        int i;
        // hoch rechts
        i = 1;
        while (position.x + i < 8 && position.y + i < 8 && (Board.board.get(new Position(position.x + i,
                position.y + i)) == null
                || Board.board.get(new Position(position.x + i, position.y + i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x + i, position.y + i),
                    Board.board.get(new Position(position.x + i, position.y + i)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
            i++;
        }
        // runter rechts
        i = 1;
        while (position.x + i < 8 && position.y - i >= 0 && (Board.board.get(new Position(position.x + i,
                position.y - i)) == null
                || Board.board.get(new Position(position.x + i, position.y - i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x + i, position.y - i),
                    Board.board.get(new Position(position.x + i, position.y - i)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
            i++;
        }
        // runter links
        i = 1;
        while (position.x - i >= 0 && position.y - i >= 0 && (Board.board.get(new Position(position.x - i, position.y - i)) == null || Board.board.get(new Position(position.x - i, position.y - i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x - i, position.y - i),
                    Board.board.get(new Position(position.x - i, position.y - i)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
            i++;
        }
        // hoch links
        i = 1;
        while (position.x - i >= 0 && position.y + i < 8 && (Board.board.get(new Position(position.x - i,
                position.y + i)) == null
                || Board.board.get(new Position(position.x - i, position.y + i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x - i, position.y + i),
                    Board.board.get(new Position(position.x - i, position.y + i)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
            i++;
        }

        return moves.toArray(new Move[moves.size()]);
    }
}
