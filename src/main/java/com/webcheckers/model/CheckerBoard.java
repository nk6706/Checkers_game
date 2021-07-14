package com.webcheckers.model;

import com.webcheckers.util.Message;

/**
 * CheckerBoard is a model-level representation of a checker board used in the game of checkers.
 * An instantiation of CheckerBoard contains an 8x8 array of CheckerPiece(s) which can be
 * modified through the move method.
 */
public class CheckerBoard {
    private CheckerPiece[][] board;

    private CheckerPiece.Color playerColor = CheckerPiece.Color.RED;

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
     * Copy constructor
     * @param board
     */
    CheckerBoard(CheckerBoard board) {
        this.board = board.getBoard();
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
        this.board[endLocation.getRow()][endLocation.getCell()] = piece;
        this.board[startLocation.getRow()][startLocation.getCell()] = null;
        return this;
    }

    public void movePiece(Position start, Position end) {
        this.board[end.getRow()][end.getCell()] = this.board[start.getRow()][start.getCell()];
        this.board[start.getRow()][start.getCell()] = null;
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
        int startY = start.getCell();
        int startX = start.getRow();
        if (startX < 0 || startX > 7 || startY < 0 || startY > 7) return false;

        // Check if there is a checker piece that can be moved
        if (board[startY][startX] == null) return false;

        // Check if the piece being moved belongs to the player attempting to move it
        if (board[startY][startX].getColor() != playerColor) return false;

        int endY = end.getCell();
        int endX = end.getRow();
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

    public Message isValidMove(Move move, boolean first) {
        final Position start = move.getStart();
        final Position end = move.getEnd();
        final int rowDiff = start.getRow() - end.getRow();
        final int cellDiff = start.getCell() - end.getCell();

        final CheckerPiece piece = getPiece(start);

        if ( !piece.isKing() ) { // Single piece move validation
            if (rowDiff < 0 ) { // Tried to move backwards
                return Message.error("A single piece can only move forward");
            } else if ( rowDiff == 0 ) { // Tried to move to tile in same row (invalid)
                return Message.error("Single piece must advance forward");
            } else if ( rowDiff == 1 ) { // Advanced 1 row (could be valid or invalid)
                if ( !first ) {
                    return Message.error("Can only move a single row forward in a turn");
                } else if ( cellDiff == 1 || cellDiff == -1 ) {
                    return Message.info("");
                } else {
                    return Message.error("You can only move a single cell in either direction");
                }
            } else if ( rowDiff == 2 ) { // Possible jump, need further investigation
                if ( cellDiff == 2 ) {
                    if ( hasPiece(new Position(start.getRow()-1, start.getCell()+1)) ) {
                        return Message.info("");
                    }
                } else if ( cellDiff == -2 ) {
                    if ( hasPiece(new Position(start.getRow()-1, start.getCell()-1)) ) {
                        return Message.info("");
                    }
                }
                return Message.error("Moved more than one row forward without jumping");
            } else { // Moved too many rows forward
                return Message.error("Moved more than one row forward without jumping");
            }
        } else { // King move validation

        }

        return Message.error("Unknown error");
    }

    private CheckerPiece getPiece(Position pos) {
        return this.board[pos.getRow()][pos.getCell()];
    }

    private boolean hasPiece(Position pos) {
        return getPiece(pos) != null;
    }

    public Message isValid(Move move){
        int startPosX = move.getStart().getRow();
        int startPosY = move.getStart().getCell();
        int endPosX = move.getEnd().getRow();
        int endPosY = move.getEnd().getCell();

        // is the move within board constraints?
        if (endPosX < 0 || endPosX > 7 || startPosX < 0 || startPosX > 7) return Message.error("Out of bounds!");
        if (endPosY < 0 || endPosY > 7 || startPosY < 0 || startPosY > 7) return Message.error("Out of bounds!");

        if (board[startPosY][startPosX] == null || board[endPosY][endPosX] != null) {
            return Message.error("Moving to non-empty location!");
        }

        CheckerPiece movingPiece = board[startPosY][startPosX];

        if (movingPiece.getColor() != playerColor) return Message.error("This is not your piece!");

        if (movingPiece.getType() == CheckerPiece.Type.SINGLE) {
            // If single space move
            if (endPosY == startPosY - 1) {
                return singleSpaceMove(startPosX, startPosY, endPosX, endPosY, movingPiece);
            } // If jump move
            else if (endPosY == startPosY - 2) {
                return jumpMove(startPosX, startPosY, endPosX, endPosY);
            }
        }

        return Message.info("Move valid!");
    }

    private Message singleSpaceMove(int startPosX, int startPosY, int endPosX, int endPosY, CheckerPiece movingPiece) {
        if (endPosY == startPosY - 1) {
            if (movingPiece.getType() == CheckerPiece.Type.SINGLE) {
                if (endPosX == startPosX - 1) {
                    if (forwardRightJumpCheck(startPosX, startPosY, endPosY)) {
                        return Message.error("Jump required!");
                    }
                    return Message.info("Move valid!");
                }
                else if (endPosX == startPosX + 1) {
                    if (forwardLeftJumpCheck(startPosX, startPosY, endPosY)) {
                        return Message.error("Jump required!");
                    }
                    return Message.info("Move valid!");
                }
            }
            else if (movingPiece.getType() == CheckerPiece.Type.KING) {
                if (endPosX == startPosX - 1) {
                    if (forwardRightJumpCheck(startPosX, startPosY, endPosY) ||
                            backwardLeftJumpCheck(startPosX, startPosY, endPosY) ||
                            backwardRightJumpCheck(startPosX, startPosY, endPosY)) {
                        return Message.error("Jump required!");
                    }
                    return Message.info("Move valid!");
                }
                else if (endPosX == startPosX + 1) {
                    if (forwardLeftJumpCheck(startPosX, startPosY, endPosY) ||
                            backwardLeftJumpCheck(startPosX, startPosY, endPosY) ||
                            backwardRightJumpCheck(startPosX, startPosY, endPosY)) {
                        return Message.error("Jump required!");
                    }
                    return Message.info("Move valid!");
                }
            }
        }
        else if (endPosY == startPosY + 1) {
            if (movingPiece.getType() != CheckerPiece.Type.KING) return Message.info("This piece cannot be moved backward.");

            if (endPosX == startPosX - 1) {
                if (forwardRightJumpCheck(startPosX, startPosY, endPosY) ||
                        forwardLeftJumpCheck(startPosX, startPosY, endPosY) ||
                        backwardRightJumpCheck(startPosX, startPosY, endPosY)) {
                    return Message.error("Jump required!");
                }
                return Message.info("Move valid!");
            }
            else if (endPosX == startPosX + 1) {
                if (forwardRightJumpCheck(startPosX, startPosY, endPosY) ||
                        forwardLeftJumpCheck(startPosX, startPosY, endPosY) ||
                        backwardLeftJumpCheck(startPosX, startPosY, endPosY)) {
                    return Message.error("Jump required!");
                }
                return Message.info("Move valid!");
            }
        }
        return Message.info("Unknown error occurred.");
    }

    private boolean forwardRightJumpCheck(int startPosX, int startPosY, int endPosY) {
        if (!(startPosX + 2 > 7) && !(startPosY - 2 < 0)) {
            // and the space one diagonally to the right contains a piece
            if (board[endPosY][startPosX + 1] != null) {
                // and that piece is not one of the player's
                if (board[endPosY][startPosX + 1].getColor() != playerColor) {
                    // and space 2 diagonally to the right is null
                    if (board[startPosY - 2][startPosX + 2] == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean forwardLeftJumpCheck(int startPosX, int startPosY, int endPosY) {
        if (!(startPosX - 2 < 0) && !(startPosY - 2 < 0)) {
            // and the space one diagonally to the left contains a piece
            if (board[endPosY][startPosX - 1] != null) {
                // and that piece is not one of the player's
                if (board[endPosY][startPosX - 1].getColor() != playerColor) {
                    // and space 2 diagonally to the left is null
                    if (board[startPosY - 2][startPosX - 2] == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean backwardRightJumpCheck(int startPosX, int startPosY, int endPosY) {
        if (!(startPosX + 2 > 7) && !(startPosY + 2 < 0)) {
            if (board[endPosY][startPosX + 1] != null) {
                if (board[endPosY][startPosX + 1].getColor() != playerColor) {
                    if (board[startPosY + 2][startPosX + 2] == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean backwardLeftJumpCheck(int startPosX, int startPosY, int endPosY) {
        if (!(startPosX - 2 < 0) && !(startPosY + 2 < 0)) {
            if (board[endPosY][startPosX - 1] != null) {
                if (board[endPosY][startPosX - 1].getColor() != playerColor) {
                    if (board[startPosY + 2][startPosX - 2] == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Message jumpMove(int startPosX, int startPosY, int endPosX, int endPosY) {
        // If jumping diagonally left
        if (endPosX == startPosX - 2) {
            if (board[startPosY - 1][startPosX - 1] == null) return Message.error("There is no piece to jump!");
            if (board[startPosY - 1][startPosX - 1].getColor() == playerColor) return Message.error("That is not your piece!");

            if (additionalJumpAvailable(endPosX, endPosY)) return Message.error("Multi-jump available and must be used.");
            return Message.info("Move valid!");
        }
        // If jumping diagonally right
        else if (endPosX == startPosX + 2) {
            if (board[startPosY - 1][startPosX + 1] == null) return Message.error("There is no piece to jump!");
            if (board[startPosY - 1][startPosX + 1].getColor() == playerColor) return Message.error("That is not your piece!");

            if (additionalJumpAvailable(endPosX, endPosY)) return Message.error("Multi-jump available and must be used.");
            return Message.info("Move valid!");
        }

        // TODO: add king piece functionality

        return Message.error("Unknown error occurred.");
    }

    private boolean additionalJumpAvailable(int endPosX, int endPosY) {
        if (endPosY - 2 >= 0) {
            if (endPosX - 2 >= 0) {
                if (board[endPosY - 2][endPosX - 2] == null) {
                    if (board[endPosY - 1][endPosX - 1] != null) {
                        if (board[endPosY - 1][endPosX - 1].getColor() != playerColor) {
                            return true;
                        }
                    }
                }
            }
            if (endPosX + 2 <= 7) {
                if (board[endPosY - 2][endPosX + 2] == null) {
                    if (board[endPosY - 1][endPosX + 1] != null) {
                        if (board[endPosY - 1][endPosX + 1].getColor() != playerColor) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public CheckerPiece getPieceBeingMoved(int startPosX, int startPosY) {
        if (startPosX >= 0 && startPosX <= 7 && startPosY >= 0 && startPosY <= 7) {
            return board[startPosY][startPosX];
        }
        return null;
    }
}
