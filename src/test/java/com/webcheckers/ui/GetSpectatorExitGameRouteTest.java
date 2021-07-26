package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetSpectatorExitGameRouteTest {
    private GetSpectatorExitGameRoute CuT;

    private Session session;
    private Request request;
    private Response response;
    private PlayerLobby playerLobby;
    private Player player;
    private GameManager gameManager;
    private TemplateEngine templateEngine;
    private TemplateEngine engine;
    static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    @BeforeEach
    void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        playerLobby = mock(PlayerLobby.class);
        player = mock(Player.class);
        when(session.attribute("player")).thenReturn(player);
        gameManager = mock(GameManager.class);
        templateEngine = mock(TemplateEngine.class);

        CuT = new GetSpectatorExitGameRoute(playerLobby);
    }

    @Test
    void pageLoads() {
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        try {
            CuT.handle(request, response);
        } catch (Exception e) {
            assertFalse(false);
        }

        verify(response).redirect(WebServer.HOME_URL);

    }
}