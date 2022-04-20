package org.example.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;


public class Pawn extends Piece {
    public boolean enpassant = false;

    public Pawn(boolean isWhite, int[] position) {
        super(isWhite, position);
        super.fen = "p";
    }

    public Pawn(boolean isWhite, int[] position, boolean enpassant) {
        super(isWhite, position);
        this.enpassant = enpassant;
    }

    @Override
    public Move[] getMoves(boolean checking) {
        List<Move> moves = new ArrayList<Move>();
        if (isWhite) {

            Piece eins_vor = Board.getPosition(position[0], position[1] + 1);
            if (eins_vor == null) {
                Move test_move = new Move(position[0], position[1] + 1);
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

            Piece zwei_vor = Board.getPosition(position[0], position[1] + 2);
            if (position[1] == 2 && eins_vor == null && zwei_vor == null) {
                Move test_move = new Move(position[0], position[1] + 2);
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

            Piece schlagen_rechts = Board.getPosition(position[0] + 1, position[1] + 1);
            if (schlagen_rechts != null && !schlagen_rechts.isWhite) {
                Move test_move = new Move(position[0] + 1, position[1] + 1, schlagen_rechts);
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

            Piece schlagen_links = Board.getPosition(position[0] - 1, position[1] + 1);
            if (schlagen_links != null && !schlagen_links.isWhite) {
                Move test_move = new Move(position[0] - 1, position[1] + 1, schlagen_links);
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

            Piece enpassant_rechts = Board.getPosition(position[0] + 1, position[1]);
            if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite
                    && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
                Move test_move = new Move(position[0] + 1, position[1] + 1, enpassant_rechts);
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

            Piece enpassant_links = Board.getPosition(position[0] - 1, position[1]);
            if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite
                    && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
                Move test_move = new Move(position[0] - 1, position[1] + 1, enpassant_rechts);
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

            Piece eins_vor = Board.getPosition(position[0], position[1] - 1);
            if (eins_vor == null) {
                Move test_move = new Move(position[0], position[1] - 1);
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

            Piece zwei_vor = Board.getPosition(position[0], position[1] - 2);
            if (position[1] == 2 && eins_vor == null && zwei_vor == null) {
                Move test_move = new Move(true, position[0], position[1] - 2);
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

            Piece schlagen_rechts = Board.getPosition(position[0] + 1, position[1] - 1);
            if (schlagen_rechts != null && !schlagen_rechts.isWhite) {
                Move test_move = new Move(position[0] + 1, position[1] - 1, schlagen_rechts);
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

            Piece schlagen_links = Board.getPosition(position[0] - 1, position[1] - 1);
            if (schlagen_links != null && !schlagen_links.isWhite) {
                Move test_move = new Move(position[0] - 1, position[1] - 1, schlagen_links);
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

            Piece enpassant_rechts = Board.getPosition(position[0] + 1, position[1]);
            if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite
                    && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
                Move test_move = new Move(position[0] + 1, position[1] - 1, enpassant_rechts);
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

            Piece enpassant_links = Board.getPosition(position[0] - 1, position[1]);
            if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite
                    && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
                Move test_move = new Move(position[0] - 1, position[1] - 1, enpassant_rechts);
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

    @Override
    public void makeMove(Move move) {
        enpassant = move.enpassant;
        position[0] = move.destinationX;
        position[1] = move.destinationY;
        if (move.capture != null) {
            move.capture.position = null;
            Board.capturedPieces.add(move.capture);
            Board.pieces.remove(move.capture);
        }
    }

    @Override
    public Pawn getClone() {
        return new Pawn(isWhite, position, enpassant);
    }
}
