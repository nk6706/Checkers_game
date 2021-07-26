package com.webcheckers.model;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckerBoardTest {

    /** Component-under-Test CuT */
    private CheckerBoard CuT;
    private Move move;
    private Position startPosition;
    private Position endPosition;
    private Position position;

    @BeforeEach
    public void setUp() {
        this.CuT = new CheckerBoard();

    }

    @Test
    public void getBoardRed() {
        //Set up
        String expected = "[ ] [W] [ ] [W] [ ] [W] [ ] [W] \n[W] [ ] [W] [ ] [W] [ ] [W] [ ] \n[ ] [W] [ ] [W] [ ] [W] [ ] [W] \n" +
                "[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] \n[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] \n[R] [ ] [R] [ ] [R] [ ] [R] [ ] \n" +
                "[ ] [R] [ ] [R] [ ] [R] [ ] [R] \n[R] [ ] [R] [ ] [R] [ ] [R] [ ] \n";

        //Invoke
        CheckerPiece[][] board = CuT.getBoard();
        String actual = CuT.toString();

        //Analyze
        assertEquals(expected, actual);
    }

    @Test
    public void getBoardWhite() {
        //Set up
        String expected = "[ ] [R] [ ] [R] [ ] [R] [ ] [R] \n[R] [ ] [R] [ ] [R] [ ] [R] [ ] \n[ ] [R] [ ] [R] [ ] [R] [ ] [R] \n" +
                "[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] \n[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] \n[W] [ ] [W] [ ] [W] [ ] [W] [ ] \n" +
                "[ ] [W] [ ] [W] [ ] [W] [ ] [W] \n[W] [ ] [W] [ ] [W] [ ] [W] [ ] \n";

        //Invoke
        CheckerPiece[][] board = CuT.getFlippedBoard();
        String actual = "";

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if (board[i][j] == null) {
                    actual += "[ ] ";
                } else {
                    actual += "[" + board[i][j].toString() + "] ";
                }
            }
            actual += "\n";
        }

        //Analyze
        assertEquals(expected, actual);
    }

    @Test
    public void testCopyConstructor(){

        String noFlipExpected = "[ ] [W] [ ] [W] [ ] [W] [ ] [W] \n" +
                "[W] [ ] [W] [ ] [W] [ ] [W] [ ] \n" +
                "[ ] [W] [ ] [W] [ ] [W] [ ] [W] \n" +
                "[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] \n" +
                "[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] \n" +
                "[R] [ ] [R] [ ] [R] [ ] [R] [ ] \n" +
                "[ ] [R] [ ] [R] [ ] [R] [ ] [R] \n" +
                "[R] [ ] [R] [ ] [R] [ ] [R] [ ] \n";

        CuT = new CheckerBoard(CuT, false);
        assertEquals(noFlipExpected, CuT.toString());
    }

    @Test
    public void testFlippedBoard(){

        String flipExpected = "[ ] [R] [ ] [R] [ ] [R] [ ] [R] \n" +
                "[R] [ ] [R] [ ] [R] [ ] [R] [ ] \n" +
                "[ ] [R] [ ] [R] [ ] [R] [ ] [R] \n" +
                "[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] \n" +
                "[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] \n" +
                "[W] [ ] [W] [ ] [W] [ ] [W] [ ] \n" +
                "[ ] [W] [ ] [W] [ ] [W] [ ] [W] \n" +
                "[W] [ ] [W] [ ] [W] [ ] [W] [ ] \n";

        CuT = new CheckerBoard(CuT, true);
        assertEquals(flipExpected, CuT.toString());

    }

    @Test
    public void testBackRowMove(){
        startPosition = new Position(5,0);
        endPosition = new Position(6,1);
        move = new Move(startPosition,endPosition);

        Message message = Message.error("A single piece can only move forward");
        Assertions.assertEquals(message.getText(),CuT.isValidMove(move,true).getText() );
    }

    @Test
    public void testSameRowMove(){
        startPosition = new Position(5,0);
        endPosition = new Position(5,1);
        move = new Move(startPosition,endPosition);

        Message message = Message.error("Single piece must change a row!");
        Assertions.assertEquals(message.getText(),CuT.isValidMove(move,true).getText() );
    }

    @Test
    public void testWrongTurnMove(){
        startPosition = new Position(5,0);
        endPosition = new Position(4,1);
        move = new Move(startPosition,endPosition);

        Message message = Message.error("Can only move once in a turn!");
        Assertions.assertEquals(message.getText(),CuT.isValidMove(move,false).getText() );
    }

    @Test
    public void testStraightMove(){
        startPosition = new Position(5,0);
        endPosition = new Position(4,0);
        move = new Move(startPosition,endPosition);

        Message message = Message.error("You can only move a single cell in either direction!");
        Assertions.assertEquals(message.getText(),CuT.isValidMove(move,true).getText() );
    }

    @Test
    public void testValidMove(){
        startPosition = new Position(5,0);
        endPosition = new Position(4,1);
        move = new Move(startPosition,endPosition);

        Message message = Message.info("");
        Assertions.assertEquals(message.getText(),CuT.isValidMove(move,true).getText() );
    }

    @Test
    public void test2CellsRightMove(){
        startPosition = new Position(5,0);
        endPosition = new Position(4,2);
        move = new Move(startPosition,endPosition);

        Message message = Message.error("You can only move a single cell in either direction!");
        Assertions.assertEquals(message.getText(),CuT.isValidMove(move,true).getText() );
    }

    @Test
    public void test2RowsRightMove(){
        startPosition = new Position(5,0);
        endPosition = new Position(3,1);
        move = new Move(startPosition,endPosition);

        Message message = Message.error("Moved more than one row forward without jumping");
        Assertions.assertEquals(message.getText(),CuT.isValidMove(move,true).getText() );
    }

    @Test
    public void testMovePiece(){
        startPosition = new Position(5,0);
        endPosition = new Position(4,1);

        String expected ="[ ] [W] [ ] [W] [ ] [W] [ ] [W] \n" +
                "[W] [ ] [W] [ ] [W] [ ] [W] [ ] \n" +
                "[ ] [W] [ ] [W] [ ] [W] [ ] [W] \n" +
                "[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] \n" +
                "[ ] [R] [ ] [ ] [ ] [ ] [ ] [ ] \n" +
                "[ ] [ ] [R] [ ] [R] [ ] [R] [ ] \n" +
                "[ ] [R] [ ] [R] [ ] [R] [ ] [R] \n" +
                "[R] [ ] [R] [ ] [R] [ ] [R] [ ] \n";

        CuT.movePiece(startPosition,endPosition);

        Assertions.assertEquals(expected,CuT.toString());
    }

    @Test
    public void testSingleMove(){

        Assertions.assertFalse(CuT.isJumpAvailable(CheckerPiece.Color.RED));
    }

    @Test
    public void testJumpAvailableTrue(){
        startPosition = new Position(2,1);
        endPosition = new Position(4,1);
        CuT.movePiece(startPosition,endPosition);
        Assertions.assertTrue(CuT.isJumpAvailable(CheckerPiece.Color.RED));
    }

    @Test
    public void testWasSingleMove(){
        CheckerBoard checkerBoard= new CheckerBoard();
        startPosition = new Position(2,1);
        endPosition = new Position(3,2);
        CuT.movePiece(startPosition,endPosition);

        Assertions.assertTrue(CuT.wasSingleMove(checkerBoard));
    }

    @Test
    public void testGetRedPieces(){
        CuT = new CheckerBoard();
        Assertions.assertEquals(12, CuT.getRedPieces());
    }
    @Test
    public void testGetWhitePieces(){
        CuT = new CheckerBoard();
        Assertions.assertEquals(12, CuT.getWhitePieces());
    }

    @Test
    public void testWasJumpAvailableTakenAsSingle(){
        CheckerBoard checkerBoard = new CheckerBoard();

    }

    @Test
    public void testSingleMoveBoardNull(){
        CheckerBoard board = null; //this looks weird, but if CuT is null cannot invoke wasSingleMove
        Assertions.assertEquals(false, CuT.wasSingleMove(board));
    }


}
