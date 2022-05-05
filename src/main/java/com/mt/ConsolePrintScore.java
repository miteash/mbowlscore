package com.mt;
import java.util.ArrayList;
import static com.mt.Main.logger;
public class ConsolePrintScore {

    public void displayPinFallen(Frame frame, int frameNum) {
        logger.info("Entering displayPinFallen");
        System.out.print("Frame " + frameNum + "\t\t\t");
        System.out.println();
        for (Throw currentThrow : frame.getThrows()) {
            System.out.print(currentThrow.throwNumber + " ");
            System.out.print("\t\t");
        }
        System.out.println();
        for (Throw currentThrow : frame.getThrows()) {
            if(currentThrow.done) {
                System.out.print(currentThrow.getKnockedDownPins() + " ");
                System.out.print("\t\t");
            }
        }
        System.out.println();
        logger.info("Exiting displayPinFallen");
    }
    public void displayPinFallenScore(ArrayList<Frame> frames)
    {
        logger.info("Entering displayPinFallenScore");
        int i = 1;

        for (Frame frame : frames) {
            if(frame.done)
                System.out.print("Frame " + i++ + "\t\t\t");
        }
        System.out.println();

        System.out.println("Ball Throw # ");
        for (Frame frame : frames) {
            if(frame.done) {
                for (Throw currentThrow : frame.getThrows()) {
                    System.out.print(currentThrow.throwNumber + " ");
                    System.out.print("\t\t");
                }
            }

        }
        System.out.println();

        System.out.println("Pins Fallen");
        for (Frame frame : frames) {
            for (Throw currentThrow : frame.getThrows()) {
                if(currentThrow.done) {
                    System.out.print(currentThrow.getKnockedDownPins() + " ");
                    System.out.print("\t\t");
                }
            }

        }
        System.out.println();
        logger.info("Exiting displayPinFallenScore");
    }
    public static void displayScore (ArrayList<Frame> frames){
        logger.info("Entering displayScore");
        int i = 1;

        for (Frame frame : frames) {
            if((frame.done) || (frame.getThrows().get(0).done))
                System.out.print("Frame " + i++ + "\t\t\t");
        }
        System.out.println();

        System.out.println("Ball Throw # ");
        for (Frame frame : frames) {
            if((frame.done) || (frame.getThrows().get(0).done)) {
                for (Throw currentThrow : frame.getThrows()) {
                    System.out.print(currentThrow.throwNumber + " ");
                    System.out.print("\t\t");
                }
            }

        }
        System.out.println();

        System.out.println("Pins Fallen");
        for (Frame frame : frames) {
            if((frame.done) || (frame.getThrows().get(0).done)){
                for (Throw currentThrow : frame.getThrows()) {
                    if (currentThrow.done) {
                        System.out.print(currentThrow.getKnockedDownPins() + " ");
                        System.out.print("\t\t");
                    }
                }
            }

        }
        System.out.println();

        for(Frame frame : frames) {
            if (frame.done) {
                if (frame.strike)
                    System.out.print("Strike: True" + "\t");
                else
                    System.out.print("Strike: False" + "\t");
            }
        }

        System.out.println();
        for(Frame frame : frames){
            if(frame.done) {
                if (frame.spare)
                    System.out.print("Spare: True " + "\t");
                else
                    System.out.print("Spare: False " + "\t");
            }
        }
        System.out.println();
        System.out.println("Game Score");
        for (Frame frame : frames) {
            if(frame.done)
                System.out.print(frame.totalScore + "\t\t\t\t");
        }

        System.out.println();
        System.out.println();
        logger.info("Exiting displayScore");
    }

}
