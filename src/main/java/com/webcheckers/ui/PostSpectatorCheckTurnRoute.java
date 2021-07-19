package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostSpectatorCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final GameManager gameManager;
    private final Gson gson;

    public PostSpectatorCheckTurnRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;

        LOG.fine("PostSpectatorCheckTurnRoute is initiated.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");

        CheckersGame game = gameManager.getGame(player.getGameID());
        Message result = Message.info("false");
        if (game.isPlayersTurn(player)) {
            result = Message.info("true");
        }

        return this.gson.toJson(result);
    }
}
