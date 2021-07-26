package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class GetSpectatorExitGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final PlayerLobby playerLobby;

    public GetSpectatorExitGameRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;

        LOG.fine("GetSpectatorExitGameRoute is initiated.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");

        player.setGameID(-1);

        response.redirect(WebServer.HOME_URL);

        return null;
    }
}
