package com.webcheckers.model;

/**
 * Player is a class which represents a user on the webcheckers application. Each Player has
 * a username unique to them while in a session.
 */
public class Player {

    private String username;

    /** ID of current game player is in */
    private int gameID;

    public Player(String username)
    {
        this.username = username;
        this.gameID = -1;
    }

    /**
     * Getter method for username
     * @return String represents username of this player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for gameID
     * @return int representing id of game player is in (-1 if not in a game)
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Setter for gameID
     * @param gameID int representing id of game player is in (-1 if not in a game)
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
