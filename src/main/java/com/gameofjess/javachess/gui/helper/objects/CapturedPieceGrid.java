package com.gameofjess.javachess.gui.helper.objects;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.chesslogic.pieces.Piece;
import com.gameofjess.javachess.helper.game.Pieces;

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

        List<CapturedPieceView> capturedPieceViewList = List.of(whiteKing = new CapturedPieceView(Pieces.KING, true), whiteQueen = new CapturedPieceView(Pieces.QUEEN, true),
                whiteBishop = new CapturedPieceView(Pieces.BISHOP, true), whiteKnight = new CapturedPieceView(Pieces.KNIGHT, true),
                whiteRook = new CapturedPieceView(Pieces.ROOK, true), whitePawn = new CapturedPieceView(Pieces.PAWN, true), blackKing = new CapturedPieceView(Pieces.KING, false),
                blackQueen = new CapturedPieceView(Pieces.QUEEN, false), blackBishop = new CapturedPieceView(Pieces.BISHOP, false),
                blackKnight = new CapturedPieceView(Pieces.KNIGHT, false), blackRook = new CapturedPieceView(Pieces.ROOK, false),
                blackPawn = new CapturedPieceView(Pieces.PAWN, false));

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

        capturedPieceViewList.forEach(CapturedPieceView::renderPieceImage);
    }

    /**
     * Adds a piece to the CapturedPieceGrid.
     * 
     * @param capturedPiece Piece to add.
     */
    public void add(Piece capturedPiece) {
        log.trace("Adding piece {} to captured pieces.", capturedPiece);
        boolean isWhite = capturedPiece.isWhite();
        switch (capturedPiece.getEnumValue()) {
            case KING -> {
                if (isWhite) {
                    whiteKing.add();
                } else {
                    blackKing.add();
                }
            }

            case QUEEN -> {
                if (isWhite) {
                    whiteQueen.add();
                } else {
                    blackQueen.add();
                }
            }

            case BISHOP -> {
                if (isWhite) {
                    whiteBishop.add();
                } else {
                    blackBishop.add();
                }
            }

            case KNIGHT -> {
                if (isWhite) {
                    whiteKnight.add();
                } else {
                    blackKnight.add();
                }
            }

            case ROOK -> {
                if (isWhite) {
                    whiteRook.add();
                } else {
                    blackRook.add();
                }
            }

            case PAWN -> {
                if (isWhite) {
                    whitePawn.add();
                } else {
                    blackPawn.add();
                }
            }
        }
    }

}
