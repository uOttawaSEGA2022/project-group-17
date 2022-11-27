package com.example.kachow_now;

import java.util.ArrayList;

public class Request {

    private String clientId;
    private String cookId;
    private long currentTime;
    private ArrayList<String> orders;
    private boolean accepted;

    public Request(ArrayList<String> mealList, String cookId, String clientId) {
        this.orders = mealList;
        this.cookId = cookId;
        this.clientId = clientId;
        this.currentTime = (System.currentTimeMillis());
        this.accepted = false;
    }
    public Request() {
        this.currentTime = (System.currentTimeMillis());
        this.orders = new ArrayList<>();
        this.accepted = false;
    }

    public void add(Meal meal) {
        orders.add(meal.getName());
    }

    public void removeMeal(int i) {
        orders.remove(i);
    }

    public boolean getAccepted() {
        return this.accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }

    public String getCookId() {
        return this.cookId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return this.clientId;
    }

    public long getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setOrders(ArrayList<String> orders) {
        this.orders = orders;
    }

    public ArrayList<String> getOrders() {
        return this.orders;
    }
}

