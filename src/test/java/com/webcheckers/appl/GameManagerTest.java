package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@Tag("Application-tier")
class GameManagerTest {

    private static final int EXPECTED_ID = 1;
    private Player playerOne;
    private Player playerTwo;

    @BeforeEach
    public void setup(){
        playerOne = mock(Player.class);
        playerTwo = mock(Player.class);

    }

    @Test
    void newGame() {
        GameManager testManager = new GameManager();
        CheckersGame testGame = testManager.newGame(playerOne, playerTwo);
        assertEquals(EXPECTED_ID, testGame.getId());
        assertEquals(playerOne, testGame.getRedPlayer());
        assertEquals(playerTwo, testGame.getWhitePlayer());
    }

    @Test
    void getGame() {
        GameManager testManager = new GameManager();
        assertEquals(EXPECTED_ID, testManager.newGame(playerOne, playerTwo).getId());

    }
}