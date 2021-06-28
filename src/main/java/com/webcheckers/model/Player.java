package com.webcheckers.model;

/**
 * Player is a class which represents a user on the webcheckers application. Each Player has
 * a username unique to them while in a session.
 */
public class Player {

    private String username;

    public Player(String username)
    {
        this.username = username;
    }

    /**
     * Getter method for username
     * @return String represents username of this player
     */
    public String getUsername() {
        return username;
    }
}
