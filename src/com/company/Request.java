package com.company;

import java.util.Random;

public class Request {
    private int number;
    private int position;
    private int arrivalTime;
    Random random = new Random();

    public Request(int number) {
        this.number = number;
        this.position = random.nextInt(0, 3000);
        this.arrivalTime = random.nextInt(0, 10000);
    }

    public Request(Request request){
        this.number = request.getNr();
        this.arrivalTime = request.getArrivalTime();
        this.position = request.getPosition();
    }

    public Request(int number, int position, int arrivalTime) {
        this.number = number;
        this.position = position;
        this.arrivalTime = arrivalTime;
    }

    public int getNr() {
        return number;
    }

    public void setNr(int number) {
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
}
