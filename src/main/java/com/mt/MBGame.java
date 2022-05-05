package com.mt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;

import static com.mt.Main.logger;

public class MBGame {
    public ArrayList<Frame> gameFrames;
    public FrameList gList;
    BufferedReader reader;


    public void makeMBGame() {
        gList = new FrameList();
        gameFrames = gList.CreateFrames();
    }
    public Frame getFrame(int i){
        return gameFrames.get(i);

    }
    public boolean setFrameValue(int framenumber, ArrayList<Frame> frames, int throwNumber1, int pinFallenValue1, int throwNumber2, int pinFallenValue2) {
        logger.info("Entering setFrameValue");
        Frame f = frames.get(framenumber);
        boolean rvalue = false;
        if (setFrameFallenPins(f, throwNumber1, pinFallenValue1)) {
            rvalue = true;
        }
        if (setFrameFallenPins(f, throwNumber2, pinFallenValue2)) {
            rvalue = true;
        }
        logger.info("Exiting setFrameValue");
        return rvalue;
    }

    public boolean setFinalFrameValue( int throwNumber1, int pinFallenValue1, int throwNumber2, int pinFallenValue2, int throwNumber3, int pinFallenValue3){
        boolean rvalue = false;
        logger.info("Entering setFinalFrameValue");
        Frame f = gameFrames.get(9);
        if (setFrameFallenPins(f, throwNumber1, pinFallenValue1)) {
            rvalue = true;
        }
        if (setFrameFallenPins(f, throwNumber2, pinFallenValue2)) {
            rvalue = true;
        }
        if (setFrameFallenPins(f, throwNumber3, pinFallenValue3) ) {
            rvalue = true;
        }
        logger.info("Exiting setFinalFrameValue");
        return rvalue;
    }

    public boolean setFrameFallenPins(Frame frame, int tryNumber, int pinFallen){
        logger.info("Entering setFinalFrameFallenPins");
        boolean rvalue = false;
        if(pinFallen == 0){
            // A case where there may be strike earlier or the guy got zero pins
            // do nothing
            rvalue = true;
        } else {
            if (tryNumber == 1) {
                if (pinFallen <= 10) {
                    frame.getThrows().get(0).setKnockedDownPins(pinFallen);
                    if (pinFallen == 10) {
                        frame.strike = true;
                        frame.score = pinFallen;
                        rvalue = true;
                    }
                }
            } else if (tryNumber == 2) {
                // Frame already is strike
                if (pinFallen <= (10 - frame.getThrows().get(0).getKnockedDownPins())) {
                    frame.getThrows().get(1).setKnockedDownPins(pinFallen);
                    frame.score = pinFallen + frame.getThrows().get(0).getKnockedDownPins();
                    rvalue = true;
                    if (pinFallen == 10) {
                        frame.strike = true;

                    } else if (pinFallen + frame.getThrows().get(0).getKnockedDownPins() == 10) {
                        frame.spare = true;
                    }
                } else if (frame.lastFrame) {
                    // we need to treat this differently
                    if (frame.strike) {
                        frame.score += pinFallen;
                    }
                }
            } else if (tryNumber == 3){
                if((frame.strike) || (frame.spare)) {
                    frame.score += pinFallen;
                    frame.getThrows().get(2).setKnockedDownPins(pinFallen);
                    rvalue = true;
                }
            }
        }
        logger.info("Exiting setFinalFrameFallenPins");
        return rvalue;
    }


    public int calculateTotalScore () {
        logger.info("Entering calculateTotalScore");
        int frameNum = 0;
        for(Frame frame : gameFrames) {
            if ((!frame.strike) && (!frame.spare)) {
                // It is a regular frame
                scoreRegularFrame(frameNum, frame);
            } else if (frame.spare) {
                scoreSpareFrame(frameNum, frame);
            } else if (frame.strike) {
                scoreStrikeFrame(frameNum, frame);
            }
            frameNum += 1;
        }
        Frame lastFrame = gameFrames.get(9);
        logger.info("Exiting calculateTotalScore");
        return lastFrame.totalScore;
    }

    private void scoreRegularFrame(int frameNumber, Frame frame) {
        logger.info("Entering scoreRegular Fame");
        int priorFrameScore = getFrameScore(frameNumber, -1);
        frame.totalScore = priorFrameScore + frame.score;
        logger.info("Exiting scoreRegularFrame");
    }


    private void scoreSpareFrame(int frameNumber,  Frame frame) {
        logger.info("Entering scoreSpareFrame");
        int priorFrameScore = getFrameScore(frameNumber, -1);
        int score = 0;
        if(frameNumber == 9){
            score = priorFrameScore + frame.score;
        } else {
            Frame nextFrame = getFrame(frameNumber + 1);
            score = priorFrameScore + frame.score + nextFrame.getThrows().get(0).getKnockedDownPins();
        }
        logger.info("Exiting scoreSpareFrame");
        frame.totalScore = score;
    }

    /**
     * Sets the score on a frame where the bowler bowled a strike.
     *
     * @param frameNumber the frame number for the strike frame.
     * @param frame       the bowling frame for the strike frame.
     */
    private void scoreStrikeFrame( int frameNumber, Frame frame) {
        logger.info("Entering scoreStrikeFrame");
        int score = 0;
        final int priorFrameScore = getFrameScore(frameNumber, -1);
        if(frameNumber == 9) {
            // We are in last frame
            score = priorFrameScore + frame.score;
        }
        else {
            Frame nextFrame = getFrame(frameNumber +  1);
            if ((!nextFrame.spare) && (!nextFrame.strike)) {
                score = priorFrameScore + frame.score + nextFrame.score;

            } else if (nextFrame.spare) {
                score = priorFrameScore + frame.score + nextFrame.score;
            } else if (nextFrame.strike) {
                // get the next ball after nextFrame (two balls from the original ball)
                if(frameNumber == 8){
                    score = priorFrameScore + frame.score + 10 + 10;
                } else {
                    Frame twoFramesAhead = getFrame(frameNumber + 2);
                    if (twoFramesAhead.strike) {
                        // original strike(10) + the second strike(10) + the third strike(10)
                        score = priorFrameScore + frame.score + nextFrame.score + 10;
                    } else {
                        // original strike(10) + the second strike(10) + the first ball.
                        score = priorFrameScore + frame.score + nextFrame.score + twoFramesAhead.getThrows().get(0).getKnockedDownPins();
                    }
                }
            }
        }
        frame.totalScore = score;
        logger.info("Exiting scoreStrikeFrame");
    }

    private int getFrameScore(int frameNumber, int frameAhead) {
        logger.info("Entering getFrameScore");
        int frameNum = frameNumber + frameAhead;
        int frameScore = 0;
        if(frameNum >= 0)
        {
            Frame frame = gameFrames.get(frameNum);
            frameScore = frame.totalScore;
        }
        logger.info("Exiting getFrameScore");
        return frameScore;
    }
}
