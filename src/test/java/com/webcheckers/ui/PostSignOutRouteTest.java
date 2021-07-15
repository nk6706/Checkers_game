package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class PostSignOutRouteTest {

    private PostSignOutRoute CuT;
    private Request request;
    private Response response;
    private PlayerLobby playerLobby;
    private Player player;
    private Session session;

    static final String USER = "user";

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        when(request.session()).thenReturn(session);
        playerLobby = mock(PlayerLobby.class);
        player = mock(Player.class);

        CuT = new PostSignOutRoute(playerLobby);
    }

    @Test
    public void testPlayerSignedOut() throws Exception{

        when(session.attribute("player")).thenReturn(new Player(USER));
        CuT.handle(request, response);
        verify(response).redirect(WebServer.HOME_URL);

    }
}
