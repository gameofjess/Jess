package com.gameofjess.javachess.gui.helper.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.Board;
import com.gameofjess.javachess.chesslogic.pieces.*;

import javafx.scene.layout.GridPane;

/**
 * View for captured pieces.
 */
public class CapturedPieceGrid extends GridPane {

    private static final Logger log = LogManager.getLogger(CapturedPieceGrid.class);

    private final CapturedPieceView whiteKing;
    private final CapturedPieceView whiteQueen;
    private final CapturedPieceView whiteBishop;
    private final CapturedPieceView whiteKnight;
    private final CapturedPieceView whiteRook;
    private final CapturedPieceView whitePawn;

    private final CapturedPieceView blackKing;
    private final CapturedPieceView blackQueen;
    private final CapturedPieceView blackBishop;
    private final CapturedPieceView blackKnight;
    private final CapturedPieceView blackRook;
    private final CapturedPieceView blackPawn;


    /**
     * Constructs a CapturedPieceGrid with all possible pieces.
     */
    public CapturedPieceGrid() {
        log.debug("Building the view for captured pieces.");

        Board tempBoard = new Board();

        whiteKing = new CapturedPieceView(new King(tempBoard, true).getImage());
        whiteQueen = new CapturedPieceView(new Queen(tempBoard, true).getImage());
        whiteBishop = new CapturedPieceView(new Bishop(tempBoard, true).getImage());
        whiteKnight = new CapturedPieceView(new Knight(tempBoard, true).getImage());
        whiteRook = new CapturedPieceView(new Rook(tempBoard, true).getImage());
        whitePawn = new CapturedPieceView(new Pawn(tempBoard, true).getImage());

        blackKing = new CapturedPieceView(new King(tempBoard, false).getImage());
        blackQueen = new CapturedPieceView(new Queen(tempBoard, false).getImage());
        blackBishop = new CapturedPieceView(new Bishop(tempBoard, false).getImage());
        blackKnight = new CapturedPieceView(new Knight(tempBoard, false).getImage());
        blackRook = new CapturedPieceView(new Rook(tempBoard, false).getImage());
        blackPawn = new CapturedPieceView(new Pawn(tempBoard, false).getImage());

        this.add(whiteKing, 0, 0);
        this.add(whiteQueen, 0, 1);
        this.add(whiteBishop, 0, 2);
        this.add(whiteKnight, 0, 3);
        this.add(whiteRook, 0, 4);
        this.add(whitePawn, 0, 5);

        this.add(blackKing, 1, 0);
        this.add(blackQueen, 1, 1);
        this.add(blackBishop, 1, 2);
        this.add(blackKnight, 1, 3);
        this.add(blackRook, 1, 4);
        this.add(blackPawn, 1, 5);
    }

    /**
     * Adds a piece to the CapturedPieceGrid.
     * 
     * @param capturedPiece Piece to add.
     */
    public void add(Piece capturedPiece) {
        log.trace("Adding piece {} to captured pieces.", capturedPiece);
        boolean isWhite = capturedPiece.isWhite();
        switch (capturedPiece.getClass().getSimpleName()) {
            case "King" -> {
                if (isWhite) {
                    whiteKing.add();
                } else {
                    blackKing.add();
                }
            }

            case "Queen" -> {
                if (isWhite) {
                    whiteQueen.add();
                } else {
                    blackQueen.add();
                }
            }

            case "Bishop" -> {
                if (isWhite) {
                    whiteBishop.add();
                } else {
                    blackBishop.add();
                }
            }

            case "Knight" -> {
                if (isWhite) {
                    whiteKnight.add();
                } else {
                    blackKnight.add();
                }
            }

            case "Rook" -> {
                if (isWhite) {
                    whiteRook.add();
                } else {
                    blackRook.add();
                }
            }

            case "Pawn" -> {
                if (isWhite) {
                    whitePawn.add();
                } else {
                    blackPawn.add();
                }
            }
        }
    }

}
