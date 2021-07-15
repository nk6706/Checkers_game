package com.webcheckers.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class CheckerPieceTest {

    private CheckerPiece CuT;
    private CheckerPiece.Color color;
    private CheckerPiece.Type type;

    @BeforeEach
    public void setup(){
        color = CheckerPiece.Color.RED;
        type = CheckerPiece.Type.SINGLE;
        CuT = new CheckerPiece(color);
    }

    @Test
    public void testGetColor(){
        Assertions.assertEquals(color,CuT.getColor());
    }

    @Test
    public void getType(){
        Assertions.assertEquals(type,CuT.getType());
    }

    @Test
    public void testIsKing(){
        Assertions.assertFalse(CuT.isKing());
    }

    @Test
    public void testToString(){
        Assertions.assertEquals("R", CuT.toString());

        //Change color
        color = CheckerPiece.Color.WHITE;
        CuT = new CheckerPiece(color);
        Assertions.assertEquals("W",CuT.toString());
    }

}
