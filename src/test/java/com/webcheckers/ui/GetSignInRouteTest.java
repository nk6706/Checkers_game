package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GetSignInRouteTest {
    /** Component-under-Test CuT */
    private GetSignInRoute CuT;

    /** Mock objects */
    private Request request;
    private Session session;
    private Response response;
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    static final String TITLE = "Sign-in";

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        templateEngine = mock(TemplateEngine.class);

        CuT = new GetSignInRoute(templateEngine);
    }

    @Test
    public void makesPage() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        try {
            CuT.handle(request, response);
        } catch (Exception e) {
            assertFalse(false);
        }

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, TITLE);
        testHelper.assertViewModelAttribute(GetSignInRoute.POST_URL_ATTR, WebServer.SIGN_IN_URL);
        testHelper.assertViewName(CuT.VIEW_NAME);
    }
}