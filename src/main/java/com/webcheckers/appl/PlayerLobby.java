package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.*;

public class PlayerLobby {

    private HashMap<String, Player> playerList;

    public PlayerLobby(){
        this.playerList = new HashMap<String, Player>();
    }

    /**
     * Add a new player to the lobby
     * @param player the Player object to add
     */
    public void addPlayer(Player player) { playerList.put(player.getUsername(), player); }

    /**
     * Returns the player with the given username, should make sure player exists first
     * @param username the name of the user to get
     * @return the Player object with the given username
     */
    public Player getPlayer(String username) {
        return playerList.get(username);
    }

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
