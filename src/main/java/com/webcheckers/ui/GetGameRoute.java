package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    // View-and-model attributes
    static final String TITLE_ATTR = "title";
    static final String MESSAGE_ATTR = "message";

    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;

    public GetGameRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();

        final Session httpSession = request.session();

        vm.put(TITLE_ATTR, "Game");
        vm.put("viewMode", "Player");

        Player player = httpSession.attribute("player");
        String name = player.getUsername();

        vm.put("currentUser", player);
        vm.put("redPlayer", player);
        vm.put("whitePlayer", player);
        vm.put("activeColor", "white");
        vm.put("gameID", "1234");

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}