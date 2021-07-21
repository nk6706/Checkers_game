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

public class PostCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final GameManager gameManager;
    private final Gson gson;

    public PostCheckTurnRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
        //
        LOG.config("PostCheckTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");

        int gameID = -1;
        if(player.getGameID() != -1) {
            gameID = player.getGameID();
        } else {
            final String queryParam = request.queryParams("gameID");
            if(queryParam != null) {
                gameID = Integer.parseInt(queryParam);
            } else {
                response.redirect(WebServer.HOME_URL);
            }
        }


        CheckersGame game = gameManager.getGame(gameID);
        Message result = Message.info("false");
        if (game.isPlayersTurn(player)) {
            result = Message.info("true");
        }

        return this.gson.toJson(result);
    }
}
