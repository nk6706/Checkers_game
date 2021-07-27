package com.webcheckers.model;

import com.webcheckers.util.Message;

import java.util.ArrayList;

/**
 * CheckerBoard is a model-level representation of a checker board used in the game of checkers.
 * An instantiation of CheckerBoard contains an 8x8 array of CheckerPiece(s) which can be
 * modified through the move method.
 */
public class CheckerBoard {

    /** Matrix representing board with pieces on it (null if no piece) */
    private CheckerPiece[][] board;
    private int whitePieces;
    private int redPieces;

    /**
     * Default constructor to create initial board
     */
    public CheckerBoard() {
        board = new CheckerPiece[8][8];
        whitePieces = 12;
        redPieces = 12;

        for (int i = 0; i < 3; i++) {
            fillRow(i, CheckerPiece.Color.WHITE);
        }

        for (int i = 5; i < 8; i++) {
            fillRow(i, CheckerPiece.Color.RED);
        }

    }


    /**
     * Constructor for testing
     * @param positions The array of positions for the test
     * @param checkerPieces The array of checkerPieces
     */
    public CheckerBoard(ArrayList<Position> positions, ArrayList<CheckerPiece> checkerPieces){
        board = new CheckerPiece[8][8];
        whitePieces = 0;
        redPieces =  0;
        if(positions.size() == checkerPieces.size()){
             for(int i=0; i<positions.size(); i++){
                 Position pos = positions.get(i);
                 this.board[pos.getRow()][pos.getCell()] = checkerPieces.get(i);
                 if(checkerPieces.get(i).getColor() == CheckerPiece.Color.RED){
                     redPieces++;
                 }else{
                     whitePieces++;
                 }
            }
        }
    }


    /**
     * Copy constructor
     * @param board the old board to copy
     */
    CheckerBoard(CheckerBoard board, boolean flipped) {
        if ( flipped ) {
            this.board = board.getFlippedBoard();
        } else {
            this.board = board.getBoard();
        }
        this.redPieces = board.getRedPieces();
        this.whitePieces = board.getWhitePieces();
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

    /**
     * Checks to see if the given move is valid
     * @param move the move to check for validity
     * @param first whether or not this is the first move of the turn
     * @return true if the move is valid, false otherwise
     */
    public Message isValidMove(Move move, boolean first) {
        final Position start = move.getStart();
        final Position end = move.getEnd();
        final int rowDiff = start.getRow() - end.getRow();
        final int cellDiff = start.getCell() - end.getCell();

        final CheckerPiece piece = getPiece(start);

        if(isJumpAvailable(piece.getColor())){
            if(!piece.isKing()){
                //For Checkers
                if(rowDiff !=2)
                    return Message.error("A jump move could have been made that was not made.");
                else{
                    return checkCaptureForwardMovement(cellDiff,start,piece);
                }
            } else{
                //for King
                if(rowDiff !=2 && rowDiff != -2)
                    return Message.error("A jump move could have been made that was not made.");
                else{
                    if( rowDiff == 2 ){
                        return checkCaptureForwardMovement(cellDiff,start,piece);
                    }else {
                        if ( cellDiff == 2 ) {
                            if ( hasPiece(new Position(start.getRow()+1, start.getCell()-1)) ) {
                                if(getPiece(new Position(start.getRow()+1, start.getCell()-1)).getColor() == piece.getColor())
                                    return Message.error("Cannot capture your own checker!");
                                return Message.info("");
                            }
                        } else if ( cellDiff == -2 ) {
                            if ( hasPiece(new Position(start.getRow()+1, start.getCell()+1)) ) {
                                if(getPiece(new Position(start.getRow()+1, start.getCell()+1)).getColor() == piece.getColor())
                                    return Message.error("Cannot capture your own checker!");
                                return Message.info("");
                            }
                        }
                    }
                    return Message.error("Cannot move more than on row without jumping.");
                }
            }


        }

        //Single move validation
        if ( !piece.isKing() ) {
            //For Checkers
            if (rowDiff < 0 ) {
                return Message.error("A single piece can only move forward");
            } else{
                return checkForwardMovement(first, rowDiff, cellDiff);
            }

        } else{
            //For King
            if(rowDiff < -1){
                return Message.error("A king can only move one row back without jump.");
            } else if( rowDiff == -1 ){
                if ( !first ) {
                    return Message.error("Can only move a single row in a turn");
                } else if ( cellDiff == 1 || cellDiff == -1 ) {
                    return Message.info("");
                } else {
                    return Message.error("You can only move a single cell in either direction");
                }
            } else{
                return checkForwardMovement(first, rowDiff, cellDiff);
            }
        }

    }


    /**
     * Helper to isValidMove for capturing in forward direction.
     * @param cellDiff the cell difference between start and end
     * @param start start position for the move
     * @param piece the piece being moved
     * @return
     */
    private Message checkCaptureForwardMovement(int cellDiff, Position start, CheckerPiece piece){
        if ( cellDiff == 2 ) {
            if ( hasPiece(new Position(start.getRow()-1, start.getCell()-1)) ) {
                if(getPiece(new Position(start.getRow()-1, start.getCell()-1)).getColor() == piece.getColor())
                    return Message.error("Cannot capture your own checker!");
                return Message.info("");
            }
        } else if ( cellDiff == -2 ) {
            if ( hasPiece(new Position(start.getRow()-1, start.getCell()+1)) ) {
                if(getPiece(new Position(start.getRow()-1, start.getCell()+1)).getColor() == piece.getColor())
                    return Message.error("Cannot capture your own checker!");
                return Message.info("");
            }
        }

        return Message.error("Moved more than one row forward without jumping");
    }


    /**
     * Helper for move validation for single move
     * @param first if it is the first move
     * @param rowDiff the difference in start and end row
     * @param cellDiff the difference in start and end column
     * @return message to main function
     */
    private Message checkForwardMovement(boolean first, int rowDiff, int cellDiff) {
        if ( rowDiff == 0 ) { // Tried to move to tile in same row (invalid)
            return Message.error("Single piece must change a row!");
        } else if ( rowDiff == 1 ) { // Advanced 1 row (could be valid or invalid)
            if ( !first ) {
                return Message.error("Can only move once in a turn!");
            } else if ( cellDiff == 1 || cellDiff == -1 ) {
                return Message.info("");
            } else {
                return Message.error("You can only move a single cell in either direction!");
            }
        } else { // Moved too many rows forward
            return Message.error("Moved more than one row forward without jumping");
        }
    }

    /**
     * Whether or not a jump is available in this given board configuration
     * @param color the color of the current player
     * @return true if was a jump is available on this board, false otherwise
     */
    public boolean isJumpAvailable(CheckerPiece.Color color) {
        for(int i = 0; i < this.board.length; i++) {
            for(int j = 0; j < this.board[i].length; j++) {
                CheckerPiece piece = this.board[i][j];
                if ( piece != null && piece.getColor().equals(color) ) {
                    final Position pos = new Position(i, j);
                    if (isJumpPossible(color, pos)) return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method to find if jump is possible
     * @param color Color of the player
     * @param pos Position of the piece
     * @return if jump is possible.
     */
    private boolean isJumpPossible(CheckerPiece.Color color, Position pos) {
        if(this.getPiece(pos).isKing()){
            if ( isJumpAvailable(pos, true, true, color) ||
                    isJumpAvailable(pos, true, false, color) ||
                    isJumpAvailable(pos, false, true, color) ||
                    isJumpAvailable(pos, false, false, color)) {
                return true;
            }
        }else{
            if ( isJumpAvailable(pos, true, true, color) ||
                    isJumpAvailable(pos, true, false, color) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Whether or not a move is available in this given board configuration
     * @param color the color of the current player
     * @return true if was a jump is available on this board, false otherwise
     */
    public boolean isMoveAvailable(CheckerPiece.Color color){

        if(isJumpAvailable(color)){
            return true;
        }

        for(int i = 0; i < this.board.length; i++) {
            for(int j = 0; j < this.board[i].length; j++) {
                if(this.board[i][j] != null){
                    Position pos = new Position(i,j);
                    if(this.getPiece(pos).isKing()){
                        if ( isMoveAvailable(pos, true, true) ||
                                isMoveAvailable(pos, true, false) ||
                                isMoveAvailable(pos, false, true) ||
                                isMoveAvailable(pos, false, false)) {
                            return true;
                        }
                    }else{
                        if ( isMoveAvailable(pos, true, true) ||
                                isMoveAvailable(pos, true, false) ) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Helper method for isMoveAvailable, checks specific moves in specific directions
     * @param pos the position of the piece
     * @param forward true if want to check for a forward move, false for backward move
     * @param right true if want to check for a move to the  right, false for left move
     * @return true if a move in the given direction is available, false otherwise
     */
    private boolean isMoveAvailable(Position pos, boolean forward, boolean right) {
        final int row = pos.getRow();
        final int cell = pos.getCell();

        final int middleRow = forward ? row - 1 : row + 1;
        final int middleCell = right ? cell + 1 : cell - 1;


        if ( middleRow > -1 && middleRow < 8 && middleCell > -1 && middleCell < 8 ) { // Check if in bounds
            if ( getPiece(new Position(middleRow, middleCell)) == null ) { // Check if empty
                return true;
            }
        }

        return false;
    }



    /**
     * Check if a jump was performed.
     * @param board the previous board.
     * @return true if jump was performed.
     */
    public boolean wasJumpMove(CheckerBoard board){
        int old = 0;int  current = 0;
        for(int i=0; i<8; i++){
            for(int j=0;j<8; j++){
                if(board.getPiece(new Position(i,j))!=null)
                    old++;
                if(this.getPiece(new Position(i,j))!=null)
                    current++;
            }
        }
        return current!=old;
    }

    /**
     * Checks for checker jump
     * @param board
     * @param color
     * @return
     */
    public boolean isJumpAvailable(CheckerBoard board, CheckerPiece.Color color){
        Position pos = findMovedPieceNew(board, color);
        if (isJumpPossible(color, pos)) return true;


        return false;
    }


    /**
     * Helper method for isJumpAvailable, checks specific jumps is specific directions
     * @param pos the position of the piece
     * @param forward true if want to check for a forward jump, false for backward jump
     * @param right true if want to check for a jump to the  right, false for left
     * @param color the color of the player's piece
     * @return true if a jump in the given direction is available, false otherwise
     */
    private boolean isJumpAvailable(Position pos, boolean forward, boolean right, CheckerPiece.Color color) {
        final int row = pos.getRow();
        final int cell = pos.getCell();

        final int middleRow = forward ? row - 1 : row + 1;
        final int middleCell = right ? cell + 1 : cell - 1;

        final int farRow = forward ? row - 2 : row + 2;
        final int farCell = right ? cell + 2 : cell - 2;

        if ( farRow > -1 && farRow < 8 && farCell > -1 && farCell < 8 ) { // Check the jump is in bounds
            if ( getPiece(new Position(farRow, farCell)) == null ) { // Check the position jumping to is empty
                final CheckerPiece pieceToJump = getPiece(new Position(middleRow, middleCell));
                if ( pieceToJump != null && !pieceToJump.getColor().equals(color) ) { // Check an opponents piece is there
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Combined with its helper methods, this method determines if between the previous board and this board, a single move was made
     * @param board the previous board before this one
     * @return true if the piece that was moved was a single piece, false otherwise
     */
    public boolean wasSingleMove(CheckerBoard board) {
        if ( board == null )
            return false;

        final Position pos = findMovedPiece(board);

        if ( isMatchingPieceHere(pos, board, true, true) ||
                isMatchingPieceHere(pos, board, true, false) ||
                isMatchingPieceHere(pos, board, false, true) ||
                isMatchingPieceHere(pos, board, false, false) ) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the given piece can be found at the given location (used for finding how a piece moved between moves)
     * @param pos the position of the old piece before the move
     * @param board the old board
     * @param forward true if want to check for a forward move, false for backward
     * @param right true if want to check for a move to the right, false for left
     * @return true if the matching piece was moved here, false otherwise
     */
    private boolean isMatchingPieceHere(Position pos, CheckerBoard board, boolean forward, boolean right) {
        final int row = pos.getRow();
        final int cell = pos.getCell();


        final int adjRow = forward ? row - 1 : row + 1;
        final int adjCell = right ? cell + 1 : cell - 1;


        if ( adjRow > -1 && adjRow < 8 && adjCell > -1 && adjCell < 8 ) { // Check the surroundings is in bounds
            final Position adjPos = new Position(adjRow, adjCell);
            final CheckerPiece oldAdjPiece = board.getPiece(adjPos);
            if ( oldAdjPiece == null ) {
                final CheckerPiece newAdjPiece = this.getPiece(adjPos);
                if ( newAdjPiece != null ) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * Returns the position on the old board where the piece that got moved
     * @param board the old board
     * @return Position of moved piece, null if one could not be found
     */
    private Position findMovedPiece(CheckerBoard board) {
        final CheckerPiece[][] previous = board.getBoard();
        for(int i = 0; i < previous.length; i++) {
            for(int j = 0; j < previous[i].length; j++) {
                final Position pos = new Position(i, j);
                CheckerPiece previousPiece = board.getPiece(pos);
                if (previousPiece != null) {
                    CheckerPiece currentPiece = this.getPiece(pos);
                    if ( currentPiece == null ) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the position on the new board where the piece that got moved
     * @param board the old board
     * @param color the color of the player who made the move
     * @return Position of moved piece, null if one could not be found
     */
    private Position findMovedPieceNew(CheckerBoard board, CheckerPiece.Color color) {
        final CheckerPiece[][] previous = board.getBoard();
        for(int i = 0; i < this.board.length; i++) {
            for(int j = 0; j < this.board[i].length; j++) {
                final Position pos = new Position(i, j);
                CheckerPiece currentPiece = this.getPiece(pos);
                if (currentPiece != null && currentPiece.getColor()==color) {
                    if ( previous[i][j] == null ) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the piece at the given position
     * @param pos the position of the piece to get
     * @return the piece at the position (null if no piece)
     */
    private CheckerPiece getPiece(Position pos) {
        return this.board[pos.getRow()][pos.getCell()];
    }

    /**
     * Whether or not a piece is located at this position
     * @param pos the position to check for a piece
     * @return true if a piece exists here, false otherwise
     */
    private boolean hasPiece(Position pos) {
        return getPiece(pos) != null;
    }

    /**
     * Moves a piece from given pos to end pos, no checks included!
     * @param start the position of the piece to move
     * @param end the position to place the piece at
     */
    public void movePiece(Position start, Position end) {
        this.board[end.getRow()][end.getCell()] = this.board[start.getRow()][start.getCell()];
        if( this.board[start.getRow()][start.getCell()].isKing() ){
            captureForward(start, end);
            if(start.getRow()-end.getRow() == -2 && end.getCell()-start.getCell() == 2){
                capturePiece(start.getRow()+1, start.getCell()+1);
            }else if(start.getRow()-end.getRow() == -2 && start.getCell()-end.getCell() == 2){
                capturePiece(start.getRow()+1, start.getCell()-1);
            }
        }else{
            captureForward(start, end);
        }
        if(end.getRow() == 0){
            this.board[end.getRow()][end.getCell()] = null;
            this.board[end.getRow()][end.getCell()] = new CheckerPiece(this.board[start.getRow()][start.getCell()].getColor(),true);
        }
        this.board[start.getRow()][start.getCell()] = null;
    }


    /**
     * Helper to movePiece for forward capture
     * @param start The start position of the move
     * @param end The end position of the move
     */
    private void captureForward(Position start, Position end) {
        if(start.getRow()-end.getRow() == 2 && end.getCell()-start.getCell() == 2){
            capturePiece(start.getRow()-1, start.getCell()+1);
        }else if(start.getRow()-end.getRow() == 2 && start.getCell()-end.getCell() == 2){
            capturePiece(start.getRow()-1,start.getCell()-1);
        }
    }


    /**
     * Capture a piece
     * @param row The row of the piece
     * @param cell the cell of the piece
     */
    private void capturePiece(int row, int cell){
        if (this.board[row][cell] != null){
            if(this.board[row][cell].getColor() == CheckerPiece.Color.RED)
                this.redPieces--;
            else
                this.whitePieces--;
        }
        this.board[row][cell] = null;
    }

    /**
     * Getters for white pieces
     * @return white pieces
     */
    public int getWhitePieces() {
        return this.whitePieces;
    }

    /**
     * Getters for red pieces
     * @return red pieces
     */
    public int getRedPieces() {
        return this.redPieces;
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

}
