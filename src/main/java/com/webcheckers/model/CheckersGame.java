package com.webcheckers.model;

import com.webcheckers.util.Message;

import java.util.ArrayList;
import java.util.Stack;

/**
 * CheckersGame is a model-level representation of a game of checkers. Each CheckersGame has
 * two Player(s) and a CheckerBoard that holds its CheckerPiece(s). Additionally, each
 * CheckersGame has a unique integer ID.
 */
public class CheckersGame {

    /** unique id for this game */
    private int id;

    private Player redPlayer;
    private Player whitePlayer;

    /** Holds the player whose turn it is */
    private Player activePlayer;

    /** Stack of boards in current torn */
    private Stack<CheckerBoard> boards = new Stack<>();

    /** Stack of boards in entire game */
    private ArrayList<CheckerBoard> gameBoards = new ArrayList<>();


    private boolean gameOver = false;
    private String gameOverMessage;

    public CheckersGame(int id, Player redPlayer, Player whitePlayer) {
        this.id = id;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activePlayer = redPlayer;

        ArrayList<Position> positions;
        ArrayList<CheckerPiece> pieces;

        switch(redPlayer.getUsername()) {
            case "1":
                positions = new ArrayList<>() {
                    {
                        add(new Position(5, 2));
                        add(new Position(4, 3));
                        add(new Position(2, 5));
                    }
                };
                pieces = new ArrayList<>() {
                    {
                        add(new CheckerPiece(CheckerPiece.Color.WHITE));
                        add(new CheckerPiece(CheckerPiece.Color.RED));
                        add(new CheckerPiece(CheckerPiece.Color.WHITE));
                    }
                };
                boards.push(new CheckerBoard(positions, pieces));
                break;
            case "2":
                positions = new ArrayList<>() {
                    {
                        add(new Position(1, 2));
                        add(new Position(3, 2));
                        add(new Position(2, 3));
                        add(new Position(1, 4));
                    }
                };
                pieces = new ArrayList<>() {
                    {
                        add(new CheckerPiece(CheckerPiece.Color.RED));
                        add(new CheckerPiece(CheckerPiece.Color.WHITE));
                        add(new CheckerPiece(CheckerPiece.Color.WHITE));
                        add(new CheckerPiece(CheckerPiece.Color.WHITE));
                    }
                };
                boards.push(new CheckerBoard(positions, pieces));
                break;
            default:
                boards.push(new CheckerBoard());
        }
    }

    /**
     * Getter method for unique id
     * @return int representing the id of this game
     */
    public int getId() {
        return id;
    }

    public CheckerPiece[][] getBoard(Player player) {
        final CheckerBoard board = this.boards.peek();
        if(isPlayersTurn(player)) {
            return board.getBoard();
        }
        return board.getFlippedBoard();
    }

    public CheckerPiece.Color getActiveColor() {
        if (isRedPlayer(activePlayer)) {
            return CheckerPiece.Color.RED;
        }
        return CheckerPiece.Color.WHITE;
    }

    /**
     * Getter method for game board (red)
     * @return CheckerBoard object of the board
     */
    public CheckerPiece[][] getBoard() {
        return this.boards.peek().getBoard();
    }

    /**
     * Getter method for game board (white)
     * @return CheckerBoard object of the board
     */
    public CheckerPiece[][] getFlippedBoard() {
        return this.boards.peek().getFlippedBoard();
    }

    /**
     *
     * @return
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     *
     * @return
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Determines if the provided player is the redPlayer of this game
     * @param player the player to check
     * @return true if the provided player is red, false otherwise (meaning they are white player)
     */
    public boolean isRedPlayer(Player player) { return player.equals(this.redPlayer); }
  
    public void setGameOver(String playerLoss){
        this.gameBoards.addAll(this.boards);
        gameOver = true;
        this.gameOverMessage = playerLoss;
        this.redPlayer.setGameID(-1);
        this.whitePlayer.setGameID(-1);
    }

    public boolean isGameOver(){
        return this.gameOver;
    }

    public String getGameOverMessage(){
        return this.gameOverMessage;
    }

    /**
     * Determines if it is the provided player's turn (if they are the activePlayer)
     * @param player the player to check if its their turn
     * @return true if it is the player's turn, false otherwise
     */
    public boolean isPlayersTurn(Player player) { return player.equals(activePlayer); }

    /**
     * Used when submitting a valid turn to change whose turn it is
     */
    public void toggleActivePlayer() {
        this.activePlayer = activePlayer.equals(whitePlayer) ? redPlayer : whitePlayer;
    }

    /**
     * Method for TurnManager to check if a move is valid or not
     * @param move the move to check
     * @return Message.INFO if valid, Message.ERROR with error msg if invalid
     */
    public Message isValidMove(Move move) {
        return this.boards.peek().isValidMove(move, this.boards.size() == 1);
    }

    /**
     * Method for appl tier to check if a move is valid or not
     * @return Message.INFO if valid, Message.ERROR with error msg if invalid
     */
    public Message isValidTurn() {
        CheckerBoard previous = null;
        int numberOfBoards = this.boards.size();
        boolean wasSingle = false;
        for (int i=0; i< numberOfBoards; i++ ) {
            if (previous != null) {
                final CheckerPiece.Color color = activePlayer.equals(redPlayer) ? CheckerPiece.Color.RED : CheckerPiece.Color.WHITE;

                if( this.boards.get(i).wasJumpMove(previous)) {
                    if( this.boards.get(i).isJumpAvailable(previous, color) ) {
                        if(i == numberOfBoards -1)
                            return Message.error("Another jump is possible!");
                    }
                }else{
                    wasSingle = true;
                }
                if(wasSingle && i>1){
                    return Message.error("Cannot jump after single move!");
                }

            }
            previous = this.boards.get(i);
        }
        return Message.info("");
    }

    /**
     * Used to make a move, copies a  new board and moves the piece as directed
     * @param move the move to make
     */
    public void makeMove(Move move) {
        CheckerBoard previous = new CheckerBoard(this.boards.peek(), false);
        previous.movePiece(move.getStart(), move.getEnd());
        this.boards.push(previous);
    }

    public Message undoMove() {
        if ( this.boards.size() == 1 ) {
            return Message.error("No moves have been made yet");
        }
        this.boards.pop();
        return Message.info("Reverted back to previous move");
    }

    /**
     * Prepares the boards for a new turn
     */
    public void newTurn() {
        this.toggleActivePlayer();
        final CheckerBoard board = new CheckerBoard(this.boards.pop(), true);
        if (!board.isMoveAvailable(this.getActiveColor())) {
            this.setGameOver("No possible moves!");
        }
        else if (board.getRedPieces() == 0){
            this.setGameOver("Red Player is out of pieces!");
        }
        else if (board.getWhitePieces() == 0){
            this.setGameOver("White Player is out of pieces!");
        }
        this.gameBoards.addAll(this.boards);
        this.boards.removeAllElements();
        this.boards.add(board);
    }

    /**
     * Determines whether there is a next turn (next element) in the gameBoards arr list
     * @param index the index to check
     * @return true if there is a next turn, false otherwise
     */
    public boolean spectatorHasNext(int index) {
        if (index < gameBoards.size()-1) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether there is a previous turn (next element) in the gameBoards arr list
     * @param index the index to check
     * @return true if there is a previous turn, false otherwise
     */
    public boolean spectatorHasPrevious(int index) {
        if (index > 0) {
            return true;
        }
        return false;
    }

    /**
     * Get the board for the spectator from the gameBoards arr list
     * @param index the index of the board to get
     * @return the CheckerPiece[][] representing the board at the given index
     */
    public CheckerPiece[][] spectatorGetBoard(int index) {
        return this.gameBoards.get(index).getBoard();
    }

}
