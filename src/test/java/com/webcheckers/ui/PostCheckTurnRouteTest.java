package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostCheckTurnRouteTest {

    /** Component-under-Test CuT */
    private PostCheckTurnRoute CuT;

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

        CuT = new PostCheckTurnRoute(gameManager, new Gson());
    }

    @Test
    public void isNotTurn() throws Exception {
        when(game.isPlayersTurn(player)).thenReturn(false);

        Object result = CuT.handle(request, response);
        assertTrue(result instanceof String);
        final Message message = gson.fromJson((String) result, Message.class);

        assertEquals(message.getText(), "false");
    }


    @Test
    public void isTurn() throws Exception {
        when(game.isPlayersTurn(player)).thenReturn(true);

        Object result = CuT.handle(request, response);
        assertTrue(result instanceof String);
        final Message message = gson.fromJson((String) result, Message.class);

        assertEquals(message.getText(), "true");
    }

}
