package com.webcheckers.ui.board;

import com.webcheckers.model.CheckerBoard;
import com.webcheckers.model.CheckerPiece;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable<Row> {

    private ArrayList<Row> rows = new ArrayList<>();

    public BoardView(CheckerBoard board) {
        int index = 0;
        for(CheckerPiece[] pieceRow : board.getBoard()) {
            Row row = new Row(index, pieceRow);
            index++;
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

}