package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;


public class Pawn extends Piece {

    private static final Image whiteImage = new Image(Objects.requireNonNull(Pawn.class.getResourceAsStream("/icons/wPawn.png")));
    private static final Image blackImage = new Image(Objects.requireNonNull(Pawn.class.getResourceAsStream("/icons/bPawn.png")));

    boolean enpassant = false;

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

            Piece eins_vor = Board.getBoardMap().get( new Position(position.getX(), position.getY() + 1));
            if (eins_vor == null) {
                Move test_move = new Move(new Position(position.getX(), position.getY() + 1));
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

            Piece zwei_vor = Board.getBoardMap().get( new Position(position.getX(), position.getY() + 2));
            if (position.getY() == 1 && eins_vor == null && zwei_vor == null) {
                Move test_move = new Move(new Position(position.getX(), position.getY() + 2));
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

            Piece schlagen_rechts = Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() + 1));
            if (schlagen_rechts != null && !schlagen_rechts.isWhite) {
                Move test_move = new Move(new Position(position.getX() + 1, position.getY() + 1), schlagen_rechts);
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

            Piece schlagen_links = Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() + 1));
            if (schlagen_links != null && !schlagen_links.isWhite) {
                Move test_move = new Move(new Position(position.getX() - 1, position.getY() + 1), schlagen_links);
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

            Piece enpassant_rechts = Board.getBoardMap().get( new Position(position.getX() + 1, position.getY()));
            if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite
                    && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
                Move test_move = new Move(new Position(position.getX() + 1, position.getY() + 1), enpassant_rechts);
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

            Piece enpassant_links = Board.getBoardMap().get( new Position(position.getX() - 1, position.getY()));
            if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite
                    && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
                Move test_move = new Move(new Position(position.getX() - 1, position.getY() + 1), enpassant_rechts);
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
		
		//SCHWARZ
		else {

            Piece eins_vor = Board.getBoardMap().get( new Position(position.getX(), position.getY() - 1));
            if (eins_vor == null) {
                Move test_move = new Move(new Position(position.getX(), position.getY() - 1));
                if (isWhite) {
                    if (!!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
                        moves.add(test_move);
                    }
                } else {
                    if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
                        moves.add(test_move);
                    }
                }
            }

            Piece zwei_vor = Board.getBoardMap().get( new Position(position.getX(), position.getY() - 2));
            if (position.getY() == 6 && eins_vor == null && zwei_vor == null) {
                Move test_move = new Move(true, new Position(position.getX(), position.getY() - 2));
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

            Piece schlagen_rechts = Board.getBoardMap().get( new Position(position.getX() + 1, position.getY() - 1));
            if (schlagen_rechts != null && schlagen_rechts.isWhite) {
                Move test_move = new Move(new Position(position.getX() + 1, position.getY() - 1), schlagen_rechts);
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

            Piece schlagen_links = Board.getBoardMap().get( new Position(position.getX() - 1, position.getY() - 1));
            if (schlagen_links != null && schlagen_links.isWhite) {
                Move test_move = new Move(new Position(position.getX() - 1, position.getY() - 1), schlagen_links);
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

            Piece enpassant_rechts = Board.getBoardMap().get( new Position(position.getX() + 1, position.getY()));
            if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite
                    && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
                Move test_move = new Move(new Position(position.getX() + 1, position.getY() - 1), enpassant_rechts);
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

            Piece enpassant_links = Board.getBoardMap().get( new Position(position.getX() - 1, position.getY()));
            if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite
                    && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
                Move test_move = new Move(new Position(position.getX() - 1, position.getY() - 1), enpassant_rechts);
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
        return moves.toArray(new Move[moves.size()]);
    }

    
	/** 
	 * @param move
	 */
	@Override
    public void makeMove(Move move) {
        enpassant = move.enpassant;
		if (move.capture != null) {
			Board.addCapturedPiece(move.capture);
		}
		Board.boardMapRemove(Board.getPosition(this));
        Board.boardMapAdd(move.destination , this);
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
