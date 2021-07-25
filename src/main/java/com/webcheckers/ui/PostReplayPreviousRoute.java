package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostReplayPreviousRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostReplayPreviousRoute.class.getName());

    private final Gson gson;

    public PostReplayPreviousRoute(Gson gson) {
        this.gson = gson;
        //
        LOG.config("PostReplayPreviousRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");

        /**final String queryParam = request.queryParams("gameID");
        if(queryParam == null) {
            response.redirect(WebServer.HOME_URL);
        }
        final int gameID = Integer.parseInt(queryParam);

        Message result = Message.info("false");
        if (this.replayManager.hasPrevious(player.getUsername(), gameID)) {
            result = Message.info("true");
        }*/

        final int replayPosition = httpSession.attribute("replayPosition");
        httpSession.attribute("replayPosition", replayPosition-1);

        return this.gson.toJson(Message.info("true"));
    }
}
