package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;

public class Knight extends Piece {

    public Knight(Board Board, boolean isWhite) {
        super(Board, isWhite);
        super.fen = "n";
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

        // hoch rechts
        if (position.getX() < 7 && position.getY() < 6
                && Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() + 2)) == null) {
            Move test_move = new Move(new Position(position.getX() + 1, position.getY() + 2));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.getX() < 7 && position.getY() < 6
                && Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() + 2)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.getX() + 1, position.getY() + 2),
                    Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() + 2)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // rechts hoch
        if (position.getX() < 6 && position.getY() < 7
                && Board.getBoardMap().get( new Position(position.getX() + 2, position.getY() + 1)) == null) {
            Move test_move = new Move(new Position(position.getX() + 2, position.getY() + 1));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.getX() < 6 && position.getY() < 7
                && Board.getBoardMap().get( new Position(position.getX() + 2, position.getY() + 1)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.getX() + 2, position.getY() + 1),
                    Board.getBoardMap().get( new Position(position.getX() + 2, position.getY() + 1)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // rechts runter
        if (position.getX() < 6 && position.getY() > 0
                && Board.getBoardMap().get( new Position(position.getX() + 2, position.getY() - 1)) == null) {
            Move test_move = new Move(new Position(position.getX() + 2, position.getY() - 1));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.getX() < 6 && position.getY() > 0
                && Board.getBoardMap().get( new Position(position.getX() + 2, position.getY() - 1)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.getX() + 2, position.getY() - 1),
                    Board.getBoardMap().get( new Position(position.getX() + 2, position.getY() - 1)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // runter rechts
        if (position.getX() < 7 && position.getY() > 1
                && Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() - 2)) == null) {
            Move test_move = new Move(new Position(position.getX() + 1, position.getY() - 2));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.getX() < 7 && position.getY() > 1
                && Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() - 2)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.getX() + 1, position.getY() - 2),
                    Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() - 2)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // runter links
        if (position.getX() > 0 && position.getY() > 1
                && Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() - 2)) == null) {
            Move test_move = new Move(new Position(position.getX() - 1, position.getY() - 2));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.getX() > 0 && position.getY() > 1
                && Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() - 2)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.getX() - 1, position.getY() - 2),
                    Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() - 2)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // Links runter
        if (position.getX() > 1 && position.getY() > 0
                && Board.getBoardMap().get( new Position(position.getX() - 2, position.getY() - 1)) == null) {
            Move test_move = new Move(new Position(position.getX() - 2, position.getY() - 1));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.getX() > 1 && position.getY() > 0
                && Board.getBoardMap().get( new Position(position.getX() - 2, position.getY() - 1)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.getX() - 2, position.getY() - 1),
                    Board.getBoardMap().get( new Position(position.getX() - 2, position.getY() - 1)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // links hoch
        if (position.getX() > 1 && position.getY() < 7
                && Board.getBoardMap().get( new Position(position.getX() - 2, position.getY() + 1)) == null) {
            Move test_move = new Move(new Position(position.getX() - 2, position.getY() + 1));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.getX() > 1 && position.getY() < 7
                && Board.getBoardMap().get( new Position(position.getX() - 2, position.getY() + 1)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.getX() - 2, position.getY() + 1),
                    Board.getBoardMap().get( new Position(position.getX() - 2, position.getY() + 1)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // hoch links
        if (position.getX() > 0 && position.getY() < 6
                && Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() + 2)) == null) {
            Move test_move = new Move(new Position(position.getX() - 1, position.getY() + 2));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.getX() > 0 && position.getY() < 6
                && Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() + 2)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.getX() - 1, position.getY() + 2),
                    Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() + 2)));
            if (isWhite) {
                if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        return moves.toArray(new Move[moves.size()]);
    }


	@Override
	public Image getImage() {
		if (isWhite) {
			return new Image(getClass().getResourceAsStream("/icons/wKnight.png"));
		}
		else{
			return new Image(getClass().getResourceAsStream("/icons/bKnight.png"));
		}
	}

}
