package org.example.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;
import org.example.javachess.chesslogic.Position;

public class King extends Piece {
    public boolean rochade = true;

    public King(Board Board, boolean isWhite) {
        super(Board, isWhite);
        super.fen = "k";
    }

    public King(Board Board, boolean isWhite, boolean rochade) {
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

        Piece test;
        // hoch
        test = Board.board.get( new Position(position.x, position.y + 1));
        if (position.y + 1 < 8 && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x, position.y + 1), test);
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
        // hoch rechts
        test = Board.board.get( new Position(position.x + 1, position.y + 1));
        if (position.x + 1 < 8 && position.y + 1 < 8
                && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x + 1, position.y + 1), test);
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
        // rechts
        test = Board.board.get( new Position(position.x + 1, position.y));
        if (position.x + 1 < 8 && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x + 1, position.y), test);
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
        test = Board.board.get( new Position(position.x + 1, position.y - 1));
        if (position.x + 1 < 8 && position.y - 1 >= 0
                && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x + 1, position.y - 1), test);
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
        // runter
        test = Board.board.get( new Position(position.x, position.y - 1));
        if (position.y - 1 >= 0 && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x, position.y - 1), test);
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
        test = Board.board.get( new Position(position.x - 1, position.y - 1));
        if (position.x - 1 >= 0 && position.y - 1 >= 0
                && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x - 1, position.y - 1), test);
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
        // links
        test = Board.board.get( new Position(position.x - 1, position.y));
        if (position.x - 1 >= 0 && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x - 1, position.y), test);
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
        test = Board.board.get( new Position(position.x - 1, position.y + 1));
        if (position.x - 1 >= 0 && position.y + 1 < 8
                && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.x - 1, position.y + 1), test);
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
		//rochade rechts
        if (rochade && Board.board.get( new Position(5, position.y)) == null
                && Board.board.get( new Position(6, position.y)) == null) {
            Piece rook = Board.board.get( new Position(7, position.y));
            if (rook instanceof Rook) {
                if (((Rook) rook).rochade) {
                    Move test_move = new Move(new Position(6, position.y), true);
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
            }
        }

        if (rochade && Board.board.get( new Position(1, position.y)) == null
                && Board.board.get( new Position(2, position.y)) == null
                && Board.board.get( new Position(3, position.y)) == null) {
            Piece rook = Board.board.get( new Position(0, position.y));
            if (rook instanceof Rook) {
                if (((Rook) rook).rochade) {
                    Move test_move = new Move(new Position(2, position.y), true);
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
            }
        }

        return moves.toArray(new Move[moves.size()]);
    }

    
	/** 
	 * @param move
	 */
	@Override
    public void makeMove(Move move) {
        rochade = false;

        if (move.rochade) {
			//rechts
            if (move.destination.x == 6) {
				Position rookpos = new Position(7, move.destination.y);
				Position kingpos = new Position(4, move.destination.y);
				Rook rook = (Rook) Board.board.get(rookpos);
				King king = (King) Board.board.get(kingpos);
				Board.board.remove(rookpos);
				Board.board.remove(kingpos);
				Board.board.put(move.destination, king);
				Board.board.put(new Position(5, move.destination.y), rook);
				rook.rochade = false;
				king.rochade = false;

			}
        }

        if (move.capture != null) {
			Board.capturedPieces.add(move.capture);
		}
		Board.board.remove(Board.getPosition(this));
        Board.board.put(move.destination , this);
    }

    
	/** 
	 * @return boolean
	 */
	public boolean checkCheck() {
		
		for (Piece piece : Board.board.values()) {

			for (Move move : piece.getMoves()) {
				if(move.destination == Board.getPosition(this)){
					return true;
				}
			}
		}
        return false;
    }

    
	/** 
	 * @param test_move
	 * @param test_piece
	 * @return boolean
	 */
	public boolean checkCheck(Move test_move, Piece test_piece) {
        /*
		TODO


		List<Piece> piecesCopy = new ArrayList<Piece>();


        // clone board
        for (Piece piece : Board.pieces) {
            piecesCopy.add(piece.getClone());
        }

        // simulate move
        for (Piece piece : piecesCopy) {
            if (piece.position.x == test_piece.position.x
                    && piece.position.y == test_piece.position.y) {
                piece.makeMove(test_move);
                break;
            }
        }

        for (Piece piece : piecesCopy) {
            for (Move move : piece.getMoves(false)) {
                if (move.destinationX == position.x && move.destinationY == position.y) {
                    return true;
                }
            }
        }
		*/
        return false;
    }
}
