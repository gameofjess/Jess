package com.gameofjess.javachess.chesslogic.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.Move;
import com.gameofjess.javachess.chesslogic.Position;

import javafx.scene.image.Image;

public class Knight extends Piece {
	private static final Logger log = LogManager.getLogger(Knight.class);

    private static final Image whiteImage = new Image(Objects.requireNonNull(Piece.class.getResourceAsStream("/icons/wKnight.png")));
    private static final Image blackImage = new Image(Objects.requireNonNull(Piece.class.getResourceAsStream("/icons/bKnight.png")));

    public Knight(Board Board, boolean isWhite) {
        super(Board, isWhite);
        super.fen = "n";
    }

    
	/** 
	 * @param checking
	 * @return Move[]
	 */
	@Override
    public Move[] getMoves(boolean checking) {
		log.trace("getting moves knight");
        List<Move> moves = new ArrayList<Move>();
		Position position = board.getPosition(this);


		Position testposition;
		Piece testlocation;
        
		// hoch rechts
		testposition = new Position(position.getX() + 1, position.getY() + 2);
		if (testposition.getX() < 8 && testposition.getY() < 8) {
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			else if (testlocation.isWhite != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
        // rechts hoch
		testposition = new Position(position.getX() + 2, position.getY() + 1);
		if (testposition.getX() < 8 && testposition.getY() < 8) {
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			else if (testlocation.isWhite != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
        // rechts runter
        testposition = new Position(position.getX() + 2, position.getY() - 1);
		if (testposition.getX() < 8 && testposition.getY() >= 0) {
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			else if (testlocation.isWhite != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// runter rechts
        testposition = new Position(position.getX() + 1, position.getY() - 2);
		if (testposition.getX() < 8 && testposition.getY() >= 0) {
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			else if (testlocation.isWhite != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// runter links
        testposition = new Position(position.getX() - 1, position.getY() - 2);
		if (testposition.getX() >= 0 && testposition.getY() >= 0) {
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			else if (testlocation.isWhite != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// links runter
        testposition = new Position(position.getX() - 2, position.getY() - 1);
		if (testposition.getX() >= 0 && testposition.getY() >= 0) {
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			else if (testlocation.isWhite != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// links hoch
        testposition = new Position(position.getX() - 2, position.getY() + 1);
		if (testposition.getX() >= 0 && testposition.getY() < 8) {
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			else if (testlocation.isWhite != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
		}
		// hoch links
        testposition = new Position(position.getX() - 1, position.getY() + 2);
		if (testposition.getX() >= 0 && testposition.getY() < 8) {
			testlocation = board.getBoardMap().get(testposition);
			if (testlocation == null) {
				Move testmove = new Move(position, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
			else if (testlocation.isWhite != isWhite) {
				Move testmove = new Move(position, testposition, testposition);
				if (!checking || !checkCheckMove(testmove)) {
					moves.add(testmove);
				}
			}
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


	@Override
	public Piece getClone(Board board) {
		return new Knight(board, isWhite);
	}

}
