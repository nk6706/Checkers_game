package com.webcheckers.model;

/**
 * Move is a small class acting as a value object that stores move data and positions
 */
public class Move {

    /** Position attributes */
    private Position start;
    private Position end;

    /**
     * Construct a new move data object
     * @param start Starting position for this move
     * @param end Ending position for this move
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Getter for starting position of this move
     * @return Position object holding the coordinate pair of the starting position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Getter for ending position of this move
     * @return Position object holding the coordinate pair of the ending position
     */
    public Position getEnd(){
        return end;
    }
}
