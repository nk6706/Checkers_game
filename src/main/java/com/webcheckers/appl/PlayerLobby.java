package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.*;

public class PlayerLobby {

    private List<Player> playerList;

    public PlayerLobby(){

    }

    public PlayerLobby(List<Player> playerList){

        this.playerList = playerList;
    }

    public void addPlayer(Player player){
        playerList.add(player);
    }

    public int getNumberOfPlayers() {
        return playerList.size();
    }

    public List<Player> getPlayerList(){
        return playerList;
    }


}
