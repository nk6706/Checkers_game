package com.webcheckers.model;

import com.webcheckers.model.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class MoveTest {

    private Move Cut;
    private Position startPosition;
    private Position endPosition;


    @BeforeEach
    public void setup(){
        startPosition = new Position(0,0);
        endPosition = new Position(1,1);
        Cut = new Move(startPosition, endPosition);
    }

    @Test
    public void testGetStart(){
        Assertions.assertEquals(0,Cut.getStart().getRow());
        Assertions.assertEquals(0,Cut.getStart().getCell());
    }

    @Test
    public void testEndStart(){
        Assertions.assertEquals(1,Cut.getEnd().getRow());
        Assertions.assertEquals(1,Cut.getEnd().getCell());
    }


}