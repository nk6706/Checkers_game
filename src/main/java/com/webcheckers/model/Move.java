package com.webcheckers.model;

import com.webcheckers.util.Message;

public class Move {
    public enum MoveValidity {
        VALID_END_OF_TURN, VALID_JUMP_REQUIRED,
        INVALID_JUMP_REQUIRED, INVALID
    }

    private Position startingPosition;
    private Position endingPosition;
    private CheckerPiece[][] currentBoard;
    private CheckerPiece.Color playerColor;

    public Move(Position start, Position end, CheckerPiece[][] currentBoard, CheckerPiece.Color playerColor){
        this.startingPosition = start;
        this.endingPosition = end;
        this.currentBoard = currentBoard;
        this.playerColor = playerColor;
    }

    public Message isValid(){
        int startPosX = startingPosition.getRow();
        int startPosY = startingPosition.getCell();
        int endPosX = endingPosition.getRow();
        int endPosY = endingPosition.getCell();

        // is the move within board constraints?
        if (endPosX < 0 || endPosX > 7 || startPosX < 0 || startPosX > 7) return Message.error("Out of bounds!");
        if (endPosY < 0 || endPosY > 7 || startPosY < 0 || startPosY > 7) return Message.error("Out of bounds!");

        if (currentBoard[startPosY][startPosX] == null || currentBoard[endPosY][endPosX] != null) {
            return Message.error("Moving to non-empty location!");
        }

        CheckerPiece movingPiece = currentBoard[startPosY][startPosX];

        if (movingPiece.getColor() != playerColor) return Message.error("This is not your piece!");

        if (movingPiece.getType() == CheckerPiece.Type.SINGLE) {
            // If single space move
            if (endPosY == startPosY - 1) {
                return singleSpaceMove(startPosX, startPosY, endPosX, endPosY);
            } // If jump move
            else if (endPosY == startPosY - 2) {
                return jumpMove(startPosX, startPosY, endPosX, endPosY);
            }
        }

        // TODO: add king piece behavior

        return Message.info("Move valid!");
    }

    private Message singleSpaceMove(int startPosX, int startPosY, int endPosX, int endPosY) {
        // If moving diagonally left one space
        if (endPosX == startPosX - 1) {
            // and 2 spaces diagonally right exists on board
            if (!(startPosX + 2 > 7) && !(startPosY - 2 < 0)) {
                // and the space one diagonally to the right contains a piece
                if (currentBoard[endPosY][startPosX + 1] != null) {
                    // and that piece is not one of the player's
                    if (currentBoard[endPosY][startPosX + 1].getColor() != playerColor) {
                        // and space 2 diagonally to the right is null
                        if (currentBoard[startPosY - 2][startPosX + 2] == null) {
                            return Message.error("Jump required!");
                        }
                    }
                }
            }
            return Message.info("Move valid!");
        }
        // If moving diagonally right one space
        else if (endPosX == startPosX + 1) {
            // and 2 spaces diagonally left exists on board
            if (!(startPosX - 2 < 0) && !(startPosY - 2 < 0)) {
                // and the space one diagonally to the left contains a piece
                if (currentBoard[endPosY][startPosX - 1] != null) {
                    // and that piece is not one of the player's
                    if (currentBoard[endPosY][startPosX - 1].getColor() != playerColor) {
                        // and space 2 diagonally to the left is null
                        if (currentBoard[startPosY - 2][startPosX - 2] == null) {
                            return Message.error("Jump required!");
                        }
                    }
                }
            }
            return Message.info("Move valid!");
        }
        return Message.info("Unknown error occurred.");
    }

    private Message jumpMove(int startPosX, int startPosY, int endPosX, int endPosY) {
        // If jumping diagonally left
        if (endPosX == startPosX - 2) {
            if (currentBoard[startPosY - 1][startPosX - 1] == null) return Message.error("There is no piece to jump!");
            if (currentBoard[startPosY - 1][startPosX - 1].getColor() == playerColor) return Message.error("That is not your piece!");

            if (additionalJumpAvailable(endPosX, endPosY)) return Message.error("Multi-jump available and must be used.");
            return Message.info("Move valid!");
        }
        // If jumping diagonally right
        else if (endPosX == startPosX + 2) {
            if (currentBoard[startPosY - 1][startPosX + 1] == null) return Message.error("There is no piece to jump!");
            if (currentBoard[startPosY - 1][startPosX + 1].getColor() == playerColor) return Message.error("That is not your piece!");

            if (additionalJumpAvailable(endPosX, endPosY)) return Message.error("Multi-jump available and must be used.");
            return Message.info("Move valid!");
        }
        return Message.error("Unknown error occurred.");
    }

    private boolean additionalJumpAvailable(int endPosX, int endPosY) {
        if (endPosY - 2 >= 0) {
            if (endPosX - 2 >= 0) {
                if (currentBoard[endPosY - 2][endPosX - 2] == null) {
                    if (currentBoard[endPosY - 1][endPosX - 1] != null) {
                        if (currentBoard[endPosY - 1][endPosX - 1].getColor() != playerColor) {
                            return true;
                        }
                    }
                }
            }
            if (endPosX + 2 <= 7) {
                if (currentBoard[endPosY - 2][endPosX + 2] == null) {
                    if (currentBoard[endPosY - 1][endPosX + 1] != null) {
                        if (currentBoard[endPosY - 1][endPosX + 1].getColor() != playerColor) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public CheckerPiece getPieceBeingMoved() {
        int startPosX = startingPosition.getRow();
        int startPosY = startingPosition.getCell();

        if (startPosX >= 0 && startPosX <= 7 && startPosY >= 0 && startPosY <= 7) {
                return currentBoard[startPosY][startPosX];
        }
        return null;
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

    public Position getStart() {
        return startingPosition;
    }

    public Position getEnd(){
        return endingPosition;
    }
}
