package com.webcheckers.model;

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

    /** Holds the player whose turn it is */
    private Player activePlayer;

    private CheckerBoard board;

    public CheckersGame(int id, Player redPlayer, Player whitePlayer) {
        this.id = id;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activePlayer = redPlayer;

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
    public CheckerPiece[][] getBoard(boolean isRedPlayer) {
        return board.getBoard(isRedPlayer);
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
     * Determines if it is the provided player's turn (if they are the activePlayer)
     * @param player the player to check if its their turn
     * @return true if it is the player's turn, false otherwise
     */
    public boolean isPlayersTurn(Player player) { return player.equals(activePlayer); }

}
