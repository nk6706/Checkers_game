package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostSignInRoute implements Route {

    static final String USERNAME_PARAM = "myUsername";
    static final String VIEW_NAME = "sign-in.ftl";
    private PlayerLobby playerLobby;

    PostSignInRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final String username = request.queryParams(USERNAME_PARAM);
        Player newPlayer = new Player(username);

        if (playerLobby.getPlayerList().contains(newPlayer)){
            //error message here
        }
        else {
            playerLobby.addPlayer(newPlayer);
        }

        return null;
    }
}
