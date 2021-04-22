package com.cpd;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        ArrayList<Process> processes = new ArrayList<>();
//        processes.add(new Process(0, 2));
//        processes.add(new Process(1, 2));
//        processes.add(new Process(2, 2));
//        processes.add(new Process(3, 2));
//        processes.get(0).setNeighbors(processes.get(1), processes.get(3));
//        processes.get(1).setNeighbors(processes.get(0), processes.get(2));
//        processes.get(2).setNeighbors(processes.get(1), processes.get(3));
//        processes.get(3).setNeighbors(processes.get(0), processes.get(2));
//        for (Process p : processes) {
//            p.start();
//        }

        //test optimum processes;
        ArrayList<OptimumProcess> processes2 = new ArrayList<>();
        processes2.add(new OptimumProcess(0, 2));
        processes2.add(new OptimumProcess(1, 2));
        processes2.add(new OptimumProcess(2, 2));
        processes2.add(new OptimumProcess(3, 2));
        processes2.get(0).setNeighbors(processes2.get(1), processes2.get(3));
        processes2.get(1).setNeighbors(processes2.get(0), processes2.get(2));
        processes2.get(2).setNeighbors(processes2.get(1), processes2.get(3));
        processes2.get(3).setNeighbors(processes2.get(0), processes2.get(2));
        for (OptimumProcess p : processes2) {
            p.start();
        }

    }
}
