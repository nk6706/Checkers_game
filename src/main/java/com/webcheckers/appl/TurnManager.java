package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;

import java.util.ArrayList;
import java.util.HashMap;

public class TurnManager {

    final HashMap<Integer, ArrayList<Move>> turns = new HashMap<>();
    final private GameManager gameManager;

    public TurnManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Add a game to the turns hashmap which keeps track of turns in a game
     * @param gameID the game id to attach to the turns list
     */
    private void addGame(int gameID) {
        this.turns.put(gameID, new ArrayList<>());
    }

    /**
     * Helper method to tell if a game is present in the turns map
     * @param gameID the game id to check for
     * @return true if the game is present, false otherwise
     */
    private boolean hasGame(int gameID) {
        return this.turns.containsKey(gameID);
    }

    /**
     * Helper method to store move
     * @param gameID the game to store the move
     * @param move the move to store
     */
    private void storeMove(int gameID, Move move) {
        this.turns.get(gameID).add(move);
    }

    /**
     * Uses the gameID and a Move data type object to move a piece on the board
     * @param gameID the id of the game to make the move in
     * @param move the move to make (start / end pos)
     */
    public void makeMove(int gameID, Move move) {
        CheckersGame game = gameManager.getGame(gameID);
        if ( !hasGame(gameID) ) {
            addGame(gameID);
        }
        storeMove(gameID, move);
    }


}
