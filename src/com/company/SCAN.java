package com.company;

import java.util.List;

public class SCAN {

    private final List<Request> requests;
    private int headPosition;
    private final int diskLength;
    private boolean goingRight;

    public SCAN(List<Request> requests, int headPosition, int diskLength) {
        this.requests = requests;
        this.headPosition = headPosition;
        this.diskLength = diskLength;
        this.goingRight = true;
    }

    public void simulate () {

        int time = 0;
        int distanceTravelledByHead = 0;
        int nrOfDoneRequests = 0;
        int waitingTime = 0;
        int nrOfSwitchedDirections = 0;
        boolean lackOfRequests = false;


        while(!lackOfRequests){

            Request currentRequest = findRequest(requests, time, headPosition);
            if (currentRequest   != null){
                nrOfDoneRequests++;
                requests.remove(currentRequest);
                if (requests.isEmpty()){
                    lackOfRequests = true;
                }
            }

            if (goingRight){
                headPosition++;
            } else {
                headPosition--;
            }

            distanceTravelledByHead++;

            if(headPosition == diskLength && goingRight){
                nrOfSwitchedDirections++;
                setGoingRight(false);
            }
            if(headPosition == 0 && !goingRight){
                nrOfSwitchedDirections++;
                setGoingRight(true);
            }

            for (Request request : requests){
                if (request.getArrivalTime() <= time){
                    waitingTime++;
                }
            }
            time++;
        }

        System.out.println("NR OF DONE REQUESTS: " + nrOfDoneRequests);
        System.out.println("NR OF SWITCHED DIRECTIONS: " + nrOfSwitchedDirections);
        System.out.println("TIME: " + time);
        System.out.println("DISTANCE TRAVELLED BY HEAD: " + distanceTravelledByHead);
        System.out.println("AVERAGE WAITING TIME: " + (double)waitingTime/nrOfDoneRequests);

    }

    public boolean isGoingRight() {
        return goingRight;
    }

    public void setGoingRight(boolean goingRight) {
        this.goingRight = goingRight;
    }

    public Request findRequest(List<Request> requests, int time, int position){
        for (Request r : requests){
            if (r.getArrivalTime() <= time && r.getPosition() == position){
                return r;
            }
        }
        return null;
    }
}
