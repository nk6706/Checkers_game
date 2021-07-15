package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
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
    private GameManager CuT;

    @BeforeEach
    public void setup(){
        playerOne = mock(Player.class);
        playerTwo = mock(Player.class);
        CuT = new GameManager();

    }

    @Test
    void newGame() {
        CheckersGame testGame = CuT.newGame(playerOne, playerTwo);
        assertEquals(EXPECTED_ID, testGame.getId());
        assertEquals(playerOne, testGame.getRedPlayer());
        assertEquals(playerTwo, testGame.getWhitePlayer());
    }

    @Test
    void getGameID() {
        assertEquals(EXPECTED_ID, CuT.newGame(playerOne, playerTwo).getId());
    }

    @Test
    void getGameId(){
        CheckersGame game = CuT.newGame(playerOne, playerTwo);
        assertEquals(game,CuT.getGame(1));
    }

    @Test
    void isValidMove(){
        CheckersGame testGame = CuT.newGame(playerOne, playerTwo);
        Position startPosition = new Position(5,0);
        Position endPosition = new Position(4,1);
        Move move = new Move(startPosition,endPosition);
        Message message = Message.info("");
        assertEquals(message.getText(), CuT.isValidMove(testGame.getId(),move).getText());
    }

    @Test
    void isValidTurn(){
        CheckersGame testGame = CuT.newGame(playerOne, playerTwo);
        Position startPosition = new Position(5,0);
        Position endPosition = new Position(4,1);
        Move move = new Move(startPosition,endPosition);
        Message message = Message.info("");
        assertEquals(message.getText(), CuT.isValidTurn(testGame.getId()).getText());
    }

    @Test
    void undoMove(){
        CheckersGame testGame = CuT.newGame(playerOne,playerTwo);
        Message message = Message.info("No moves have been made yet");
        assertEquals(message.getText(),CuT.undoMove(testGame.getId()).getText());
    }

    @Test
    void makeMove(){
        CheckersGame testGame = CuT.newGame(playerOne,playerTwo);
        Position startPosition = new Position(5,0);
        Position endPosition = new Position(4,1);
        Move move = new Move(startPosition,endPosition);
        CuT.makeMove(testGame.getId(), move);
        Message message = Message.info("");
        assertEquals(message.getText(), CuT.isValidTurn(testGame.getId()).getText());

    }

    @Test
    void submitTurn(){
        CheckersGame testGame = CuT.newGame(playerOne,playerTwo);
        Position startPosition = new Position(5,0);
        Position endPosition = new Position(4,1);
        Move move = new Move(startPosition,endPosition);
        CuT.makeMove(testGame.getId(), move);
        CuT.submitTurn(testGame.getId());
        Message message = Message.info("");
        assertEquals(message.getText(), CuT.isValidTurn(testGame.getId()).getText());

    }
}