package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;

public class Bishop extends Piece {

    private static final Image whiteImage = new Image(Objects.requireNonNull(Bishop.class.getResourceAsStream("/icons/wBishop.png")));
    private static final Image blackImage = new Image(Objects.requireNonNull(Bishop.class.getResourceAsStream("/icons/bBishop.png")));

	public Bishop(Board Board, boolean isWhite) {
		super(Board, isWhite);
		super.fen = "b";
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
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() + j, position.getY() + j);
			if (testposition.getX() < 8 && testposition.getY() < 8) {
				Piece testlocation = Board.getBoardMap().get(testposition);
				if(testlocation == null){
					moves.add(new Move(testposition));
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					moves.add(new Move(testposition, testlocation));
					break;
				}
			}
			else break;
		}
		//runter rechts
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() + j, position.getY() - j);
			if (testposition.getX() < 8 && testposition.getY() >= 0) {
				Piece testlocation = Board.getBoardMap().get(testposition);
				if(testlocation == null){
					moves.add(new Move(testposition));
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					moves.add(new Move(testposition, testlocation));
					break;
				}
			}
			else break;
		}
		// runter links
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() - j, position.getY() - j);
			if (testposition.getX() >= 0 && testposition.getY() >= 0) {
				Piece testlocation = Board.getBoardMap().get(testposition);
				if(testlocation == null){
					moves.add(new Move(testposition));
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					moves.add(new Move(testposition, testlocation));
					break;
				}
			}
			else break;
		}
		// hoch links
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() - j, position.getY() + j);
			if (testposition.getX() >= 0 && testposition.getY() < 8) {
				Piece testlocation = Board.getBoardMap().get(testposition);
				if(testlocation == null){
					moves.add(new Move(testposition));
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					moves.add(new Move(testposition, testlocation));
					break;
				}
			}
			else break;
		}

		return moves.toArray(new Move[moves.size()]);
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
