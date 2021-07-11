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

    public boolean isValid(CheckerBoard board){

        // is the move within board constraints?
        if (endingPosition.getX() < 0 || endingPosition.getX() > 7){
            return false;
        } else if (endingPosition.getY() < 0 || endingPosition.getY() > 7){
            return false;
        }

        // is the desired space empty?
        if (!(board.getBoard()[endingPosition.getX()][endingPosition.getY()] == null)){
            return false;
        }

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

        // is the piece moving to a valid square?
        if (endingPosition.getX()%2 == 0 || endingPosition.getY()%2 == 0){
            return false;
        }

        return true;
    }

    public CheckerBoard makeMove(CheckerBoard board){

        if (!isValid(board)){
            return board;
        }

        // TODO: modify function returns
        //return board.placePiece(this.currentPiece,this.startingPosition, this.endingPosition);
        return null;
    }
}
