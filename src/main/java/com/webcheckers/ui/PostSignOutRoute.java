package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.*;

import java.util.logging.Logger;

public class PostSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());

    private final PlayerLobby playerLobby;

    public PostSignOutRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
        LOG.config("PostSignOutRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player playerName = (Player) httpSession.attribute("player");

        this.playerLobby.playerSignOut(playerName.getUsername());

        httpSession.removeAttribute("player");

        response.redirect(WebServer.HOME_URL);

        return "";
    }
}
