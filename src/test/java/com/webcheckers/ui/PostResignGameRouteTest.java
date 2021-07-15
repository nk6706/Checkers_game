package com.webcheckers.ui;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Player;
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
    private Gson gson;
    private Player player1;
    private Player player2;


    @BeforeEach
    void setUp() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
//        gameManager = new GameManager();
        gameManager = mock(GameManager.class);
        player1 = mock(Player.class);
//        player1.setGameID(123);
        player2 = mock(Player.class);
//        player2.setGameID(123);
        gameManager.newGame(player1, player2);

        gson = new Gson();

        CuT = new PostResignGameRoute(gameManager, gson);
    }

    @Test
    void handle() {
        assertNotEquals(gameManager, null);

        when(session.attribute("player")).thenReturn(player1);
//        when(gameManager.getGame(1).isGameOver()).thenReturn(true);
        Object jsonObject = null;

        try {
            jsonObject = CuT.handle(request, response);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertNotEquals(CuT, null);
    }
}