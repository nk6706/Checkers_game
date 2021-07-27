package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * GameManager handles interaction between the UI tier and CheckerGame objects in the model tier.
 * It is responsible for creating and storing games as well as facilitating
 * operations for them.
 */
public class GameManager {

    /** All games running on the site right now, key=GameID value=Game */
    private final HashMap<Integer, CheckersGame> games;

    /** Replay positions for users, key=username value=replay pos */
    private final HashMap<String, Integer> replayPositions;

    /** Id of the last game made, incremented before creating a new game */
    private int lastId = 0;

    /**
     * Initializes needed HashMaps for storing games
     */
    public GameManager() {
        this.games = new HashMap<>();
        this.replayPositions = new HashMap<>();
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
     * Determines whether or not the turn queued is valid
     * @param gameID the gameID to check if the turn is valid of
     * @return Message.info if the game is valid, Message.error otherwise
     */
    public Message isValidTurn(int gameID) {
        final CheckersGame game = getGame(gameID);
        return game.isValidTurn();
    }

    /**
     * Used to submit a turn when the user is done
     * @param gameID the gameID to finalize the turn of
     */
    public void submitTurn(int gameID) {
        final CheckersGame game = getGame(gameID);
        game.newTurn();
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

    /**
     * Appl tier method which passed setGameOver call down to the game itself
     * @param gameID the id of the game to set over
     * @param message the game over reason or message
     */
    public void setGameOver(int gameID, String message) {
        final CheckersGame game = getGame(gameID);
        game.setGameOver(message);
    }


    /**
     * Returns the index of the game board to show in replay mode
     * @param username the username of the player, used to find the index
     * @return the index the player is at or 0 if none could be found
     */
    public int getReplayPosition(String username) {
        if (!this.replayPositions.containsKey(username)) {
            this.replayPositions.put(username, 0);
        }
        return this.replayPositions.get(username);
    }

    /**
     * Increases the replay index by 1
     * @param username the username of the player whose index should be updated
     * @return the int of the position prior to incrementing
     */
    public int incrementReplayPosition(String username) {
        final int pos = this.getReplayPosition(username);
        return this.replayPositions.put(username, pos+1);
    }

    /**
     * Decreases the replay index by 1
     * @param username the username of the player whose index should be updated
     * @return the int of the position prior to decrementing
     */
    public int decrementReplayPosition(String username) {
        final int pos = this.getReplayPosition(username);
        return this.replayPositions.put(username, pos-1);
    }

    /**
     * Removes a player's replay position when they are done replaying a game
     * @param username the name of the user to remove
     * @return the position prior to removal
     */
    public int removeReplayPosition(String username) {
        return this.replayPositions.remove(username);
    }

    /**
     * Used to get the list of games that can be replayed
     * @return ArrayList of CheckerGame representing finished (replayable) games
     */
    public ArrayList<CheckersGame> getReplayGames() {
        final ArrayList<CheckersGame> games = new ArrayList<>();
        for ( CheckersGame game : this.games.values() ) {
            if ( game.isGameOver() ) {
                games.add(game);
            }
        }
        return games;
    }

    /**
     * Used to get the list of games that can be spectated
     * @return ArrayList of CheckerGame representing unfinished (spectatable) games
     */
    public ArrayList<CheckersGame> getSpectatorGames() {
        final ArrayList<CheckersGame> games = new ArrayList<>();
        for ( CheckersGame game : this.games.values() ) {
            if ( !game.isGameOver() ) {
                games.add(game);
            }
        }
        return games;
    }

}