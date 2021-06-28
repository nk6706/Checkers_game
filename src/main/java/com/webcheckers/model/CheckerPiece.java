package com.webcheckers.model;

public class CheckerPiece {

    public enum Color {
        RED, WHITE
    }

    public enum Type {
        SINGLE, KING
    }

    private Color color;
    private Type type;

    public CheckerPiece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() { return type; }

    public String toString() {
        if (color == Color.RED) {
            return "R";
        }
        else {
            return "W";
        }
    }

}
