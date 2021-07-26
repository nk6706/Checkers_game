package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostResignGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());

    private final Gson gson;
    private final GameManager gameManager;

    public PostResignGameRoute(GameManager gameManager, Gson gson) {
        this.gson = gson;
        this.gameManager = gameManager;
        //
        LOG.config("PostResignGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");

        gameManager.setGameOver(player.getGameID(), player.getUsername() + " has resigned.");

        return gson.toJson(Message.info("resigned"));
    }
}
