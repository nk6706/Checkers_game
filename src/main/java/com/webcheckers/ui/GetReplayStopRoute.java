package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetReplayStopRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetReplayStopRoute.class.getName());

    private final GameManager gameManager;

    public GetReplayStopRoute(GameManager gameManager) {
        this.gameManager = gameManager;
        //
        LOG.config("GetReplayStopRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");

        this.gameManager.removeReplayPosition(player.getUsername());
        response.redirect(WebServer.HOME_URL);

        return null;
    }
}
