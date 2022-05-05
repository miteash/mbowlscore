package com.mt;
import java.util.ArrayList;

import static com.mt.Main.logger;

public class FrameList {
    // Class Frame
    public  ArrayList<Frame> createFrames() {
        logger.info("Entering createFrames");
        Frame frame;
        ArrayList<Frame> createFrames = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            if (i == 9) {
                frame = createLastFrame();
            } else {
                frame = createFrame();
            }

            createFrames.add(frame);
        }
        logger.info("Exiting createFrames");
        return createFrames;
    }

    public Frame createFrame() {
        logger.info("Entering createFrame");
        Frame f = new Frame();
        f.mthrows = createThrows(2);
        f.lastFrame = false;
        f.score = 0;
        f.strike = false;
        f.done = false;
        f.spare = false;
        f.totalScore = 0;
        logger.info("Exiting createFrame");
        return f;
    }


    public Frame createLastFrame() {
        logger.info("Entering createLastFrame");
        Frame f = new Frame();
        f.mthrows = createThrows(3);
        f.lastFrame = true;
        f.score = 0;
        f.strike = false;
        f.done = false;
        f.spare = false;
        f.totalScore = 0;
        logger.info("Exiting createLastFrame");
        return f;
    }
    public ArrayList<Throw> createThrows(int numThrows) {
        logger.info("Entering createThrows");
        ArrayList<Throw> mthrow = new ArrayList<>();
        int indexThrow = 0;
        while (mthrow.size() < numThrows) {
            Throw newThrow = new Throw();
            newThrow.throwNumber = (++indexThrow);
            mthrow.add(newThrow);
        }
        logger.info("Entering createThrows");
        return (mthrow);
    }

    public boolean setFrameFallenPins(Frame frame, int throwNumber, int pinFallen) {
        logger.info("Entering setFrameFallenPins");
        boolean rvalue = false;
        if (throwNumber == 1) {
            if (pinFallen <= 10) {
                frame.getThrows().get(throwNumber).setKnockedDownPins(pinFallen);
                if (pinFallen == 10) {
                    frame.strike = true;
                    frame.score = pinFallen;
                    rvalue = true;
                }
            }
        } else if (throwNumber == 2) {
            if (pinFallen <= (10 - frame.getThrows().get(0).getKnockedDownPins())) {
                frame.getThrows().get(throwNumber).setKnockedDownPins(pinFallen);
                rvalue = true;
                if (pinFallen + frame.getThrows().get(1).getKnockedDownPins() == 10) {
                    frame.spare = true;
                }
                frame.score = frame.getThrows().get(1).getKnockedDownPins() + pinFallen;
                System.out.println("2 Score = " + frame.score);
            }
        }else if (throwNumber == 3) {
                if (pinFallen <= 10) {
                    if ((frame.score == 10) || (frame.score == 20)) {
                        // Case of two strike or one strike
                        // we can have pinFallen value of 0 to 10;
                        frame.score = frame.score + pinFallen;
                        rvalue = true;
                    }
                }
        }

        logger.info("Exiting setFrameFallenPins");
        return rvalue;
    }
}
