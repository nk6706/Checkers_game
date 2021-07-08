package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final GameManager gameManager;

    public PostCheckTurnRoute(GameManager gameManager) {
        this.gameManager = gameManager;
        //
        LOG.config("PostCheckTurnRoute is initialized.");
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

        result = Message.info("true");
        return result;
    }
}
