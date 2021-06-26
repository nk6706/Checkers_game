package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.*;

public class PlayerLobby {

    private HashMap<String, Player> playerList;

    public PlayerLobby(){
        this.playerList = new HashMap<String, Player>();
    }

    public void addPlayer(Player player){
        playerList.put(player.getUsername(), player);
    }

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
