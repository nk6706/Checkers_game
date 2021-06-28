package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());

    static final String VIEW_NAME = "game.ftl";

    private final PlayerLobby playerLobby;
    private final GameManager gameManager;

    public PostGameRoute(final PlayerLobby playerLobby, final GameManager gameManager) {
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
        //
        LOG.config("PostGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");

        String opponentName = request.queryParams("opponent");
        if (this.playerLobby.hasPlayer(opponentName)) {
            Player opponent = this.playerLobby.getPlayer(opponentName);
            if(!opponent.inGame()) {
                CheckersGame game = this.gameManager.newGame(player, opponent);
                response.redirect(WebServer.GAME_URL);
            } else {
                // Opponent in-game error
                response.redirect(WebServer.HOME_URL + "?error=Opponent in game already");
            }
        } else {
            // Invalid opponent error
            response.redirect(WebServer.HOME_URL + "?error=Opponent could not be found");
        }

        return null;
    }
}
