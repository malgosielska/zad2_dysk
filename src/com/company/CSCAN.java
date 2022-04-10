package com.company;

import java.util.Comparator;
import java.util.List;

public class CSCAN {

    private final List<Request> requests;
    private final int diskLength;
    Comparator<Request> BY_ARRIVALTIME = new Comparator<Request>() {
        @Override
        public int compare(Request r1, Request r2) {
            return r1.getArrivalTime() - r2.getArrivalTime();
        }
    };
    private int headPosition;

    public CSCAN(List<Request> requests, int headPosition, int diskLength) {
        this.requests = requests;
        this.headPosition = headPosition;
        this.diskLength = diskLength;
        requests.sort(BY_ARRIVALTIME);
    }

    public void simulate() {
        int time = 0;
        int distanceTravelledByHead = 0;
        int nrOfDoneRequests = 0;
        int waitingTime = 0;
        int nrOfMovements = 0;
        boolean lackOfRequests = false;

        while (!lackOfRequests) {

            Request currentRequest;
            if ((currentRequest = findRequest(requests, time, headPosition)) != null) {
                nrOfDoneRequests++;
                requests.remove(currentRequest);
                if (requests.isEmpty()) {
                    lackOfRequests = true;
                }
            }

            headPosition++;
            distanceTravelledByHead++;

            if (headPosition == diskLength) {
                nrOfMovements++;
                this.headPosition = 0;
            }

            for (Request request : requests) {
                if (request.getArrivalTime() <= time) {
                    waitingTime++;
                }
            }
            time++;
        }
        System.out.println("NR OF DONE REQUESTS: " + nrOfDoneRequests);
        System.out.println("NR OF MOVEMENTS: " + nrOfMovements);
        System.out.println("TIME: " + time);
        System.out.println("DISTANCE TRAVELLED BY HEAD: " + distanceTravelledByHead);
        System.out.println("AVERAGE WAITING TIME: " + (double) waitingTime / nrOfDoneRequests);
    }

    public Request findRequest(List<Request> requests, int time, int position) {
        for (Request r : requests) {
            if (r.getArrivalTime() <= time && r.getPosition() == position) {
                return r;
            }
        }
        return null;
    }

}
