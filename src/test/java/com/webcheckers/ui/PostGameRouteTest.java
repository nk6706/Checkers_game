package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.mockito.Mockito.*;

public class PostGameRouteTest {

    private PostGameRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private PlayerLobby playerLobby;
    private GameManager gameManager;

    static final String TEST_NAME_A = "AvailablePlayer";
    static final String TEST_NAME_B = "UnavailablePlayer";
    static final String TEST_NAME_C = "NotAPlayer";
    static final String TEST_NAME_YOU = "PlayingPlayer";
    static final String PLAYER_NOT_AVAILABLE_URL = WebServer.HOME_URL + "?error=Opponent in game already";
    static final String PLAYER_NOT_FOUND_URL = WebServer.HOME_URL + "?error=Opponent could not be found";

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        when(request.session()).thenReturn(session);
        playerLobby = mock(PlayerLobby.class);
        gameManager = new GameManager();

        when(session.attribute("player")).thenReturn(new Player(TEST_NAME_YOU));
        when(request.queryParams("opponent")).thenReturn(TEST_NAME_A);
        when(playerLobby.getPlayer(TEST_NAME_A)).thenReturn(new Player(TEST_NAME_A));

        CuT = new PostGameRoute(playerLobby, gameManager);
    }

    @Test
    public void playerIsAvailable() throws Exception {
        when(request.queryParams("opponent")).thenReturn(TEST_NAME_A);
        when(playerLobby.playerAvailable(TEST_NAME_A)).thenReturn("available");

        CuT.handle(request, response);

        verify(response).redirect(WebServer.GAME_URL);
    }

    @Test
    public void playerIsInGame() throws Exception {

        when(request.queryParams("opponent")).thenReturn(TEST_NAME_B);
        when(playerLobby.playerAvailable(TEST_NAME_B)).thenReturn("unavailable");

        CuT.handle(request, response);

        verify(response).redirect(PLAYER_NOT_AVAILABLE_URL);
    }

    @Test
    public void playerCouldNotBeFound() throws Exception {
        when(request.queryParams("opponent")).thenReturn(TEST_NAME_C);
        when(playerLobby.playerAvailable(TEST_NAME_C)).thenReturn("not found");

        CuT.handle(request, response);

        verify(response).redirect(PLAYER_NOT_FOUND_URL);
    }
}