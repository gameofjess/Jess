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


		Position testposition;
		Piece testlocation;
        if (isWhite) {

			testposition = new Position(position.getX(), position.getY() + 1);
            testlocation = Board.getBoardMap().get(testposition);
            if (testlocation == null) {
                moves.add(new Move(position, testposition));
				testposition = new Position(position.getX(), position.getY() + 2);
            	testlocation = Board.getBoardMap().get(testposition);
				if(position.getY() == 1 && testlocation == null){
					moves.add(new Move(position, testposition));
				}
            }

			testposition = new Position(position.getX() + 1, position.getY() + 1);
			testlocation = Board.getBoardMap().get(testposition);
            if (testlocation != null && !testlocation.isWhite) {
                moves.add(new Move(position, testposition, testposition));
            }

            testposition = new Position(position.getX() - 1, position.getY() + 1);
			testlocation = Board.getBoardMap().get(testposition);
            if (testlocation != null && !testlocation.isWhite) {
                moves.add(new Move(position, testposition, testposition));
            }

            // Piece enpassant_rechts = Board.getBoardMap().get( new Position(position.getX() + 1, position.getY()));
            // if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite
            //         && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
            //     Move test_move = new Move(new Position(position.getX() + 1, position.getY() + 1), enpassant_rechts);
            //     if (isWhite) {
            //         if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
            //             moves.add(test_move);
            //         }
            //     } else {
            //         if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
            //             moves.add(test_move);
            //         }
            //     }
            // }

            // Piece enpassant_links = Board.getBoardMap().get( new Position(position.getX() - 1, position.getY()));
            // if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite
            //         && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
            //     Move test_move = new Move(new Position(position.getX() - 1, position.getY() + 1), enpassant_rechts);
            //     if (isWhite) {
            //         if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
            //             moves.add(test_move);
            //         }
            //     } else {
            //         if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
            //             moves.add(test_move);
            //         }
            //     }
            // }
        } 
		
		//SCHWARZ
		else {

			testposition = new Position(position.getX(), position.getY() - 1);
            testlocation = Board.getBoardMap().get(testposition);
            if (testlocation == null) {
                moves.add(new Move(position, testposition));
				testposition = new Position(position.getX(), position.getY() - 2);
            	testlocation = Board.getBoardMap().get(testposition);
				if(position.getY() == 6 && testlocation == null){
					moves.add(new Move(position, testposition));
				}
            }

			testposition = new Position(position.getX() + 1, position.getY() - 1);
			testlocation = Board.getBoardMap().get(testposition);
            if (testlocation != null && testlocation.isWhite) {
                moves.add(new Move(position, testposition, testposition));
            }

            testposition = new Position(position.getX() - 1, position.getY() - 1);
			testlocation = Board.getBoardMap().get(testposition);
            if (testlocation != null && testlocation.isWhite) {
                moves.add(new Move(position, testposition, testposition));
            }

            // Piece enpassant_rechts = Board.getBoardMap().get( new Position(position.getX() + 1, position.getY()));
            // if (schlagen_rechts == null && enpassant_rechts != null && !enpassant_rechts.isWhite
            //         && enpassant_rechts instanceof Pawn && ((Pawn) enpassant_rechts).enpassant) {
            //     Move test_move = new Move(new Position(position.getX() + 1, position.getY() - 1), enpassant_rechts);
            //     if (isWhite) {
            //         if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
            //             moves.add(test_move);
            //         }
            //     } else {
            //         if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
            //             moves.add(test_move);
            //         }
            //     }
            // }

            // Piece enpassant_links = Board.getBoardMap().get( new Position(position.getX() - 1, position.getY()));
            // if (schlagen_links == null && enpassant_links != null && !enpassant_links.isWhite
            //         && enpassant_links instanceof Pawn && ((Pawn) enpassant_links).enpassant) {
            //     Move test_move = new Move(new Position(position.getX() - 1, position.getY() - 1), enpassant_rechts);
            //     if (isWhite) {
            //         if (!checking || !Board.getKingWhite().checkCheck(test_move, this)) {
            //             moves.add(test_move);
            //         }
            //     } else {
            //         if (!checking || !Board.getKingBlack().checkCheck(test_move, this)) {
            //             moves.add(test_move);
            //         }
            //     }
            // }
        }
        return moves.toArray(new Move[moves.size()]);
    }

    
	/** 
	 * @param move
	 */
	@Override
    public void makeMove(Move move) {
        enpassant = move.getEnpassant();
		if (move.getCapturePosition() != null) {
			Board.capture(move.getCapturePosition());
		}
		Board.boardMapRemove(Board.getPosition(this));
        Board.boardMapAdd(move.getDestination() , this);
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
