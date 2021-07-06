package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CheckersGameTest {
    private Player redPlayer;
    private Player whitePlayer;

    private static final int TEST_ID = 7001;

    @BeforeEach
    void setUp() {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
    }

    @Test
    public void testGetID() {
        CheckersGame CuT = new CheckersGame(TEST_ID, redPlayer, whitePlayer);
        assertEquals(TEST_ID, CuT.getId());
    }

    @Test
    public void testGetRedPlayer() {
        CheckersGame CuT = new CheckersGame(TEST_ID, redPlayer, whitePlayer);
        assertEquals(CuT.getRedPlayer(), redPlayer);
    }

    @Test
    public void testGetWhitePlayer() {
        CheckersGame CuT = new CheckersGame(TEST_ID, redPlayer, whitePlayer);
        assertEquals(CuT.getWhitePlayer(), whitePlayer);
    }

    @Test
    public void testIsRedPlayerTrue() {
        CheckersGame CuT = new CheckersGame(1, redPlayer, whitePlayer);
        assertTrue(CuT.isRedPlayer(redPlayer));
    }

    @Test
    public void testIsRedPlayerFalse() {
        CheckersGame CuT = new CheckersGame(1, redPlayer, whitePlayer);
        assertFalse(CuT.isRedPlayer(whitePlayer));
    }
}
