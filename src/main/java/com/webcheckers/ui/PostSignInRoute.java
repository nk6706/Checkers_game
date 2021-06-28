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

        // Check that username is at least one alphanumeric character and contains no symbol
        if (username.length() > 0 && username.chars().allMatch( c -> Character.isLetterOrDigit(c) || Character.isWhitespace(c)) && username.chars().anyMatch(Character::isLetterOrDigit)) {
            if (!this.playerLobby.hasPlayer(username)) { // Check that username does not already exist
                final Player player = new Player(username);
                httpSession.attribute("player", player);
                playerLobby.addPlayer(player);
                response.redirect(WebServer.HOME_URL);
            }
            else {
                response.redirect(WebServer.SIGN_IN_URL + "?error=Name taken");
            }
        } else {
            response.redirect(WebServer.SIGN_IN_URL + "?error=Username must contain at least one alphanumeric character and only alphanumeric characters or spaces");
        }

        return null;
    }
}
