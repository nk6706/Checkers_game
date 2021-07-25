package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetReplayStopRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetReplayStopRoute.class.getName());


    public GetReplayStopRoute() {
        LOG.config("GetReplayStopRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {


        return null;
    }
}
