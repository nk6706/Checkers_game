package com.webcheckers.ui.board;

import com.webcheckers.model.CheckerBoard;
import com.webcheckers.model.CheckerPiece;
import com.webcheckers.model.CheckersGame;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * BoardView is a UI-level representation of a checker board. The class' 'rows' field is an
 * Iterable ArrayList, and its subsequent contents are what form a '2D' iterable of iterables,
 * which is used by FreeMarker to render a checkerboard to the UI.
 */
public class BoardView implements Iterable<Row> {

    private ArrayList<Row> rows = new ArrayList<>();

    public BoardView(CheckerPiece[][] board) {
        int index = 0;
        for(CheckerPiece[] pieceRow : board) {
            Row row = new Row(index, pieceRow);
            rows.add(row);
            index++;
        }
    }

    /**
     * Gets an Iterator over the BoardView's 'rows' field
     * @return Iterator<Row> an Iterator over the board's rows
     */
    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

}