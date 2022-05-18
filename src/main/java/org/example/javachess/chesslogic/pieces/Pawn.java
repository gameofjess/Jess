package org.example.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;
import org.example.javachess.chesslogic.Position;


public class Pawn extends Piece {
    public boolean enpassant = false;

    public Pawn(Board Board, boolean isWhite) {
        super(Board, isWhite);
        super.fen = "p";
    }

    public Pawn(Board Board, boolean isWhite, boolean enpassant) {
        super(Board, isWhite);
        this.enpassant = enpassant;
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

        if (isWhite) {

            Piece eins_vor = Board.board.get( new Position(position.x, position.y + 1));
            if (eins_vor == null) {
                Move test_move = new Move(new Position(position.x, position.y + 1));
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

            Piece zwei_vor = Board.board.get( new Position(position.x, position.y + 2));
            if (position.y == 2 && eins_vor == null && zwei_vor == null) {
                Move test_move = new Move(new Position(position.x, position.y + 2));
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

            Piece schlagen_rechts = Board.board.get( new Position(position.x + 1, position.y + 1));
            if (schlagen_rechts != null && !schlagen_rechts.isWhite) {
                Move test_move = new Move(new Position(position.x + 1, position.y + 1), schlagen_rechts);
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

            Piece schlagen_links = Board.board.get( new Position(position.x - 1, position.y + 1));
            if (schlagen_links != null && !schlagen_links.isWhite) {
                Move test_move = new Move(new Position(position.x - 1, position.y + 1), schlagen_links);
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

            Piece enpassant_rechts = Board.board.get( new Position(position.x + 1, position.y));
            if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite
                    && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
                Move test_move = new Move(new Position(position.x + 1, position.y + 1), enpassant_rechts);
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

            Piece enpassant_links = Board.board.get( new Position(position.x - 1, position.y));
            if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite
                    && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
                Move test_move = new Move(new Position(position.x - 1, position.y + 1), enpassant_rechts);
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
        } else {

            Piece eins_vor = Board.board.get( new Position(position.x, position.y - 1));
            if (eins_vor == null) {
                Move test_move = new Move(new Position(position.x, position.y - 1));
                if (isWhite) {
                    if (!!checking || !Board.kingWhite.checkCheck(test_move, this)) {
                        moves.add(test_move);
                    }
                } else {
                    if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
                        moves.add(test_move);
                    }
                }
            }

            Piece zwei_vor = Board.board.get( new Position(position.x, position.y - 2));
            if (position.y == 2 && eins_vor == null && zwei_vor == null) {
                Move test_move = new Move(true, new Position(position.x, position.y - 2));
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

            Piece schlagen_rechts = Board.board.get( new Position(position.x + 1, position.y - 1));
            if (schlagen_rechts != null && !schlagen_rechts.isWhite) {
                Move test_move = new Move(new Position(position.x + 1, position.y - 1), schlagen_rechts);
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

            Piece schlagen_links = Board.board.get( new Position(position.x - 1, position.y - 1));
            if (schlagen_links != null && !schlagen_links.isWhite) {
                Move test_move = new Move(new Position(position.x - 1, position.y - 1), schlagen_links);
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

            Piece enpassant_rechts = Board.board.get( new Position(position.x + 1, position.y));
            if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite
                    && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
                Move test_move = new Move(new Position(position.x + 1, position.y - 1), enpassant_rechts);
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

            Piece enpassant_links = Board.board.get( new Position(position.x - 1, position.y));
            if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite
                    && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
                Move test_move = new Move(new Position(position.x - 1, position.y - 1), enpassant_rechts);
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
        return moves.toArray(new Move[moves.size()]);
    }

    
	/** 
	 * @param move
	 */
	@Override
    public void makeMove(Move move) {
        enpassant = move.enpassant;
		if (move.capture != null) {
			Board.capturedPieces.add(move.capture);
		}
		Board.board.remove(Board.getPosition(this));
        Board.board.put(move.destination , this);
    }
}
