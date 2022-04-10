package com.company;

import java.util.Random;

public class RealTimeRequest {
    private int number;
    private int position;
    private int arrivalTime;
    private boolean isRealTime;
    private int deadline;
    Random random = new Random();

    public RealTimeRequest(int number) {
        this.number = number;
        this.position = random.nextInt(0, 100);
        this.arrivalTime = random.nextInt(0, 100);
        this.isRealTime = makeRealTime();
        if (isRealTime){
            this.deadline = random.nextInt(0, 50);
        } else {
            this.deadline = 0;
        }
    }

    public RealTimeRequest(RealTimeRequest request){
        this.number = request.getNumber();
        this.arrivalTime = request.getArrivalTime();
        this.position = request.getPosition();
        this.isRealTime = request.isRealTime();
        this.deadline = request.getDeadline();
    }

    public boolean makeRealTime(){
        int x = random.nextInt(0,4);
        return x == 0;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isRealTime() {
        return isRealTime;
    }

    public void setRealTime(boolean realTime) {
        isRealTime = realTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
}
