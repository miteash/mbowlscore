package com.mt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    // Class Main
    public static final Logger logger = LogManager.getLogger(Main.class);
    public static MBGame mGame = new MBGame();
    public static void main(String[] args) {
        logger.info("Start Program to score Bowling game");
        mGame.makeMBGame();
        try {
            mGame.playGameConsole();
        }catch (Exception e){
            logger.warn("IO Exception happened" + e.getMessage());
        }
        logger.info("Finished Program to score Bowling game");
    }
}