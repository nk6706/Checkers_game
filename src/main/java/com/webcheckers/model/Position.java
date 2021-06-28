package com.webcheckers.model;

/**
 * Position is a class which represents an x-y-coordinate on a plane.
 */
public class Position {

    private int x;
    private int y;

    public Position (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the Position's x coordinate.
     * @return int the Position's x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the Position's y coordinate.
     * @return int the Position's y coordinate
     */
    public int getY() {
        return y;
    }

}
