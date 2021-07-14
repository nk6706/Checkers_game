package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

}
