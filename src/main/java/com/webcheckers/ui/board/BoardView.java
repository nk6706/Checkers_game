package com.webcheckers.ui.board;

import com.webcheckers.model.CheckerBoard;
import com.webcheckers.model.CheckerPiece;
import com.webcheckers.model.CheckersGame;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable<Row> {

    private ArrayList<Row> rows = new ArrayList<>();

    public BoardView(CheckersGame board) {
        int index = 0;
        for(CheckerPiece[] pieceRow : board.getBoard().getBoard()) {
            Row row = new Row(index, pieceRow);
            index++;
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

}