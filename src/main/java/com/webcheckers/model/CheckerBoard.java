package com.webcheckers.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * CheckerBoard is a model-level representation of a checker board used in the game of checkers.
 * An instantiation of CheckerBoard contains an 8x8 array of CheckerPiece(s) which can be
 * modified through the move method.
 */
public class CheckerBoard {
    private CheckerPiece[][] board;

    public CheckerBoard() {
        board = new CheckerPiece[8][8];

        for (int i = 0; i < 3; i++) {
            fillRow(i, CheckerPiece.Color.WHITE);
        }

        for (int i = 5; i < 8; i++) {
            fillRow(i, CheckerPiece.Color.RED);
        }

    }

    /**
     * Getter for board, assuming red player
     * @return CheckerPiece[][] of board
     */
    public CheckerPiece[][] getBoard() {
        CheckerPiece[][] board_copy = new CheckerPiece[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board_copy[i][j] = board[i][j];
            }
        }

        return board_copy;
    }

    /**
     * Getter for board, assuming white player
     * @return CheckerPiece[][] of board
     */
    public CheckerPiece[][] getFlippedBoard(){

        CheckerPiece[][] flippedBoard = new CheckerPiece[8][8];
        for(int i = 0; i < this.board.length; i++) {
            CheckerPiece[] row = this.board[i];
            for(int j = 0; j < row.length; j++) {
                flippedBoard[this.board.length-i-1][this.board.length-j-1] = row[j];
            }
        }
        return flippedBoard;
    }

    /**
     * A helper method used by the CheckerBoard constructor. Places CheckerPiece(s) in their
     * appropriate starting locations according to which row on the board they are being placed.
     *
     * @param row the row on the CheckerBoard that the pieces are being placed
     * @param color the color of the pieces being placed
     */
    private void fillRow(int row, CheckerPiece.Color color) {
        if (row % 2 == 0) {
            for (int j = 1; j < board[row].length; j+=2) {
                board[row][j] = new CheckerPiece(color);
            }
        }
        else {
            for (int j = 0; j < board[row].length; j+=2) {
                board[row][j] = new CheckerPiece(color);
            }
        }
    }

    public CheckerBoard placePiece(CheckerPiece piece, Position startLocation, Position endLocation){
        this.board[endLocation.getX()][endLocation.getY()] = piece;
        this.board[startLocation.getX()][startLocation.getY()] = null;
        return this;
    }

    /**
     * Overrides the default toString method and outputs a string representation of the board.
     * Each space is either [ ], [R], or [W]. Mainly used for the JUnit Tests
     * @return the board as a string.
     */
    @Override
    public String toString() {
        String finalStr = "";
        for(int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if(this.board[i][j] == null) {
                    finalStr += "[ ] ";
                } else {
                    finalStr += "[" + this.board[i][j].toString() + "] ";
                }
            }
            finalStr += "\n";
        }

        return finalStr;
    }

    /**
     * The move method checks the validity of a move on the checkerboard. If the check succeeds,
     * the piece at the starting position will be moved to the end position on the board and the
     * method will return true, returning false instead if the move is invalid.
     *
     * @param playerColor the color that the player moving a piece controls
     * @param start the position of the checker piece being moved
     * @param end the desired final position of the piece being moved
     * @return true if the move was executed, false otherwise
     */
    public boolean move(CheckerPiece.Color playerColor, Position start, Position end) {
        int startY = start.getY();
        int startX = start.getX();
        if (startX < 0 || startX > 7 || startY < 0 || startY > 7) return false;

        // Check if there is a checker piece that can be moved
        if (board[startY][startX] == null) return false;

        // Check if the piece being moved belongs to the player attempting to move it
        if (board[startY][startX].getColor() != playerColor) return false;

        int endY = end.getY();
        int endX = end.getX();
        if (endX < 0 || endX > 7 || endY < 0 || endY > 7) return false;

        // Check if the space being moved to is already occupied
        if (board[endY][endX] != null) return false;

        // Check if square being moved to is white
        if (endY % 2 == 0) {
            if (endX % 2 == 0) return false;
        }
        else {
            if (endX % 2 != 0) return false;
        }

        // Move is valid, so move the piece to its new location and clear its former space
        board[endY][endX] = board[startY][startX];
        board[startY][startX] = null;

        return true;
    }
}
