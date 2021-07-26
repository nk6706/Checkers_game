package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostSpectatorCheckTurnRouteTest {
    /** Component-under-Test CuT */
    private PostSpectatorCheckTurnRoute CuT;

    /** Necessary tool to test return values of handle */
    private Gson gson = new Gson();

    /** Mock objects */
    private Request request;
    private Session session;
    private Response response;
    private Player player;
    private CheckersGame game;
    private GameManager gameManager;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        this.response = mock(Response.class);
        this.player = mock(Player.class);
        when(session.attribute("player")).thenReturn(player);
        this.game = mock(CheckersGame.class);
        this.gameManager = mock(GameManager.class);
        when(gameManager.getGame(player.getGameID())).thenReturn(game);

        CuT = new PostSpectatorCheckTurnRoute(gameManager, new Gson());
    }

    @Test
    public void newMoveAvailable() throws Exception {
//        when(game.isPlayersTurn(player)).thenReturn(false);

        Object result = CuT.handle(request, response);
        assertTrue(result instanceof String);
        final Message message = gson.fromJson((String) result, Message.class);

        assertEquals(message.getText(), "true");
    }
}