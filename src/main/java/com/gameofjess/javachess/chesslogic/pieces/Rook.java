package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;

public class Rook extends Piece {

    private static final Image whiteImage = new Image(Objects.requireNonNull(Rook.class.getResourceAsStream("/icons/wRook.png")));
    private static final Image blackImage = new Image(Objects.requireNonNull(Rook.class.getResourceAsStream("/icons/bRook.png")));

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

		//rechts
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() + j, position.getY());
			if (testposition.getX() < 8) {
				Piece testlocation = Board.getBoardMap().get(testposition);
				if(testlocation == null){
					moves.add(new Move(position, testposition));
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					moves.add(new Move(position, testposition, testposition));
					break;
				}
			}
			else break;
		}
		//links
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() - j, position.getY());
			if (testposition.getX() >= 0) {
				Piece testlocation = Board.getBoardMap().get(testposition);
				if(testlocation == null){
					moves.add(new Move(position, testposition));
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					moves.add(new Move(position, testposition, testposition));
					break;
				}
			}
			else break;
		}
		//oben
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX(), position.getY() + j);
			if (testposition.getY() < 8) {
				Piece testlocation = Board.getBoardMap().get(testposition);
				if(testlocation == null){
					moves.add(new Move(position, testposition));
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					moves.add(new Move(position, testposition, testposition));
					break;
				}
			}
			else break;
		}
		//unten
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX(), position.getY() - j);
			if (testposition.getY() >= 0) {
				Piece testlocation = Board.getBoardMap().get(testposition);
				if(testlocation == null){
					moves.add(new Move(position, testposition));
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					moves.add(new Move(position, testposition, testposition));
					break;
				}
			}
			else break;
		}
        return moves.toArray(new Move[moves.size()]);
    }

    
	/** 
	 * @param move
	 */
	@Override
    public void makeMove(Move move) {
		rochade = false;
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
		}
		else{
            return blackImage;
		}
	}


}
