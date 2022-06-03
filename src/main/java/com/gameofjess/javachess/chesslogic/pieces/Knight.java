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
        if (position.x < 7 && position.y < 6
                && Board.board.get( new Position(position.x + 1, position.y + 2)) == null) {
            Move test_move = new Move(new Position(position.x + 1, position.y + 2));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.x < 7 && position.y < 6
                && Board.board.get( new Position(position.x + 1, position.y + 2)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.x + 1, position.y + 2),
                    Board.board.get( new Position(position.x + 1, position.y + 2)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // rechts hoch
        if (position.x < 6 && position.y < 7
                && Board.board.get( new Position(position.x + 2, position.y + 1)) == null) {
            Move test_move = new Move(new Position(position.x + 2, position.y + 1));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.x < 6 && position.y < 7
                && Board.board.get( new Position(position.x + 2, position.y + 1)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.x + 2, position.y + 1),
                    Board.board.get( new Position(position.x + 2, position.y + 1)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // rechts runter
        if (position.x < 6 && position.y > 0
                && Board.board.get( new Position(position.x + 2, position.y - 1)) == null) {
            Move test_move = new Move(new Position(position.x + 2, position.y - 1));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.x < 6 && position.y > 0
                && Board.board.get( new Position(position.x + 2, position.y - 1)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.x + 2, position.y - 1),
                    Board.board.get( new Position(position.x + 2, position.y - 1)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // runter rechts
        if (position.x < 7 && position.y > 1
                && Board.board.get( new Position(position.x + 1, position.y - 2)) == null) {
            Move test_move = new Move(new Position(position.x + 1, position.y - 2));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.x < 7 && position.y > 1
                && Board.board.get( new Position(position.x + 1, position.y - 2)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.x + 1, position.y - 2),
                    Board.board.get( new Position(position.x + 1, position.y - 2)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // runter links
        if (position.x > 0 && position.y > 1
                && Board.board.get( new Position(position.x - 1, position.y - 2)) == null) {
            Move test_move = new Move(new Position(position.x - 1, position.y - 2));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.x > 0 && position.y > 1
                && Board.board.get( new Position(position.x - 1, position.y - 2)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.x - 1, position.y - 2),
                    Board.board.get( new Position(position.x - 1, position.y - 2)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // Links runter
        if (position.x > 1 && position.y > 0
                && Board.board.get( new Position(position.x - 2, position.y - 1)) == null) {
            Move test_move = new Move(new Position(position.x - 2, position.y - 1));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.x > 1 && position.y > 0
                && Board.board.get( new Position(position.x - 2, position.y - 1)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.x - 2, position.y - 1),
                    Board.board.get( new Position(position.x - 2, position.y - 1)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // links hoch
        if (position.x > 1 && position.y < 7
                && Board.board.get( new Position(position.x - 2, position.y + 1)) == null) {
            Move test_move = new Move(new Position(position.x - 2, position.y + 1));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.x > 1 && position.y < 7
                && Board.board.get( new Position(position.x - 2, position.y + 1)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.x - 2, position.y + 1),
                    Board.board.get( new Position(position.x - 2, position.y + 1)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        }
        // hoch links
        if (position.x > 0 && position.y < 6
                && Board.board.get( new Position(position.x - 1, position.y + 2)) == null) {
            Move test_move = new Move(new Position(position.x - 1, position.y + 2));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            }
        } else if (position.x > 0 && position.y < 6
                && Board.board.get( new Position(position.x - 1, position.y + 2)).isWhite != isWhite) {
            Move test_move = new Move(new Position(position.x - 1, position.y + 2),
                    Board.board.get( new Position(position.x - 1, position.y + 2)));
            if (isWhite) {
                if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                    moves.add(test_move);
                }
            } else {
                if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
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