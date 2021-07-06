package com.webcheckers.model;

import com.webcheckers.model.Player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Model-tier")
public class PlayerTest {

    private Player CuT;
    private java.lang.Object TestObject;

    @BeforeEach
    public void setup(){
        CuT = new Player("user1");
    }

    @Test
    public void testPlayerCreated(){
        Assertions.assertEquals( "{Username: user1; Game ID: -1}", CuT.toString());
    }

    @Test
    void testGameNotStarted(){
        Assertions.assertTrue(CuT.inGame()==false);
    }


    @Test
    void testGameStarted(){
        CuT.setGameID(1);
        Assertions.assertTrue(CuT.inGame());
    }


    @Test
    void testGameStartsAndEnds(){
        Assertions.assertTrue(CuT.inGame()==false);
        CuT.setGameID(1);
        Assertions.assertEquals(1, CuT.getGameID());
        Assertions.assertTrue(CuT.inGame());
        CuT.setGameID(-1);
        Assertions.assertEquals(-1, CuT.getGameID());
        Assertions.assertFalse((CuT.inGame()));
    }

    @Test
    void testEqualsTrue(){
        TestObject = new Player("user1");
        Assertions.assertTrue(CuT.equals(TestObject));
    }


    @Test
    void testObjectEqual(){
        TestObject = new Player("user2");
        Assertions.assertFalse(CuT.equals(TestObject));
    }


    @Test
    void testHashCodeOfSameObject(){
        TestObject = new Player("user1");
        Assertions.assertEquals(TestObject.hashCode(), CuT.hashCode());
    }

    @Test
    void testHashCodeOfDifferentObject(){
        TestObject = new Player("user2");
        Assertions.assertFalse(TestObject.hashCode() == CuT.hashCode());
    }

}
