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

public class PostReplayNextRouteTest {

    /** Component-Under-Test */
    private PostReplayNextRoute CuT;

    /** Mock objects */
    private Request request;
    private Session session;
    private Response response;
    private GameManager gameManager;
    private Player player;

    /** Necessary tool to test return values of handle / Friendly */
    private Gson gson = new Gson();

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        gameManager = mock(GameManager.class);
        player = mock(Player.class);
        when(player.getUsername()).thenReturn("player1");
        when(this.gameManager.incrementReplayPosition(player.getUsername())).thenReturn(0);
        when(session.attribute("player")).thenReturn(player);

        CuT = new PostReplayNextRoute(gameManager, gson);
    }

    @Test
    public void testHandle() throws Exception {
        final Object result = CuT.handle(request, response);
        assertTrue(result instanceof String);
        final Message message = gson.fromJson((String) result, Message.class);

        assertEquals(message.getText(), "true");
        verify(this.gameManager, times(1)).incrementReplayPosition("player1");
    }


}
