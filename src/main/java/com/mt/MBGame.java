package com.mt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;

import static com.mt.Main.logger;

public class MBGame {
    // Class MBGame
    public ArrayList<Frame> gameFrames;
    public FrameList gList;
    public ConsolePrintScore consolePrint = new ConsolePrintScore();
    BufferedReader reader;


    public void makeMBGame() {
        gList = new FrameList();
        gameFrames = gList.createFrames();
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
    public Frame getFrame(int i){
        return gameFrames.get(i);

    }
    int getFallenPinCount(int max) {
        boolean validValue = false;
        int pinFallenCount = 0;
        int min = 0;
        logger.info("Entering Function getFallenPinCount");
        do {
            try {
                System.out.println("Enter Pins Fallen - a value from " + min + " to " +  max);

                pinFallenCount = Integer.parseInt(reader.readLine());
            } catch (Exception e) {
                System.out.println("IOException has been occured! now you need to start over " + e.getMessage());
                logger.fatal("IOException has occured! Need to start the program again");
                logger.warn("IOException has occured! Need to start the program again" + e.getMessage());
                System.exit(-1);
            }
            if( (pinFallenCount >= min) && (pinFallenCount <= max))
                validValue = true;
        } while(!validValue);
        logger.info("Exiting Function getFallenPinCount");
        return pinFallenCount;
    }
    public void playGameConsole() {
            int pinFallen;
            int min = 0;
            int max;
            for (int i = 0; i <= 9; i++) {
                Frame currentFrame = gameFrames.get(i);
                for (Throw thisThrow : currentFrame.getThrows()) {
                    max = 10;
                    if (!thisThrow.done) {
                        if (thisThrow.throwNumber == 1) {
                            System.out.println("Game Frame # " + (i + 1) + " Throw # " + thisThrow.throwNumber);
                            pinFallen = getFallenPinCount( max);
                            setFrameFallenPins(currentFrame, thisThrow.throwNumber, pinFallen);
                        } else if (thisThrow.throwNumber == 2) {
                            if (!currentFrame.lastFrame) {
                                if(!currentFrame.strike) {
                                    System.out.println("Game Frame # " + (i + 1) + " Throw # " + thisThrow.throwNumber);
                                    pinFallen = getFallenPinCount( (10 - currentFrame.getThrows().get(0).getKnockedDownPins()));
                                    setFrameFallenPins(currentFrame, thisThrow.throwNumber, pinFallen);
                                }
                            } else {
                                System.out.println("Game Frame # " + (i + 1) + " Throw # " + thisThrow.throwNumber);
                                if (currentFrame.score == 10) {
                                    pinFallen = getFallenPinCount(max);
                                    System.out.println("Last Frame");
                                    setFrameFallenPins(currentFrame, thisThrow.throwNumber, pinFallen);
                                }else
                                    pinFallen = getFallenPinCount( (10 - currentFrame.getThrows().get(0).getKnockedDownPins()));
                                    setFrameFallenPins(currentFrame, thisThrow.throwNumber, pinFallen);
                                }
                        } else if (thisThrow.throwNumber == 3) {
                           //  System.out.println("Strike = " + currentFrame.strike + " Spare = " + currentFrame.spare + " Score = " + currentFrame.score + " Done = " + currentFrame.done);
                            if ((currentFrame.strike) || (currentFrame.spare)) {
                                System.out.println("Game Frame # " + (i + 1) + " Throw # " + thisThrow.throwNumber);
                                pinFallen = getFallenPinCount( max);
                                setFrameFallenPins(currentFrame, thisThrow.throwNumber, pinFallen);
                            }
                        }
                    }
                    thisThrow.done = true;
                }

                currentFrame.done = true;
                calculateTotalScore();
                ConsolePrintScore.displayScore(gameFrames);


            }

    }
    public boolean setFrameValue(int framenumber, ArrayList<Frame> frames, int throwNumber1, int pinFallenValue1, int throwNumber2, int pinFallenValue2) {
        logger.info("Entering setFrameValue");
        Frame f = frames.get(framenumber);
        boolean rvalue = setFrameFallenPins(f, throwNumber1, pinFallenValue1) && setFrameFallenPins(f, throwNumber2, pinFallenValue2);


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

    public boolean setFrameFallenPins(Frame frame, int throwNumber, int pinFallen){
        logger.info("Entering setFinalFrameFallenPins");
        boolean rvalue = false;
        if(pinFallen == 0){
            // A case where there may be strike earlier or the guy got zero pins
            // do nothing
            rvalue = true;
        } else {
            if (throwNumber == 1) {
                if (pinFallen <= 10) {
                    frame.getThrows().get(0).setKnockedDownPins(pinFallen);
                    frame.getThrows().get(0).done = true;
                    if (pinFallen == 10) {
                        frame.strike = true;
                        frame.score = pinFallen;
                    }
                    rvalue = true;
                }
            } else if (throwNumber == 2) {
                // Frame already is strike
                if (pinFallen <= (10 - frame.getThrows().get(0).getKnockedDownPins())) {
                    frame.getThrows().get(1).setKnockedDownPins(pinFallen);
                    frame.getThrows().get(1).done = true;
                    frame.score = pinFallen + frame.getThrows().get(0).getKnockedDownPins();
                    rvalue = true;

                    if (pinFallen == 10) {
                        if (frame.lastFrame)
                            frame.spare = true;
                    } else if (pinFallen + frame.getThrows().get(0).getKnockedDownPins() == 10) {
                        frame.spare = true;
                    }
                } else if(pinFallen == 10) {
                    if( frame.lastFrame){
                        frame.getThrows().get(1).setKnockedDownPins(pinFallen);
                        frame.getThrows().get(1).done = true;
                        frame.score = pinFallen + frame.getThrows().get(0).getKnockedDownPins();
                    }

                }

            } else if (throwNumber == 3) {
                if ((frame.strike) || (frame.spare) || frame.score == 10) {
                    frame.score += pinFallen;
                    frame.getThrows().get(2).setKnockedDownPins(pinFallen);
                    frame.getThrows().get(2).done = true;
                    rvalue = true;
                }
            }
        }

        logger.info("Exiting setFinalFrameFallenPins");
        return rvalue;
    }


    public int calculateTotalScore() {
        logger.info("Entering calculateTotalScore");
        int frameNum = 0;
        for(Frame frame : gameFrames) {
            if (frame.strike) {
                scoreStrikeFrame(frameNum, frame);
            } else if(frame.spare) {
                scoreSpareFrame(frameNum, frame);
            } else {
                scoreRegularFrame(frameNum, frame);
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
        int score;
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
