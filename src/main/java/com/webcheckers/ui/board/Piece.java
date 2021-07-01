package com.webcheckers.ui.board;

import com.webcheckers.model.CheckerPiece;

// @TODO Remove Piece class in ui tier (use model tier)
/**
 * Piece is a UI-level representation of a checker piece used by the UI's BoardView component. It has
 * the same type (SINGLE or KING) and color (RED or WHITE) attributes as the model's CheckerPiece class.
 */
public class Piece {

    private CheckerPiece.Type type;
    private CheckerPiece.Color color;

    public Piece(CheckerPiece.Type type, CheckerPiece.Color color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Getter for the Piece's Type
     * @return Type the Piece's Type
     */
    public CheckerPiece.Type getType() {
        return type;
    }

    /**
     * Getter for the Piece's Color
     * @return Type the Piece's Color
     */
    public CheckerPiece.Color getColor() {
        return color;
    }


}