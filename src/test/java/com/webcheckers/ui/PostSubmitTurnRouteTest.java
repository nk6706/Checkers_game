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
public class PostSubmitTurnRouteTest {

    /** Component-under-Test CuT */
    private PostSubmitTurnRoute CuT;

    /** Mock objects */
    private Request request;
    private Session session;
    private Player player;
    private Response response;
    private GameManager gameManager;
    private CheckersGame game;

    /** Friendly */
    private final Gson gson = new Gson();

    private static final int gameID = 7;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        player = mock(Player.class);
        when(request.attribute("player")).thenReturn(player);
        response = mock(Response.class);
        gameManager = mock(GameManager.class);
        game = mock(CheckersGame.class);
        when(player.getGameID()).thenReturn(gameID);

        CuT = new PostSubmitTurnRoute(gameManager, gson);
    }

    @Test
    public void handleValidTurn() throws Exception {
        when(gameManager.isValidTurn(game.getId())).thenReturn(Message.info(""));
        when(session.attribute("player")).thenReturn(mock(Player.class));

        String json = (String) CuT.handle(request, response);
        final Message result = gson.fromJson(json, Message.class);

        assertEquals(Message.Type.INFO, result.getType());
        assertEquals("", result.getText());
    }

    @Test
    public void handleInvalidTurn() throws Exception {

        when(gameManager.isValidTurn(game.getId())).thenReturn(Message.info(""));
        when(session.attribute("player")).thenReturn(mock(Player.class));
        when(gameManager.isValidTurn(any(Integer.class))).thenReturn(Message.error("Test Error"));

        String json = (String) CuT.handle(request, response);
        final Message result = gson.fromJson(json, Message.class);

        assertNotEquals(Message.Type.INFO, result.getType());
        assertNotEquals("", result.getText());
    }
}
