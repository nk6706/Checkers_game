package com.webcheckers.model;

public class Move {

    private Position startingPosition;
    private Position endingPosition;
    private CheckerPiece currentPiece;


    public Move(Position start, Position end, CheckerPiece pieceToMove){
        this.startingPosition = start;
        this.endingPosition = end;
        currentPiece = pieceToMove;
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

        // is the piece moving in a valid direction?
        if (!(this.currentPiece.getType() == CheckerPiece.Type.KING)){
            if (this.currentPiece.getColor() == CheckerPiece.Color.WHITE && endingPosition.getY() > startingPosition.getY()){
                return false;
            } else if (this.currentPiece.getColor() == CheckerPiece.Color.RED && endingPosition.getY() < startingPosition.getY()){
                return  false;
            }
        }

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

        return board.placePiece(this.currentPiece,this.startingPosition, this.endingPosition);
    }
}
