package com.mt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MBGameTest {
    MBGame gameTest;

    @BeforeEach
    void setUp() {
        gameTest = new MBGame();
        gameTest.makeMBGame();
    }

    @AfterEach
    void tearDown() {
    }


    @DisplayName("Assert a frame input if combination of two throw values in permitted range then return true else false")
    @Test
    void setFrameValue() {
        assertTrue(gameTest.setFrameValue(1, gameTest.gameFrames,1,9,2,1));

    }


    @DisplayName("Assert a throw input if value in permissible range then return true else false")

    @Test
    void setFinalFrameValue() {
        assertEquals(true,gameTest.setFrameFallenPins(gameTest.getFrame(0), 1, 10));

    }
    @DisplayName("Assert a throw input if value in permissible range then return true else false")

    @Test

    void setFrameFallenPins() {
        assertEquals(true, gameTest.setFinalFrameValue( 1, 10,  2, 10, 3, 10));

    }

    @DisplayName("Assert a Total Score of 300 for getting a strike in all frames")

    @Test
    void calculateTotalScore() {
        assertTrue(gameTest.setFrameValue(0, gameTest.gameFrames,1,10,2,0));
        assertTrue(gameTest.setFrameValue(1, gameTest.gameFrames,1,10,2,0));
        assertTrue(gameTest.setFrameValue(2, gameTest.gameFrames,1,10,2,0));
        assertTrue(gameTest.setFrameValue(3, gameTest.gameFrames,1,10,2,0));
        assertTrue(gameTest.setFrameValue(4, gameTest.gameFrames,1,10,2,0));
        assertTrue(gameTest.setFrameValue(5, gameTest.gameFrames,1,10,2,0));
        assertTrue(gameTest.setFrameValue(6, gameTest.gameFrames,1,10,2,0));
        assertTrue(gameTest.setFrameValue(7, gameTest.gameFrames,1,10,2,0));
        assertTrue(gameTest.setFrameValue(8, gameTest.gameFrames,1,10,2,0));
        assertTrue(gameTest.setFinalFrameValue(1,10,2,10, 3, 10));
        assertEquals(300, gameTest.calculateTotalScore());
    }

    @DisplayName("Assert a Total Score of 150 for getting a spare in all frames")
    @Test
    void calculateTotalScore1() {
        assertTrue(gameTest.setFrameValue(0, gameTest.gameFrames,1,5,2,5));
        assertTrue(gameTest.setFrameValue(1, gameTest.gameFrames,1,5,2,5));
        assertTrue(gameTest.setFrameValue(2, gameTest.gameFrames,1,5,2,5));
        assertTrue(gameTest.setFrameValue(3, gameTest.gameFrames,1,5,2,5));
        assertTrue(gameTest.setFrameValue(4, gameTest.gameFrames,1,5,2,5));
        assertTrue(gameTest.setFrameValue(5, gameTest.gameFrames,1,5,2,5));
        assertTrue(gameTest.setFrameValue(6, gameTest.gameFrames,1,5,2,5));
        assertTrue(gameTest.setFrameValue(7, gameTest.gameFrames,1,5,2,5));
        assertTrue(gameTest.setFrameValue(8, gameTest.gameFrames,1,5,2,5));
        assertTrue(gameTest.setFinalFrameValue(1,5,2,5, 3, 5));
        assertEquals(150, gameTest.calculateTotalScore());
    }
    @DisplayName("Assert a Total Score of 20 for getting a 1 in each throw in all frames")
    @Test
    void calculateTotalScore2() {
        assertTrue(gameTest.setFrameValue(0, gameTest.gameFrames,1,1,2,1));
        assertTrue(gameTest.setFrameValue(1, gameTest.gameFrames,1,1,2,1));
        assertTrue(gameTest.setFrameValue(2, gameTest.gameFrames,1,1,2,1));
        assertTrue(gameTest.setFrameValue(3, gameTest.gameFrames,1,1,2,1));
        assertTrue(gameTest.setFrameValue(4, gameTest.gameFrames,1,1,2,1));
        assertTrue(gameTest.setFrameValue(5, gameTest.gameFrames,1,1,2,1));
        assertTrue(gameTest.setFrameValue(6, gameTest.gameFrames,1,1,2,1));
        assertTrue(gameTest.setFrameValue(7, gameTest.gameFrames,1,1,2,1));
        assertTrue(gameTest.setFrameValue(8, gameTest.gameFrames,1,1,2,1));
        assertTrue(gameTest.setFinalFrameValue(1,1,2,1, 3, 0));
        assertEquals(20, gameTest.calculateTotalScore());
    }

    @Test
    @DisplayName("Assert a Total Score of 40 for getting a 2 in each throw in all frames")
    void calculateTotalScore3() {
        assertTrue(gameTest.setFrameValue(0, gameTest.gameFrames,1,2,2,2));
        assertTrue(gameTest.setFrameValue(1, gameTest.gameFrames,1,2,2,2));
        assertTrue(gameTest.setFrameValue(2, gameTest.gameFrames,1,2,2,2));
        assertTrue(gameTest.setFrameValue(3, gameTest.gameFrames,1,2,2,2));
        assertTrue(gameTest.setFrameValue(4, gameTest.gameFrames,1,2,2,2));
        assertTrue(gameTest.setFrameValue(5, gameTest.gameFrames,1,2,2,2));
        assertTrue(gameTest.setFrameValue(6, gameTest.gameFrames,1,2,2,2));
        assertTrue(gameTest.setFrameValue(7, gameTest.gameFrames,1,2,2,2));
        assertTrue(gameTest.setFrameValue(8, gameTest.gameFrames,1,2,2,2));
        assertTrue(gameTest.setFinalFrameValue(1,2,2,2, 3, 0));
        assertEquals(40, gameTest.calculateTotalScore());
    }
}