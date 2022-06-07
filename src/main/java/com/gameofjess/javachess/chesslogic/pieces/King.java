package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;

public class King extends Piece {

    private static final Image whiteImage = new Image(Objects.requireNonNull(King.class.getResourceAsStream("/icons/wKing.png")));
    private static final Image blackImage = new Image(Objects.requireNonNull(King.class.getResourceAsStream("/icons/bKing.png")));

    boolean rochade = true;

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
        test = Board.getBoardMap().get( new Position(position.getX(), position.getY() + 1));
        if (position.getY() + 1 < 8 && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX(), position.getY() + 1), test);
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
        // hoch rechts
        test = Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() + 1));
        if (position.getX() + 1 < 8 && position.getY() + 1 < 8
                && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() + 1, position.getY() + 1), test);
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
        // rechts
        test = Board.getBoardMap().get( new Position(position.getX() + 1, position.getY()));
        if (position.getX() + 1 < 8 && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() + 1, position.getY()), test);
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
        test = Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() - 1));
        if (position.getX() + 1 < 8 && position.getY() - 1 >= 0
                && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() + 1, position.getY() - 1), test);
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
        // runter
        test = Board.getBoardMap().get( new Position(position.getX(), position.getY() - 1));
        if (position.getY() - 1 >= 0 && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX(), position.getY() - 1), test);
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
        test = Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() - 1));
        if (position.getX() - 1 >= 0 && position.getY() - 1 >= 0
                && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() - 1, position.getY() - 1), test);
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
        // links
        test = Board.getBoardMap().get( new Position(position.getX() - 1, position.getY()));
        if (position.getX() - 1 >= 0 && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() - 1, position.getY()), test);
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
        test = Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() + 1));
        if (position.getX() - 1 >= 0 && position.getY() + 1 < 8
                && (test == null || test.isWhite != isWhite)) {
            Move test_move = new Move(new Position(position.getX() - 1, position.getY() + 1), test);
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
		//rochade rechts
        if (rochade && Board.getBoardMap().get( new Position(5, position.getY())) == null
                && Board.getBoardMap().get( new Position(6, position.getY())) == null) {
            Piece rook = Board.getBoardMap().get( new Position(7, position.getY()));
            if (rook instanceof Rook) {
                if (((Rook) rook).rochade) {
                    Move test_move = new Move(new Position(6, position.getY()), true);
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
            }
        }

        if (rochade && Board.getBoardMap().get( new Position(1, position.getY())) == null
                && Board.getBoardMap().get( new Position(2, position.getY())) == null
                && Board.getBoardMap().get( new Position(3, position.getY())) == null) {
            Piece rook = Board.getBoardMap().get( new Position(0, position.getY()));
            if (rook instanceof Rook) {
                if (((Rook) rook).rochade) {
                    Move test_move = new Move(new Position(2, position.getY()), true);
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
            if (move.destination.getX() == 6) {
				Position rookpos = new Position(7, move.destination.getY());
				Position kingpos = new Position(4, move.destination.getY());
				Rook rook = (Rook) Board.getBoardMap().get(rookpos);
				King king = (King) Board.getBoardMap().get(kingpos);
				Board.boardMapRemove(rookpos);
				Board.boardMapRemove(kingpos);
				Board.boardMapAdd(move.destination, king);
				Board.boardMapAdd(new Position(5, move.destination.getY()), rook);
				rook.rochade = false;
				king.rochade = false;

			}
        }

        if (move.capture != null) {
			Board.addCapturedPiece(move.capture);
		}
		Board.boardMapRemove(Board.getPosition(this));
        Board.boardMapAdd(move.destination , this);
    }

    
	/** 
	 * @return boolean
	 */
	public boolean checkCheck() {
		
		for (Piece piece : Board.getBoardMap().values()) {

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
            if (piece.position.getX() == test_piece.position.x
                    && piece.position.getY() == test_piece.position.getY()) {
                piece.makeMove(test_move);
                break;
            }
        }

        for (Piece piece : piecesCopy) {
            for (Move move : piece.getMoves(false)) {
                if (move.destinationX == position.getX() && move.destinationY == position.getY()) {
                    return true;
                }
            }
        }
		*/
        return false;
    }

    @Override
    public Image getImage() {
        if (isWhite) {
            return whiteImage;
        } else {
            return blackImage;
        }
    }

}
