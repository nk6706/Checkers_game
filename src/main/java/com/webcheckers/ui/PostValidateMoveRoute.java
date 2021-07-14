package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.TurnManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final GameManager gameManager;
    private final Gson gson;

    public PostValidateMoveRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
        //
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");
        final int gameID = player.getGameID();
        final Move move = gson.fromJson(request.queryParams("actionData"), Move.class);

        final Message msg = this.gameManager.isValidMove(gameID, move);
        if (msg.getText().equals("")) {
            this.gameManager.makeMove(gameID, move);
        }

        return gson.toJson(msg);
    }
}