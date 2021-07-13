package com.webcheckers.model;

/**
 * Position is a class which represents an x-y-coordinate on a plane.
 */
public class Position {

    private int row;
    private int cell;

    public Position (int x, int y) {
        this.row = x;
        this.cell = y;
    }

    /**
     * Getter for the Position's x coordinate.
     * @return int the Position's x coordinate
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for the Position's y coordinate.
     * @return int the Position's y coordinate
     */
    public int getCell() {
        return cell;
    }

}
