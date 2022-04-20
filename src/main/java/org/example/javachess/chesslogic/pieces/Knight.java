package org.example.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.chesslogic.Board;
import org.example.javachess.chesslogic.Move;

public class Knight extends Piece {

	public Knight(boolean isWhite, int[] position){
		super(isWhite, position);
		super.fen = "n";
	}

	@Override
	public Move[] getMoves(boolean checking) {
		List<Move> moves = new ArrayList<Move>();
		//hoch rechts
		if (position[0] < 7 && position[1] < 6 && Board.getPosition(position[0] +1 , position[1] + 2) == null) {
			Move test_move = new Move(position[0] +1, position[1] + 2);
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		else if (position[0] < 7 && position[1] < 6 && Board.getPosition(position[0] +1 , position[1] + 2).isWhite != isWhite) {
			Move test_move = new Move(position[0] +1, position[1] + 2, Board.getPosition(position[0] +1 , position[1] + 2));
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		//rechts hoch
		if (position[0] < 6 && position[1] < 7 && Board.getPosition(position[0] +2 , position[1] + 1) == null) {
			Move test_move = new Move(position[0] +2, position[1] + 1);
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		else if (position[0] < 6 && position[1] < 7 && Board.getPosition(position[0] +2 , position[1] + 1).isWhite != isWhite) {
			Move test_move = new Move(position[0] +2, position[1] + 1, Board.getPosition(position[0] +2 , position[1] + 1));
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		//rechts runter
		if (position[0] < 6 && position[1] > 0 && Board.getPosition(position[0] +2 , position[1] - 1) == null) {
			Move test_move = new Move(position[0] +2, position[1] - 1);
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		else if (position[0] < 6 && position[1] > 0 && Board.getPosition(position[0] +2 , position[1] - 1).isWhite != isWhite) {
			Move test_move = new Move(position[0] +2, position[1] - 1, Board.getPosition(position[0] +2 , position[1] - 1));
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		//runter rechts
		if (position[0] < 7 && position[1] > 1 && Board.getPosition(position[0] +1 , position[1] - 2) == null) {
			Move test_move = new Move(position[0] +1, position[1] - 2);
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		else if (position[0] < 7 && position[1] > 1 && Board.getPosition(position[0] +1 , position[1] - 2).isWhite != isWhite) {
			Move test_move = new Move(position[0] +1, position[1] - 2, Board.getPosition(position[0] +1 , position[1] - 2));
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		//runter links
		if (position[0] > 0 && position[1] > 1 && Board.getPosition(position[0] -1 , position[1] - 2) == null) {
			Move test_move = new Move(position[0] -1, position[1] - 2);
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		else if (position[0] > 0 && position[1] > 1 && Board.getPosition(position[0] -1 , position[1] - 2).isWhite != isWhite) {
			Move test_move = new Move(position[0] -1, position[1] - 2, Board.getPosition(position[0] -1 , position[1] - 2));
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		//Links runter
		if (position[0] > 1 && position[1] > 0 && Board.getPosition(position[0] -2 , position[1] - 1) == null) {
			Move test_move = new Move(position[0] -2, position[1] - 1);
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		else if (position[0] > 1 && position[1] > 0 && Board.getPosition(position[0] -2 , position[1] - 1).isWhite != isWhite) {
			Move test_move = new Move(position[0] -2, position[1] - 1, Board.getPosition(position[0] -2 , position[1] - 1));
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		//links hoch
		if (position[0] > 1 && position[1] < 7 && Board.getPosition(position[0] -2 , position[1] + 1) == null) {
			Move test_move = new Move(position[0] -2, position[1] + 1);
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		else if (position[0] > 1 && position[1] < 7 && Board.getPosition(position[0] -2 , position[1] + 1).isWhite != isWhite) {
			Move test_move = new Move(position[0] -2, position[1] + 1, Board.getPosition(position[0] -2 , position[1] + 1));
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		//hoch links
		if (position[0] > 0&& position[1] < 6 && Board.getPosition(position[0] -1 , position[1] + 2) == null) {
			Move test_move = new Move(position[0] -1, position[1] + 2);
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		else if (position[0] > 0 && position[1] < 6 && Board.getPosition(position[0] -1 , position[1] + 2).isWhite != isWhite) {
			Move test_move = new Move(position[0] -1, position[1] + 2, Board.getPosition(position[0] -1 , position[1] + 2));
			if (isWhite) {
				if (!checking || !Board.kingWhite.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
			else{
				if (!checking || !Board.kingBlack.checkCheck(test_move, this)) {
					moves.add(test_move);
				}
			}
		}
		return moves.toArray(new Move[moves.size()]);
	}

	@Override
	public Knight getClone() {
		return new Knight(isWhite, position);
	}
}
