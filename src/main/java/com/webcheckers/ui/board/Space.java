package com.webcheckers.ui.board;

import com.webcheckers.model.CheckerPiece;

public class Space {

    private int cellIdx;
    private boolean isDark;

    private Piece piece = null;

    public Space(int cellIdx, CheckerPiece piece, boolean isDark) {
        this.cellIdx = cellIdx;
        this.isDark = isDark;
        if(piece != null)
            this.piece = new Piece(piece.getType(), piece.getColor());
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
    public Piece getPiece() {
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