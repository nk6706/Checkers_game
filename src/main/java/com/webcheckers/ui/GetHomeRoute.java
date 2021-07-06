package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {

    static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    final TemplateEngine templateEngine;

    private final PlayerLobby playerLobby;


    /**
    * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
    *
    * @param templateEngine
    *   the HTML template rendering engine
    */
    public GetHomeRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
    * Render the WebCheckers Home page.
    *
    * @param request
    *   the HTTP request
    * @param response
    *   the HTTP response
    *
    * @return
    *   the rendered HTML for the Home page
    */
    @Override
    public Object handle(Request request, Response response) {
        final Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();

        final Player player = httpSession.attribute("player");
        if (player != null && player.inGame()) {
            response.redirect(WebServer.GAME_URL);
        }

        vm.put("title", "Welcome!");

        // display a user message in the Home page
        final String error = request.queryParams("error");
        if (error == null) {
            vm.put("message", WELCOME_MSG);
        } else {
            vm.put("message", Message.error(error));
        }

        if ( player == null  ) {
            vm.put("totalPlayers", this.playerLobby.getNumberOfPlayers());
        } else {
            vm.put("currentUser", player);

            ArrayList<String> usernames = this.playerLobby.getPlayerList();
            usernames.remove(player.getUsername());

            vm.put("playerList", usernames);
        }

        // render the View
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}
