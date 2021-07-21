package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import java.util.HashMap;

public class GameManager {

    /** All games running on the site right now, key=GameID value=Game */
    private HashMap<Integer, CheckersGame> games;
    /** Id of the last game made, incremented before creating a new game */
    private int lastId = 0;

    public GameManager() {
        this.games = new HashMap<>();
    }

    /**
     * Constructs a new game when one is required
     * @param redPlayer the player to make red pieces
     * @param whitePlayer the player to make white pieces
     * @return a newly constructed checkers game
     */
    public CheckersGame newGame(Player redPlayer, Player whitePlayer) {
        CheckersGame game = new CheckersGame(++lastId, redPlayer, whitePlayer);
        games.put(game.getId(), game);
        return game;
    }

    /**
     * Get the game with the given id from the hash map
     * @param id the id of the game to retrieve
     * @return CheckersGame object with given id
     */
    public CheckersGame getGame(int id) {
        return games.get(id);
    }

    /**
     * Validates a move
     * @param gameID the game id to validate the move in
     * @param move the move to validate
     * @return Message.info if move is valid, Message.error if not
     */
    public Message isValidMove(int gameID, Move move) {
        final CheckersGame game = getGame(gameID);
        return game.isValidMove(move);
    }

    /**
     *
     * @param gameID
     * @return
     */
    public Message isValidTurn(int gameID) {
        final CheckersGame game = getGame(gameID);
        return game.isValidTurn();
    }

    /**
     *
     * @param gameID
     */
    public void submitTurn(int gameID) {
        final CheckersGame game = getGame(gameID);
        game.newTurn();
        game.toggleActivePlayer();
    }

    /**
     * Uses the gameID and a Move data type object to move a piece on the board
     * @param gameID the id of the game to make the move in
     * @param move the move to make (start / end pos)
     */
    public void makeMove(int gameID, Move move) {
        final CheckersGame game = getGame(gameID);
        game.makeMove(move);
    }

    /**
     * Used to undo a move in a game
     * @param gameID the game id to undo the move in
     * @return a message regarding if undo was valid or not
     */
    public Message undoMove(int gameID) {
        final CheckersGame game = getGame(gameID);
        return game.undoMove();
    }

    public void setGameOver(int gameID, String message) {
        final CheckersGame game = getGame(gameID);
        game.setGameOver(message);
    }

}