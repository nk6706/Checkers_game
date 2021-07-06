package com.webcheckers.model;

import com.webcheckers.model.Player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Model-tier")
public class PlayerTest {

    @BeforeEach
    public void setup(){
        Player CuT = new Player("user1");
    }

    @Test
    public void testPlayerCreated(){
        Player CuT = new Player("user1");
        Assertions.assertEquals( "{Username: user1; Game ID: -1}", CuT.toString());
    }

    @Test
    void gameNotStarted(){
        Player Cut = new Player("user1");
        Assertions.assertTrue(Cut.inGame()==false);
    }


    @Test
    void testGameStarted(){
        Player CuT = new Player("user1");
        CuT.setGameID(1);
        Assertions.assertTrue(CuT.inGame());
    }


    @Test
    void testGameStartsAndEnds(){
        Player Cut = new Player("user1");
        Assertions.assertTrue(Cut.inGame()==false);
        Cut.setGameID(1);
        Assertions.assertTrue(Cut.inGame());
        Cut.setGameID(-1);
        Assertions.assertTrue((Cut.inGame())==false);
    }




}
