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

public class Rook extends Piece {
	private static final Logger log = LogManager.getLogger(Rook.class);

    boolean rochade = false;

	/**
	 * Constructor
	 * @param Board
	 * @param isWhite
	 */
    public Rook(Board Board, boolean isWhite) {
        super(Board, isWhite);
        super.fen = "r";
    }

	/**
	 * Constructor
	 * @param Board
	 * @param isWhite
	 * @param rochade
	 */
    public Rook(Board Board, boolean isWhite, boolean rochade) {
        super(Board, isWhite);
        this.rochade = rochade;
    }

	@Override
    public Move[] getMoves(boolean checking) {
		log.trace("getting moves rook");
        List<Move> moves = new ArrayList<Move>();
		Position position = board.getPosition(this);

		//rechts
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() + j, position.getY());
			if (testposition.getX() < 8) {
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
		//links
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX() - j, position.getY());
			if (testposition.getX() >= 0) {
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
		//oben
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX(), position.getY() + j);
			if (testposition.getY() < 8) {
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
		//unten
		for (int j = 1; j < 8; j++) {
			Position testposition = new Position(position.getX(), position.getY() - j);
			if (testposition.getY() >= 0) {
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
    public void makeMove(Move move) {
		log.debug("current board:\n{}", board);
		log.trace("making move");
		rochade = false;
		if (move.getCapturePosition() != null) {
			board.capture(move.getCapturePosition());
		}
		board.boardMapRemove(board.getPosition(this));
        board.boardMapAdd(move.getDestination() , this);
		log.debug("current board:\n{}", board);
		log.debug("current board:\n{}", board);
    }

    @Override
    public Pieces getEnumValue() {
        return Pieces.ROOK;
    }

	@Override
	public Image getImage() {
        return getEnumValue().getImage(isWhite);
	}

	@Override
	public Piece getClone(Board board) {
		return new Rook(board, isWhite, rochade);
	}


}
