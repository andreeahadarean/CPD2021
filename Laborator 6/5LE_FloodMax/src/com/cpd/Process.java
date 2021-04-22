package com.cpd;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Process extends Thread {

    int myID;
    int maxID;
    Status status;
    int rounds;
    Process[] neightbors;
    int diameter;
    CountDownLatch newRound;

    public Process(int myID, int diameter) {
        this.myID = myID;
        this.maxID = myID;
        this.status = Status.UNKNOWN;
        rounds = 0;
        this.diameter = diameter;
    }

    public void setNeighbors(Process... neightbors) {
        this.neightbors = neightbors;
    }

    @Override
    public void run() {
        while (rounds < diameter) {
            newRound = new CountDownLatch(neightbors.length);
            sendMax();
            rounds++;
            try {
                newRound.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
            }
        }
        if (maxID == myID) {
            this.status = Status.LEADER;
            System.out.println("Process " + myID + " is leader");
        } else {
            this.status = Status.NONLEADER;
            System.out.println("Process " + myID + " is a non-leader");
        }
    }

    public synchronized void getMax(int max, int sender) {
        System.out.println("Process " + myID + " received new max: " + max + " from " + sender);
        if (max > maxID) {
            maxID = max;
        }
        newRound.countDown();
    }

    public void sendMax() {
        System.out.println("Process " + myID + " is sending max = " + maxID + " to neighbors @round " + rounds);
        for (Process neighbor : neightbors) {
            neighbor.getMax(maxID, myID);
        }
    }
}
