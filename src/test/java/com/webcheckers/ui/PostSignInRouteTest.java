package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostSignInRouteTest {

    /** Component-under-Test CuT */
    private PostSignInRoute CuT;

    /** Mock objects */
    private Request request;
    private Session session;
    private Response response;
    private PlayerLobby playerLobby;

    /** URLS needed in some tests */
    final String INVALID_NAME_URL = WebServer.SIGN_IN_URL + "?error=" + PostSignInRoute.INVALID_NAME_ERR;
    final String NAME_TAKEN_URL = WebServer.SIGN_IN_URL + "?error=" + PostSignInRoute.NAME_TAKEN_ERR;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);

        CuT = new PostSignInRoute(playerLobby);
    }

    @Test
    public void bad_name() throws Exception {
        final String username = "!!!";
        when(request.queryParams(PostSignInRoute.USERNAME_PARAM)).thenReturn(username);
        when(playerLobby.signin(username)).thenReturn(INVALID_NAME_URL);

        CuT.handle(request, response);
        assertNull(session.attribute("player"));

        verify(request, times(1)).queryParams(PostSignInRoute.USERNAME_PARAM);
        verify(response, times(1)).redirect(INVALID_NAME_URL);
    }

    @Test
    public void taken_name() throws Exception {
        final String username = "player1";
        when(request.queryParams(PostSignInRoute.USERNAME_PARAM)).thenReturn(username);
        when(playerLobby.signin(username)).thenReturn(NAME_TAKEN_URL);

        CuT.handle(request, response);
        assertNull(session.attribute("player"));

        verify(request, times(1)).queryParams(PostSignInRoute.USERNAME_PARAM);
        verify(response, times(1)).redirect(NAME_TAKEN_URL);
    }

    @Test
    public void good_name() throws Exception {
        final String username = "player1";
        when(request.queryParams(PostSignInRoute.USERNAME_PARAM)).thenReturn(username);
        when(playerLobby.signin(username)).thenReturn(WebServer.HOME_URL);

        CuT.handle(request, response);

        verify(request, times(1)).queryParams(PostSignInRoute.USERNAME_PARAM);
        verify(response, times(1)).redirect(WebServer.HOME_URL);
    }

}
