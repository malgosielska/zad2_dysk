package com.company;

import java.util.Comparator;
import java.util.List;

public class FCFS {

    private List<Request> requests;
    private int headPosition;
    private int diskLength;


    Comparator<Request> BY_ARRIVALTIME = new Comparator<Request>() {
        @Override
        public int compare(Request r1, Request r2) {
            return r1.getArrivalTime() - r2.getArrivalTime();
        }
    };

    public FCFS(List<Request> requests,int headPosition, int diskLength) {
        this.headPosition = headPosition;
        this.diskLength = diskLength;
        this.requests = requests;
        requests.sort(BY_ARRIVALTIME);
    }

    public void simulate() {
        int time = 0;
        int distanceTravelledByHead = 0;
        int nrOfDoneRequests = 0;
        int waitingTime = 0;
        Request currentRequest = requests.get(0);
        boolean lackOfRequests = false;

        while (!lackOfRequests){

            if (currentRequest==null && requests.get(0).getArrivalTime() <= time){
                currentRequest = requests.get(0);
            }

            if(currentRequest != null && currentRequest.getArrivalTime() <= time){
                if (currentRequest.getPosition() < headPosition){
                    headPosition--;
                    distanceTravelledByHead++;
                } else if (currentRequest.getPosition() > headPosition){
                    headPosition++;
                    distanceTravelledByHead++;
                } else {
                    nrOfDoneRequests++;
                    requests.remove(currentRequest);
                    currentRequest = null;
                    if(requests.isEmpty()){
                      lackOfRequests = true;
                    }
                }
            }

            for(Request request : requests){
                if (request.getArrivalTime() <= time){
                    waitingTime++;
                }
            }

            time++;
        }
        System.out.println("NR OF DONE REQUESTS: " + nrOfDoneRequests);
        System.out.println("TIME: " + time);
        System.out.println("DISTANCE TRAVELLED BY HEAD: " + distanceTravelledByHead);
        System.out.println("AVERAGE WAITING TIME: " + (double)waitingTime/nrOfDoneRequests);

    }
}
