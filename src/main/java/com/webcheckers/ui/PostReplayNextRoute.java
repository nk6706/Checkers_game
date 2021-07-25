package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostReplayNextRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostReplayNextRoute.class.getName());

    private final GameManager gameManager;
    private final Gson gson;

    public PostReplayNextRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
        //
        LOG.config("PostReplayNextRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");

        this.gameManager.incrementReplayPosition(player.getUsername());

        return this.gson.toJson(Message.info("true"));
    }
}
