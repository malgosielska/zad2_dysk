package com.company;

import java.util.Comparator;
import java.util.List;

public class FDSCAN {

    private final List<RealTimeRequest> requests;
    Comparator<RealTimeRequest> BY_ARRIVALTIME = new Comparator<RealTimeRequest>() {
        @Override
        public int compare(RealTimeRequest r1, RealTimeRequest r2) {
            return r1.getArrivalTime() - r2.getArrivalTime();
        }
    };
    private int headPosition;
    private int diskLength;

    public FDSCAN(List<RealTimeRequest> requests, int headPosition, int diskLength) {
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
        int nrOfDoneByGoingThrough = 0;
        int waitingTime = 0;
        RealTimeRequest current = null;
        boolean lackOfRequests = false;

        while (!lackOfRequests) {

            if (anyDeadlines(requests, time)) {
                if (current != null) {
                    if (!current.isRealTime()) {
                        current = findDoableRequest(requests, time, headPosition);
                    }
                } else {
                    current = findDoableRequest(requests, time, headPosition);
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

                RealTimeRequest done;
                if ((done = findGoingThroughRequest(requests, time, headPosition)) != null && done != current) {
                    nrOfDoneByGoingThrough++;
                    nrOfDoneRequests++;
                    requests.remove(done);
                }

                if (requests.isEmpty()) {
                    lackOfRequests = true;
                }

                for (int i = 0; i < requests.size(); i++) {
                    if (requests.get(i).getArrivalTime() <= time) {
                        if (requests.get(i).isRealTime() && requests.get(i) != current) {
                            if (requests.get(i).getDeadline() < 0) {
                                nrOfMissedRequest++;
                                requests.remove(requests.get(i));
                            } else {
                                requests.get(i).setDeadline(requests.get(i).getDeadline() - 1);
                            }
                        }
                    }
                    waitingTime++;
                }
            }
            time++;
        }
        System.out.println("NR OF DONE REQUESTS: " + nrOfDoneRequests);
        System.out.println("NR OF MISSED REQUESTS: " + nrOfMissedRequest);
        System.out.println("NR OF REQUESTS DONE BY GOING THROUGH: " + nrOfDoneByGoingThrough);
        System.out.println("TIME: " + time);
        System.out.println("DISTANCE TRAVELLED BY HEAD: " + distanceTravelledByHead);
        System.out.println("AVERAGE WAITING TIME: " + (double) waitingTime / (nrOfDoneRequests + nrOfMissedRequest));
    }


    public RealTimeRequest findDoableRequest(List<RealTimeRequest> requests, int time, int headPosition) {
        requests.sort(BY_ARRIVALTIME);
        RealTimeRequest furthestRequest = getFirstNormalRequest(requests, time, headPosition);
        int biggestDistance = Integer.MIN_VALUE;
        for (RealTimeRequest request : requests) {
            if (request.getArrivalTime() <= time && request.isRealTime()) {
                if (request.getDeadline() - Math.abs(request.getPosition() - headPosition) > 0) {
                    if (Math.abs(request.getPosition() - headPosition) > biggestDistance) {
                        biggestDistance = Math.abs(request.getPosition() - headPosition);
                        furthestRequest = request;
                    }
                }
            }
        }
        if (furthestRequest == null) {
            furthestRequest = requests.get(0);
        }
        return furthestRequest;
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
        for (RealTimeRequest request : requests) {
            if (request.isRealTime() && request.getArrivalTime() <= time) {
                return true;
            }
        }
        return false;
    }

    public RealTimeRequest findGoingThroughRequest(List<RealTimeRequest> requests, int time, int headPosition) {
        for (RealTimeRequest request : requests) {
            if (request.getArrivalTime() <= time && request.getPosition() == headPosition) {
                return request;
            }
        }
        return null;
    }
}
