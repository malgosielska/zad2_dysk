package com.company;

public class Main {

    public static void main(String[] args) {

        Generator generator = new Generator();

        System.out.println("\nFCFS\n");
        FCFS fcfs = new FCFS(generator.fcfsRequests(), 50, 3000);
        fcfs.simulate();

        System.out.println("\nSSTF\n");
        SSTF sstf = new SSTF(generator.sstfRequests(), 50, 3000);
        sstf.simulate();

        System.out.println("\nSCAN\n");
        SCAN scan = new SCAN(generator.scanRequests(), 50, 3000);
        scan.simulate();

        System.out.println("\nC - SCAN\n");
        CSCAN cscan = new CSCAN(generator.cscanRequests(), 50, 3000);
        cscan.simulate();

        System.out.println("\nEDF\n");
        EDF edf = new EDF(generator.edfRequests(), 50, 100);
        edf.simulate();

        System.out.println("\nFD - SCAN\n");
        FDSCAN fdscan = new FDSCAN(generator.fdscanRequests(), 50, 100);
        fdscan.simulate();

    }
}
