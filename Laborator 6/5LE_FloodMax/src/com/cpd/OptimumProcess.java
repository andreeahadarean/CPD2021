package com.cpd;

public class OptimumProcess extends Process {

    boolean newInfo = true;

    public OptimumProcess(int myID, int diameter) {
        super(myID, diameter);
    }

    public synchronized void getMax(int max, int sender) {
        System.out.println("Process " + myID + " received new max: " + max + " from " + sender);
        if (max > maxID) {
            maxID = max;
            newInfo = true;
        }
        newRound.countDown();
    }

    public void sendMax() {
        if (newInfo) {
            newInfo = false;
            System.out.println("Process " + myID + " is sending max = " + maxID + " to neighbors @round " + rounds);
            for (Process neighbor : neightbors) {
                neighbor.getMax(maxID, myID);
            }
        }

    }
}
