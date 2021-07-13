package com.webcheckers.appl;

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
     * Helper method to store move
     * @param gameID the game to store the move
     * @param move the move to store
     */
    private void storeMove(int gameID, Move move) {
        this.turns.get(gameID).add(move);
    }

    public void makeMove(Move move) {

    }


}
