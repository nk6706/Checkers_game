package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.ui.PostSignInRoute;
import com.webcheckers.ui.WebServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Application-tier")
public class PlayerLobbyTest {

    /** Component-under-Test CuT */
    private PlayerLobby CuT;

    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();
    }

    @Test
    public void test_player_count() {
        assertSame(0, CuT.getNumberOfPlayers());
        CuT.addPlayer(mock(Player.class));
        assertSame(1, CuT.getNumberOfPlayers());
    }

    @Test
    public void test_has_player() {
        final Player player = mock(Player.class);
        when(player.getUsername()).thenReturn("my name");
        assertFalse(CuT.hasPlayer(player.getUsername()));
        CuT.addPlayer(player);
        assertTrue(CuT.hasPlayer(player.getUsername()));
    }

    @Test
    public void test_get_player() {
        final Player player = mock(Player.class);
        when(player.getUsername()).thenReturn("my name");
        assertNull(CuT.getPlayer(player.getUsername()));
        CuT.addPlayer(player);
        assertSame(player, CuT.getPlayer(player.getUsername()));
    }

    @Test
    public void signin_name_taken() {
        final Player player = mock(Player.class);
        when(player.getUsername()).thenReturn("my name");
        CuT.addPlayer(player);
        assertEquals(WebServer.SIGN_IN_URL + "?error=" + PostSignInRoute.NAME_TAKEN_ERR, CuT.signin(player.getUsername()));
    }

    @Test
    public void signin_invalid_name() {
        final String expected = WebServer.SIGN_IN_URL + "?error=" + PostSignInRoute.INVALID_NAME_ERR;
        assertEquals(expected, CuT.signin(""));
        assertEquals(expected, CuT.signin("Hello world!"));
    }

    @Test
    public void sigin_valid_name () {
        assertEquals(WebServer.HOME_URL, CuT.signin("hello"));
    }

    @Test
    public void testGetPlayerList() {
        final Player player = mock(Player.class);
        when(player.getUsername()).thenReturn("my name");

        assertEquals(CuT.getPlayerList().size(), 0);
        CuT.addPlayer(player);
        assertEquals(CuT.getPlayerList().size(), 1);
        assertEquals(CuT.getPlayerList().remove(0), "my name");
    }

    @Test
    public void playerAvailableNotAvailable() {
        final Player player = mock(Player.class);
        when(player.getUsername()).thenReturn("name");
        when(player.inGame()).thenReturn(true);
        CuT.addPlayer(player);
        assertEquals(CuT.playerAvailable("name"), "unavailable");
    }

    @Test
    public void playerAvailableAvailable() {
        final Player player = mock(Player.class);
        when(player.getUsername()).thenReturn("name");
        when(player.inGame()).thenReturn(false);
        CuT.addPlayer(player);
        assertEquals(CuT.playerAvailable("name"), "available");
    }

    @Test
    public void playerAvailableDNE() {
        assertEquals(CuT.playerAvailable("george"), "not found");
    }

    @Test
    public void signoutTest() {
        final Player player = mock(Player.class);
        when(player.getUsername()).thenReturn("my name");
        assertEquals(CuT.getPlayerList().size(), 0);
        CuT.addPlayer(player);
        assertEquals(CuT.getPlayerList().size(), 1);
        CuT.playerSignOut("my name");
        assertEquals(CuT.getPlayerList().size(), 0);
    }

}
