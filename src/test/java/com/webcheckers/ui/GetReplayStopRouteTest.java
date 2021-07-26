package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class GetReplayStopRouteTest {

    /** Component-Under-Test */
    private GetReplayStopRoute CuT;

    /** Mock objects */
    private Request request;
    private Session session;
    private Response response;
    private GameManager gameManager;
    private Player player;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameManager = mock(GameManager.class);
        player = mock(Player.class);
        when(player.getUsername()).thenReturn("player1");
        when(this.gameManager.removeReplayPosition(player.getUsername())).thenReturn(0);
        when(session.attribute("player")).thenReturn(player);

        CuT = new GetReplayStopRoute(gameManager);
    }

    @Test
    public void testHandle() throws Exception {
        CuT.handle(request, response);

        verify(response, times(1)).redirect(WebServer.HOME_URL);
        verify(this.gameManager, times(1)).removeReplayPosition("player1");
    }


}
