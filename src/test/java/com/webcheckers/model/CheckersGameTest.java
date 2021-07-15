package com.webcheckers.model;

import com.webcheckers.appl.GameManager;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Model-tier")
class CheckersGameTest {

    /** Component-under-Test CuT */
    private CheckersGame CuT;

    /** Mocks */
    private Player redPlayer;
    private Player whitePlayer;

    private static final int TEST_ID = 7001;

    @BeforeEach
    void setUp() {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        CuT = new CheckersGame(TEST_ID, redPlayer, whitePlayer);
    }

    @Test
    public void testGetID() {
        assertEquals(TEST_ID, CuT.getId());
    }

    @Test
    public void testGetRedPlayer() {
        assertEquals(CuT.getRedPlayer(), redPlayer);
    }

    @Test
    public void testGetWhitePlayer() {
        assertEquals(CuT.getWhitePlayer(), whitePlayer);
    }

    @Test
    public void testIsRedPlayerTrue() {
        assertTrue(CuT.isRedPlayer(redPlayer));
    }

    @Test
    public void testIsRedPlayerFalse() {
        assertFalse(CuT.isRedPlayer(whitePlayer));
    }

    @Test
    public void isValidMoveTest() {
        Move move = new Move(new Position(5, 4), new Position(4, 5));

        final Message msg = CuT.isValidMove(move);

        assertEquals(Message.Type.INFO, msg.getType());
        assertEquals("", msg.getText());
    }

    @Test
    public void isNotValidMoveTest() {
        Move move = new Move(new Position(5, 4), new Position(3, 6));

        final Message msg = CuT.isValidMove(move);

        assertEquals(Message.Type.ERROR, msg.getType());
        assertNotEquals("", msg.getText());
    }

    @Test
    public void testIsPlayersTurnTrue() {
        assertTrue(CuT.isPlayersTurn(redPlayer));
    }

    @Test
    public void testIsPlayersTurnFalse() {
        assertFalse(CuT.isPlayersTurn(whitePlayer));
    }

    @Test
    public void testToggleActivePlayer() {
        CuT.toggleActivePlayer();
        assertTrue(CuT.isPlayersTurn(whitePlayer));
    }

    @Test
    public void isValidTurnTest() {
        final Message msg = CuT.isValidTurn();
        assertEquals(Message.Type.INFO, msg.getType());
        assertEquals("", msg.getText());
    }

    @Test
    public void testNewTurn() {
        CuT.makeMove(new Move(new Position(5,4), new Position(4,5)));
        GameManager mockManager = mock(GameManager.class);
        //mockManager.submitTurn(CuT.getId());

    }

    @Test
    public void undoMoveNoMoveTest(){
        final Message msg = CuT.undoMove();
        assertEquals(msg.getType(), Message.Type.ERROR);
    }

    @Test
    public void undoMoveWithMoveTest(){
        Move move = new Move(new Position(5,4), new Position(4,5));
        CuT.makeMove(move);
        final Message msg = CuT.undoMove();

        assertEquals(msg.getType(), Message.Type.INFO);
    }

    @Test
    public void flippedBoardTest(){
        assertNotNull(CuT.getFlippedBoard());
    }

    @Test
    public void normalBoardTest(){
        assertNotNull(CuT.getBoard());
    }

}
