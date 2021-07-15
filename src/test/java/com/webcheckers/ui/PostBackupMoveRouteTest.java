package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostBackupMoveRouteTest {
    private PostBackupMoveRoute CuT;


    /** Necessary tool to test return values of handle */
    private Gson gson = new Gson();


    /** Mock objects */
    private Request request;
    private Session session;
    private Response response;
    private Player player;
    private CheckersGame game;
    private GameManager gameManager;
    private Move move;

    @BeforeEach
    public void setup() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        this.player = mock(Player.class);
        this.game = mock(CheckersGame.class);
        this.gameManager = mock(GameManager.class);

        CuT = new PostBackupMoveRoute(gameManager,gson);
        when(session.attribute("player")).thenReturn(player);

    }

    @Test
    public void handle() throws Exception{
        Object result = CuT.handle(request,response);
        assertTrue(result instanceof String);
        gameManager.undoMove(player.getGameID());
        assertNull(response);
    }
}
