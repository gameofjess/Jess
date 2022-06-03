package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;

public class Bishop extends Piece {

    public Bishop(Board Board, boolean isWhite) {
        super(Board, isWhite);
        super.fen = "b";
    }

    
	/** 
	 * @param checking
	 * @return Move[]
	 */
	@Override
    public Move[] getMoves(boolean checking) {
		checking = false;
        List<Move> moves = new ArrayList<Move>();
		Position position = Board.getPosition(this);

        int i;
        // hoch rechts
        i = 1;
        while (position.getX() + i < 8 && position.getY() + i < 8 && (Board.getBoardMap().get(new Position(position.getX() + i,
                position.getY() + i)) == null
                || Board.getBoardMap().get(new Position(position.getX() + i, position.getY() + i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() + i, position.getY() + i),
                    Board.getBoardMap().get(new Position(position.getX() + i, position.getY() + i)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
            i++;
        }
        // runter rechts
        i = 1;
        while (position.getX() + i < 8 && position.getY() - i >= 0 && (Board.getBoardMap().get(new Position(position.getX() + i,
                position.getY() - i)) == null
                || Board.getBoardMap().get(new Position(position.getX() + i, position.getY() - i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() + i, position.getY() - i),
                    Board.getBoardMap().get(new Position(position.getX() + i, position.getY() - i)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
            i++;
        }
        // runter links
        i = 1;
        while (position.getX() - i >= 0 && position.getY() - i >= 0 && (Board.getBoardMap().get(new Position(position.getX() - i, position.getY() - i)) == null || Board.getBoardMap().get(new Position(position.getX() - i, position.getY() - i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() - i, position.getY() - i),
                    Board.getBoardMap().get(new Position(position.getX() - i, position.getY() - i)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
            i++;
        }
        // hoch links
        i = 1;
        while (position.getX() - i >= 0 && position.getY() + i < 8 && (Board.getBoardMap().get(new Position(position.getX() - i,
                position.getY() + i)) == null
                || Board.getBoardMap().get(new Position(position.getX() - i, position.getY() + i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() - i, position.getY() + i),
                    Board.getBoardMap().get(new Position(position.getX() - i, position.getY() + i)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
            i++;
        }

        return moves.toArray(new Move[moves.size()]);
    }

	@Override
	public Image getImage() {
		if (isWhite) {
			return new Image(getClass().getResourceAsStream("/icons/wBishop.png"));
		}
		else{
			return new Image(getClass().getResourceAsStream("/icons/bBishop.png"));
		}
	}
}
