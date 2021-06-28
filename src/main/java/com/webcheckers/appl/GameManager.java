package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;

import java.util.HashMap;

public class GameManager {

    /** All games running on the site right now, key=GameID value=Game */
    private HashMap<Integer, CheckersGame> games;
    /** Id of the last game made, incremented before creating a new game */
    private int lastId = 0;

    public GameManager(HashMap<Integer, CheckersGame> games) {
        this.games = games;
    }

    public CheckersGame newGame(Player redPlayer, Player whitePlayer) {
        CheckersGame game = new CheckersGame(lastId++, redPlayer, whitePlayer);
        games.put(game.getId(), game);
        return game;
    }

}