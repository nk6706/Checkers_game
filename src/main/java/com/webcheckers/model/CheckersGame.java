package com.webcheckers.model;

public class CheckersGame {

    /** unique id for this game */
    private int id;

    private Player redPlayer;
    private Player whitePlayer;

    private CheckerBoard board;

    public CheckersGame(int id, Player redPlayer, Player whitePlayer) {
        this.id = id;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;

        board = new CheckerBoard();
    }

    /**
     * Getter method for unique id
     * @return int representing the id of this game
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method for game board
     * @return CheckerBoard object of the board
     */
    public CheckerBoard getBoard() {
        return board;
    }

}
