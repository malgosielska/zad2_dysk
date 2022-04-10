package com.company;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    private final List<Request> requests = generateRequests();
    private final List<RealTimeRequest> realTimeRequests = generateRealTimeRequests();

    public List<Request> generateRequests(){
        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            requests.add(new Request(i));
        }
        return requests;
    }

    public List<RealTimeRequest> generateRealTimeRequests(){
        List<RealTimeRequest> requests = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            requests.add(new RealTimeRequest(i));
        }
        return requests;
    }

    public List<Request> fcfsRequests (){
        List<Request> fcfsRequests = new ArrayList<>();
        for (Request request : requests){
            fcfsRequests.add(new Request(request));
        }

        return fcfsRequests;
    }

    public List<Request> sstfRequests (){
        List<Request> sstfRequests = new ArrayList<>();
        for (Request request : requests){
            sstfRequests.add(new Request(request));
        }

        return sstfRequests;
    }

    public List<Request> scanRequests (){
        List<Request> scanRequests = new ArrayList<>();
        for (Request request : requests){
            scanRequests.add(new Request(request));
        }

        return scanRequests;
    }
    public List<Request> cscanRequests (){
        List<Request> cscanRequests = new ArrayList<>();
        for (Request request : requests){
            cscanRequests.add(new Request(request));
        }

        return cscanRequests;
    }

    public List<RealTimeRequest> edfRequests (){
        List<RealTimeRequest> edfRequests = new ArrayList<>();
        for (RealTimeRequest request : realTimeRequests){
            edfRequests.add(new RealTimeRequest(request));
        }
        return edfRequests;
    }

    public List<RealTimeRequest> fdscanRequests (){
        List<RealTimeRequest> fdscanRequests = new ArrayList<>();
        for (RealTimeRequest request : realTimeRequests){
            fdscanRequests.add(new RealTimeRequest(request));
        }
        return fdscanRequests;
    }

}
