package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerBoardTest {

    /** Component-under-Test CuT */
    private CheckerBoard CuT;

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
        CheckerPiece[][] board = CuT.getBoard(true);
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
        CheckerPiece[][] board = CuT.getBoard(false);
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
    public void validMove() {
        //Set up
        Position start = new Position(0, 5);
        Position end = new Position(1, 4);

        //Invoke
        Boolean move = CuT.move(CheckerPiece.Color.RED, start, end);

        //Analyze
        assertTrue(move);
    }

    @Test
    public void occupiedSpaceMove() {
        //Set up
        Position start = new Position(0, 7);
        Position end = new Position(1, 6);

        //Invoke
        Boolean move = CuT.move(CheckerPiece.Color.RED, start, end);

        //Analyze
        assertFalse(move);
    }

    @Test
    public void sameSpaceMove() {
        //Set up
        Position start = new Position(0, 7);
        Position end = new Position(0, 7);

        //Invoke
        Boolean move = CuT.move(CheckerPiece.Color.RED, start, end);

        //Analyze
        assertFalse(move);
    }

    @Test
    public void whitespaceMove() {
        //Set up
        Position start = new Position(0, 7);
        Position end = new Position(0, 6);

        //Invoke
        Boolean move = CuT.move(CheckerPiece.Color.RED, start, end);

        //Analyze
        assertFalse(move);
    }

    @Test
    public void noPieceMove() {
        //Set up
        Position start = new Position(0, 6);
        Position end = new Position(1, 5);

        //Invoke
        Boolean move = CuT.move(CheckerPiece.Color.RED, start, end);

        //Analyze
        assertFalse(move);
    }
}
