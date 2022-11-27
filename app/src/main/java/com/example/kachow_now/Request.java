package com.example.kachow_now;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Request {

    private String clientId;
    private String cookId;
    private String currentTime;
    private ArrayList<String> orders;
    private String state;

    public Request(ArrayList<String> mealList, String cookId, String clientId){
        this.orders = mealList;
        this.cookId = cookId;
        this.clientId = clientId;
        this.currentTime = Long.toString(System.currentTimeMillis());
        this.state = "pending";
    }
    public Request(){

    }

    public void add(Meal meal){
        orders.add(meal.getName());

    }
    public void removeMeal(int i){
        orders.remove(i);
    }

    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }

    public void setCookId(String cookId){
        this.cookId = cookId;
    }
    public String getCookId(){
        return this.cookId;
    }
    public void setClientId(String clientId){
        this.clientId = clientId;
    }
    public String getClientId(){
        return this.clientId;
    }
    public String getCurrentTime(){
        return this.currentTime;
    }
    public void setCurrentTime(String currentTime){
        this.currentTime = currentTime;
    }
    public void setOrders(ArrayList<String> orders){
        this.orders = orders;
    }
}

