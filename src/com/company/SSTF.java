package com.company;

import java.util.Comparator;
import java.util.List;

public class SSTF {

    private final List<Request> requests;
    private int headPosition;
    private final int diskLength;

    Comparator<Request> BY_SEEKTIME = new Comparator<Request>() {
        @Override
        public int compare(Request r1, Request r2) {
            return Math.abs(r1.getPosition() - headPosition) - Math.abs(r2.getPosition() - headPosition);
        }
    };

    public SSTF(List<Request> requests, int headPosition, int diskLength) {
        this.headPosition = headPosition;
        this.diskLength = diskLength;
        this.requests = requests;
        requests.sort(BY_SEEKTIME);
    }

    public void simulate() {
        int time = 0;
        int distanceTravelledByHead = 0;
        int nrOfDoneRequests = 0;
        int waitingTime = 0;
        Request currentRequest = getFirstActive(requests, time);
        String lastMove = "right";
        boolean lackOfRequests = false;

        while (!lackOfRequests){

            if (currentRequest!= null){
                for (int i = 0; i < requests.size(); i++){
                    if (requests.get(i).getArrivalTime() <= time){
                        waitingTime++;
                    }
                }

                if (currentRequest.getPosition() < headPosition){
                    headPosition--;
                    lastMove = "left";
                    distanceTravelledByHead++;
                } else if (currentRequest.getPosition() > headPosition){
                    headPosition++;
                    lastMove = "right";
                    distanceTravelledByHead++;
                } else {
                    nrOfDoneRequests++;
                    requests.remove(currentRequest);
                    if(!requests.isEmpty()){
                        currentRequest = getFirstActive(requests, time);
                        if (lastMove.equals("right")){
                            headPosition++;
                        } else {
                            headPosition --;
                        }
                        distanceTravelledByHead++;
                    } else {
                        lackOfRequests = true;
                    }
                }
            } else {
                currentRequest = getFirstActive(requests, time);
                if (lastMove.equals("right")){
                    headPosition++;
                } else {
                    headPosition --;
                }
                distanceTravelledByHead++;
            }
            time++;
        }

        System.out.println("NR OF DONE REQUESTS: " + nrOfDoneRequests);
        System.out.println("TIME: " + time);
        System.out.println("DISTANCE TRAVELLED BY HEAD: " + distanceTravelledByHead);
        System.out.println("AVERAGE WAITING TIME: " + (double)waitingTime/nrOfDoneRequests);

    }

    public Request getFirstActive(List<Request> requests, int time) {
        requests.sort(BY_SEEKTIME);
        for (Request r : requests)
            if (r.getArrivalTime() <= time){
                return r;
            }
        return null;
    }
}
