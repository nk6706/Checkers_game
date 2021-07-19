package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetSpectatorGameRouteTest {
    private GetSpectatorGameRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameManager gameManager;
    private CheckersGame gameTest;

    private Gson gson = new Gson();

    static final String TITLE = "Game";

    @BeforeEach
    public void setup(){

        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when (request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        gameManager = mock(GameManager.class);
        gameTest = mock(CheckersGame.class);

        CuT = new GetSpectatorGameRoute(templateEngine, gameManager, playerLobby, gson);
    }

    @Test
    public void makesPage() {

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player testPlayer = mock(Player.class);
        when(session.attribute("player")).thenReturn(testPlayer);
        when(testPlayer.getGameID()).thenReturn(1);
        when(request.queryParams("gameID")).thenReturn("1");
        when(gameManager.getGame(1)).thenReturn(new CheckersGame(1, testPlayer, mock(Player.class)));

        try {
            CuT.handle(request, response);
        } catch (Exception e) {
            assertFalse(false);
        }

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, TITLE);
        testHelper.assertViewName(CuT.VIEW_NAME);
    }

}