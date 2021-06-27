package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    // View-and-model attributes
    static final String TITLE_ATTR = "title";
    static final String POST_URL_ATTR = "postSignInURL";
    static final String MESSAGE_ATTR = "message";

    static final String VIEW_NAME = "signin.ftl";

    private final TemplateEngine templateEngine;

    public GetSignInRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetSignInRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();

        vm.put(TITLE_ATTR, "Sign-in");
        vm.put(POST_URL_ATTR, WebServer.SIGN_IN_URL);

        final String error = request.queryParams("error");
        if (error != null) {
            vm.put(MESSAGE_ATTR, Message.error(error));
        }

        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}
