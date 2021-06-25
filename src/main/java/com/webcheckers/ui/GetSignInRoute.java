package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class GetSignInRoute implements Route {

    static final String TITLE_ATTR = "title";
    static final String VIEW_NAME = "sign-in.ftl";
    static final Message SIGN_IN_MESSAGE = Message.info("Sign in with a unique user name.");






    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();


        return null;
    }
}
