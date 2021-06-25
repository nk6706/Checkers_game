package com.webcheckers.Model;

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
}
