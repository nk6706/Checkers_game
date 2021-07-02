package com.webcheckers.model;

/**
 * Player is a class which represents a user on the webcheckers application. Each Player has
 * a username unique to them while in a session.
 */
public class Player {

    private String username;

    /** ID of current game player is in */
    private int gameID;

    // @TODO check braces in all files
    public Player(String username) {
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

    /**
     * Whether or not this player is in a game
     * @return true if player is in a game, false otherwise
     */
    public boolean inGame() {
        return this.gameID != -1;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Player)) return false;
        final Player that = (Player) obj;
        return this.username.equals(that.username);
    }
}
