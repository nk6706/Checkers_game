package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    static final String USERNAME_PARAM = "username";
    static final String VIEW_NAME = "signin.ftl";

    /** Sign-in error messages */
    public static final String NAME_TAKEN_ERR = "Name taken";
    public static final String INVALID_NAME_ERR = "Username must contain at least one alphanumeric character and only alphanumeric characters or spaces";

    private final PlayerLobby playerLobby;

    public PostSignInRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
        //
        LOG.config("PostSignInRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final String username = request.queryParams(USERNAME_PARAM);
        final Session httpSession = request.session();

        String result = this.playerLobby.signin(username);
        if (result.equals(WebServer.HOME_URL)) {
            httpSession.attribute("player", this.playerLobby.getPlayer(username));
        }
        response.redirect(result);

        return null;
    }
}
