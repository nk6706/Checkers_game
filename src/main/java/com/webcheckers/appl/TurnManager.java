package com.webcheckers.appl;

import com.webcheckers.model.Move;

import java.util.ArrayList;
import java.util.HashMap;

public class TurnManager {

    HashMap<Integer, ArrayList<Move>> turns;

    public TurnManager() {
        this.turns = new HashMap<>();
    }
}
