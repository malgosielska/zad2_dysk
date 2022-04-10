package com.company;

import java.util.Comparator;
import java.util.List;

public class EDF {

    private final List<RealTimeRequest> requests;
    Comparator<RealTimeRequest> BY_ARRIVALTIME = new Comparator<RealTimeRequest>() {
        @Override
        public int compare(RealTimeRequest r1, RealTimeRequest r2) {
            return r1.getArrivalTime() - r2.getArrivalTime();
        }
    };
    private int headPosition;
    private int diskLength;

    public EDF(List<RealTimeRequest> requests, int headPosition, int diskLength) {
        this.headPosition = headPosition;
        this.diskLength = diskLength;
        this.requests = requests;
        requests.sort(BY_ARRIVALTIME);
    }

    public void simulate() {

        int time = 0;
        int distanceTravelledByHead = 0;
        int nrOfDoneRequests = 0;
        int nrOfMissedRequest = 0;
        int waitingTime = 0;
        RealTimeRequest current = null;
        boolean lackOfRequests = false;


        while (!lackOfRequests) {

            if (anyDeadlines(requests, time)) {
                if (current == null) {
                    current = findShortestDeadline(requests, time, headPosition);
                }
            } else {
                if (current == null) {
                    current = getFirstNormalRequest(requests, time, headPosition);
                }
            }

            if (current != null) {
                if (current.isRealTime()) {
                    if (current.getDeadline() >= 0) {
                        current.setDeadline(current.getDeadline() - 1);
                        if (current.getPosition() > headPosition) {
                            headPosition++;
                            distanceTravelledByHead++;
                        } else if (current.getPosition() < headPosition) {
                            headPosition--;
                            distanceTravelledByHead++;
                        } else {
                            nrOfDoneRequests++;
                            requests.remove(current);
                            current = null;
                        }
                    } else {
                        nrOfMissedRequest++;
                        requests.remove(current);
                        current = null;
                    }
                } else {
                    if (current.getPosition() < headPosition) {
                        headPosition--;
                        distanceTravelledByHead++;
                    } else if (current.getPosition() > headPosition) {
                        headPosition++;
                        distanceTravelledByHead++;
                    } else {
                        nrOfDoneRequests++;
                        requests.remove(current);
                        current = null;
                    }
                }
                if (requests.isEmpty()) {
                    lackOfRequests = true;
                }
                for (RealTimeRequest request : requests) {
                    if (request.getArrivalTime() <= time) {
                        waitingTime++;
                    }
                }

            }
            time++;
        }

        System.out.println("NR OF DONE REQUESTS: " + nrOfDoneRequests);
        System.out.println("NR OF MISSED REQUESTS: " + nrOfMissedRequest);
        System.out.println("TIME: " + time);
        System.out.println("DISTANCE TRAVELLED BY HEAD: " + distanceTravelledByHead);
        System.out.println("AVERAGE WAITING TIME: " + (double) waitingTime / (nrOfDoneRequests + nrOfMissedRequest));
    }

    public RealTimeRequest findShortestDeadline(List<RealTimeRequest> requests, int time, int headPosition) {
        requests.sort(BY_ARRIVALTIME);
        RealTimeRequest shortestDeadlineRequest = null;
        int min = Integer.MAX_VALUE;
        for (RealTimeRequest request : requests) {
            if (request.getArrivalTime() <= time && request.isRealTime()) {
                if (request.getDeadline() < min) {
                    min = request.getDeadline();
                    shortestDeadlineRequest = request;
                }
            }
        }

        return shortestDeadlineRequest;
    }

    public RealTimeRequest getFirstNormalRequest(List<RealTimeRequest> requests, int time, int headPosition) {
        requests.sort(BY_ARRIVALTIME);
        RealTimeRequest firstRequest = null;
        for (RealTimeRequest request : requests) {
            if (request.getArrivalTime() <= time && !request.isRealTime()) {
                firstRequest = request;
            }
        }
        return firstRequest;
    }

    public boolean anyDeadlines(List<RealTimeRequest> requests, int time) {
        boolean deadlines = false;
        for (RealTimeRequest request : requests) {
            if (request.isRealTime()) {
                deadlines = true;
                break;
            }
        }
        return deadlines;
    }
}
