package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CheckersGameTest {

    private CheckersGame CuT;

    private Player redPlayer;
    private Player whitePlayer;

    @BeforeEach
    void setUp() {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);

        CuT = new CheckersGame(1, redPlayer, whitePlayer);
    }
}