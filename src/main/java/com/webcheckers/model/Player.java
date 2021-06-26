package com.webcheckers.model;

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
