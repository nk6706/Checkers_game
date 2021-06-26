package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.*;

public class PlayerLobby {

    private HashMap<String, Player> playerList;

    public PlayerLobby(){
        this.playerList = new HashMap<String, Player>();
    }

    /**
     * Add/create a new player
     * @param username the username of the new player
     */
    public void addPlayer(String username) { playerList.put(username, new Player(username)); }

    /**
     * Retrieve the current number of players
     * @return int representing how many players are signed in
     */
    public int getNumberOfPlayers() {
        return playerList.size();
    }

    /**
     * Check if the given player (username) exists in the lobby
     * @param username the name of the player to check for
     * @return true if the player is present, false otherwise
     */
    public boolean hasPlayer(String username) {
        return this.playerList.containsKey(username);
    }

    public HashMap<String, Player> getPlayerList(){
        return playerList;
    }


}
