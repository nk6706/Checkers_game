package com.webcheckers.ui.board;

import com.webcheckers.model.CheckerPiece;

public class Piece {

    private CheckerPiece.Type type;
    private CheckerPiece.Color color;

    public Piece(CheckerPiece.Type type, CheckerPiece.Color color) {
        this.type = type;
        this.color = color;
    }

    public CheckerPiece.Type getType() {
        return type;
    }

    public CheckerPiece.Color getColor() {
        return color;
    }


}