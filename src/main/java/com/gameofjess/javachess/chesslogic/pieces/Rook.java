package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;

public class Rook extends Piece {
    boolean rochade = true;

    public Rook(Board Board, boolean isWhite) {
        super(Board, isWhite);
        super.fen = "r";
    }

    public Rook(Board Board, boolean isWhite, boolean rochade) {
        super(Board, isWhite);
        this.rochade = rochade;
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
        // rechts
        i = 1;
        while (position.getX() + i < 8 && (Board.getBoardMap().get( new Position(position.getX() + i, position.getY())) == null
                || Board.getBoardMap().get( new Position(position.getX() + i, position.getY())).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() + i, position.getY()),
                    Board.getBoardMap().get( new Position(position.getX() + i, position.getY())));
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
        // links
        i = 1;
        while (position.getX() - i >= 0 && (Board.getBoardMap().get( new Position(position.getX() - i, position.getY())) == null
                || Board.getBoardMap().get( new Position(position.getX() - i, position.getY())).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() - i, position.getY()),
                    Board.getBoardMap().get( new Position(position.getX() - i, position.getY())));
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
        // hoch
        i = 1;
        while (position.getY() + i < 8 && (Board.getBoardMap().get( new Position(position.getX(), position.getY() + i)) == null
                || Board.getBoardMap().get( new Position(position.getX(), position.getY() + i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX(), position.getY() + i),
                    Board.getBoardMap().get( new Position(position.getX() - i, position.getY() + i)));
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
        // runter
        i = 1;
        while (position.getY() - i >= 0 && (Board.getBoardMap().get( new Position(position.getX(), position.getY() - i)) == null
                || Board.getBoardMap().get( new Position(position.getX(), position.getY() - i)).isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX(), position.getY() - i),
                    Board.getBoardMap().get( new Position(position.getX(), position.getY() - i)));
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

    
	/** 
	 * @param move
	 */
	@Override
    public void makeMove(Move move) {
		rochade = false;
		if (move.capture != null) {
			Board.addCapturedPiece(move.capture);
		}
		Board.boardMapRemove(Board.getPosition(this));
        Board.boardMapAdd(move.destination , this);
    }

	@Override
	public Image getImage() {
		if (isWhite) {
			return new Image(getClass().getResourceAsStream("/icons/wRook.png"));
		}
		else{
			return new Image(getClass().getResourceAsStream("/icons/bRook.png"));
		}
	}


}
