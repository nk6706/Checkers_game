package com.webcheckers.model;

public class Move {
    public enum MoveValidity {
        VALID_END_OF_TURN, VALID_JUMP_REQUIRED,
        INVALID_JUMP_REQUIRED, INVALID
    }

    private Position startingPosition;
    private Position endingPosition;
    private CheckerPiece[][] currentBoard;

    public Move(Position start, Position end, CheckerPiece[][] currentBoard){
        this.startingPosition = start;
        this.endingPosition = end;
        this.currentBoard = currentBoard;
    }

    public MoveValidity isValid(){

        int startPosX = startingPosition.getX();
        int startPosY = startingPosition.getY();
        int endPosX = endingPosition.getX();
        int endPosY = endingPosition.getY();

        // is the move within board constraints?
        if (endPosX < 0 || endPosX > 7 || startPosX < 0 || startPosX > 7){
            return MoveValidity.INVALID;
        } else if (endPosY < 0 || endPosY > 7 || startPosY < 0 || startPosY > 7){
            return MoveValidity.INVALID;
        }

        if (currentBoard[startPosY][startPosX] == null || currentBoard[endPosY][endPosX] != null) {
            return MoveValidity.INVALID;
        }

        CheckerPiece movingPiece = currentBoard[startPosY][startPosX];

        if (movingPiece.getType() != CheckerPiece.Type.KING) {
            // If single space diagonal move
            if (endPosY == startPosY - 1 && (endPosX == startPosX - 1 || endPosX == startPosX + 1)) {

            } // If jump move
            else if (endPosY == startPosY - 2 && (endPosX == startPosX - 2 || endPosX == startPosX + 2)) {

            }
        }

        /*
        // is the desired space empty?
        if (!(board.getBoard()[endingPosition.getX()][endingPosition.getY()] == null)){
            return false;
        }*/

        /* TODO: modify for currentBoard variable
        // is the piece moving in a valid direction?
        if (!(this.currentPiece.getType() == CheckerPiece.Type.KING)){
            if (this.currentPiece.getColor() == CheckerPiece.Color.WHITE && endingPosition.getY() > startingPosition.getY()){
                return false;
            } else if (this.currentPiece.getColor() == CheckerPiece.Color.RED && endingPosition.getY() < startingPosition.getY()){
                return  false;
            }
        }
        */

        /*
        // is the piece moving to a valid square?
        if (endingPosition.getX()%2 == 0 || endingPosition.getY()%2 == 0){
            return false;
        }*/

        return MoveValidity.VALID_END_OF_TURN;
    }

    public CheckerBoard makeMove(CheckerBoard board){
        /*
        if (!isValid(board)){
            return board;
        }*/

        // TODO: modify function returns
        //return board.placePiece(this.currentPiece,this.startingPosition, this.endingPosition);
        return null;
    }
}
