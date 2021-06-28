package com.webcheckers.ui.board;

import com.webcheckers.model.CheckerPiece;

import java.util.ArrayList;
import java.util.Iterator;

public class Row implements Iterable<Space> {

    private int index;
    private ArrayList<Space> spaces = new ArrayList<>();

    public Row(int num, CheckerPiece[] row){
        this.index = num;
        for(int i = 0; i < row.length; i++) {
            CheckerPiece piece = row[i];
            spaces.add(new Space(i, piece, isDark(num, i)));
        }
    }

    /**
     * Helper method to determine if the space is light or dark
     * @param row row number
     * @param col col number
     * @return true if space is dark false otherwise
     */
    private boolean isDark(int row, int col) {
        if(row%2 == 0) {
            if (col%2 != 0)
                return true;
        } else {
            if (col%2 == 0)
                return true;
        }
        return false;
    }

    /**
     * getter for index
     * @return int index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}