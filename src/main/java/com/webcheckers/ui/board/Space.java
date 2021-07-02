package com.webcheckers.ui.board;

import com.webcheckers.model.CheckerPiece;

/**
 * Space is a UI-level representation of a single space on a checker board. A Space is white or dark,
 * has an x coordinate relative to its position in a Row (leftmost space being 0), and can hold a
 * checker piece represented by Piece (which can also be null if no piece is present at that space).
 */
public class Space {

    private int cellIdx;
    private boolean isDark;

    private CheckerPiece piece = null;

    public Space(int cellIdx, CheckerPiece piece, boolean isDark) {
        this.cellIdx = cellIdx;
        this.isDark = isDark;
        if(piece != null)
            this.piece = piece;
    }

    /**
     * Getter for cellIdx
     * @return int cellidx
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * get piece
     * @return Piece
     */
    public CheckerPiece getPiece() {
        return piece;
    }

    /**
     * Returns whether or not we can move to space
     * @return
     */
    public boolean isValid() {
        return isDark && this.piece == null;
    }

}