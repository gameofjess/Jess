package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;
import com.gameofjess.javachess.helper.game.Pieces;

import javafx.scene.image.Image;

public class Bishop extends Piece {
	private static final Logger log = LogManager.getLogger(Bishop.class);

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
		log.trace("getting moves bishop");
		List<Move> moves = new ArrayList<Move>();
		Position position = board.getPosition(this);

		// hoch rechts
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() + j, position.getY() + j);
			if (testposition.getX() < 8 && testposition.getY() < 8) {
				Piece testlocation = board.getBoardMap().get(testposition);
				if(testlocation == null){
					Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					Move testmove = new Move(position, testposition, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
					break;
				}
			}
			else break;
		}
		//runter rechts
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() + j, position.getY() - j);
			if (testposition.getX() < 8 && testposition.getY() >= 0) {
				Piece testlocation = board.getBoardMap().get(testposition);
				if(testlocation == null){
					Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					Move testmove = new Move(position, testposition, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
					break;
				}
			}
			else break;
		}
		// runter links
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() - j, position.getY() - j);
			if (testposition.getX() >= 0 && testposition.getY() >= 0) {
				Piece testlocation = board.getBoardMap().get(testposition);
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
		// hoch links
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() - j, position.getY() + j);
			if (testposition.getX() >= 0 && testposition.getY() < 8) {
				Piece testlocation = board.getBoardMap().get(testposition);
				if(testlocation == null){
					Move testmove = new Move(position, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
				}
				else if (testlocation.isWhite() == isWhite) {
					break;
				}
				else if(testlocation.isWhite() != isWhite){
					Move testmove = new Move(position, testposition, testposition);
					if (!checking || !checkCheckMove(testmove)) {
						moves.add(testmove);
					}
					break;
				}
			}
			else break;
		}

		return moves.toArray(new Move[moves.size()]);
	}

    @Override
    public Pieces getEnumValue() {
        return Pieces.BISHOP;
    }

	@Override
	public Image getImage() {
        return getEnumValue().getImage(isWhite);
	}


	@Override
	public Piece getClone(Board board) {
		return new Bishop(board, isWhite);
	}
}
