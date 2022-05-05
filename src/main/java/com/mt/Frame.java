package com.mt;

import java.util.ArrayList;

public class Frame {
    public int score = 0;

    public ArrayList<Throw> mthrows = new ArrayList<>();
    public boolean strike = false;
    public boolean spare = false;
    public int totalScore = 0;
    public boolean done = false;
    public boolean lastFrame = false;

    public ArrayList<Throw> getThrows() {
        return mthrows;
    }

    public void setTries(ArrayList<Throw> athrows) {
        this.mthrows = athrows;
    }
}
