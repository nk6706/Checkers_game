package com.webcheckers.model;

import com.webcheckers.util.Message;

/**
 * CheckersGame is a model-level representation of a game of checkers. Each CheckersGame has
 * two Player(s) and a CheckerBoard that holds its CheckerPiece(s). Additionally, each
 * CheckersGame has a unique integer ID.
 */
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
     * Getter method for game board (red)
     * @return CheckerBoard object of the board
     */
    public CheckerPiece[][] getBoard() {
        return board.getBoard();
    }

    /**
     * Getter method for game board (white)
     * @return CheckerBoard object of the board
     */
    public CheckerPiece[][] getFlippedBoard() {
        return board.getFlippedBoard();
    }
    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Determines if the provided player is the redPlayer of this game
     * @param player the player to check
     * @return true if the provided player is red, false otherwise (meaning they are white player)
     */
    public boolean isRedPlayer(Player player) { return player.equals(this.redPlayer); }

    /**
     * Method for TurnManager to check if a move is valid or not
     * @param move the move to check
     * @return Message.INFO if valid, Message.ERROR with error msg if invalid
     */
    public Message isValid(Move move) {
        return board.isValid(move);
    }

}
