package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostValidateMoveRouteTest {

    /** Component-under-Test CuT */
    private PostValidateMoveRoute CuT;

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

        CuT = new PostValidateMoveRoute(gameManager, gson);
    }

    @Test
    public void handleValidMove() throws Exception {
        when(session.attribute("player")).thenReturn(mock(Player.class));
        Move m = new Move(new Position(1, 1), new Position(2, 2));
        when(request.queryParams("actionData")).thenReturn(gson.toJson(m));
        when(gameManager.isValidMove(any(Integer.class), any(Move.class))).thenReturn(Message.info(""));

        String json = (String) CuT.handle(request, response);
        final Message result = gson.fromJson(json, Message.class);

        assertEquals(Message.Type.INFO, result.getType());
        assertEquals("", result.getText());
    }
}
