package com.webcheckers.ui;


import com.webcheckers.appl.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostResignGameRouteTest {

    /** Component-Under-Test CuT */
    private PostResignGameRoute CuT;

    /** Mock Objects */
    private Request request;
    private Session session;
    private Response response;
    private GameManager gameManager;


    @BeforeEach
    void setUp() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

    }

    @Test
    void handle() {

    }
}