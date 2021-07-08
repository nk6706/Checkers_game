package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostResignGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());


    public PostResignGameRoute() {
        //
        LOG.config("PostResignGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();


        return null;
    }
}
