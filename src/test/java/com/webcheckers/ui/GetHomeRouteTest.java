package com.webcheckers.ui;


import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class GetHomeRouteTest {
    private GetHomeRoute CuT;
    private Session session;
    private Request request;
    private Response response;
    private PlayerLobby playerLobby;
    private Player player;
    private TemplateEngine templateEngine;
    private TemplateEngine engine;
    static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    @BeforeEach void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        playerLobby = mock(PlayerLobby.class);
        player = mock(Player.class);
        templateEngine = mock(TemplateEngine.class);

        CuT = new GetHomeRoute(playerLobby, templateEngine);
    }

    @Test
    public void testPage(){
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        try {
            CuT.handle(request, response);
        } catch (Exception e) {
            assertFalse(false);
        }

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewName("home.ftl");
        testHelper.assertViewModelAttribute("totalPlayers", 0);
        testHelper.assertViewModelAttribute("currentUser", null);

    }
}
