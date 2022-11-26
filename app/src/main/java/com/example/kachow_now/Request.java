package com.example.kachow_now;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Request {

    private String clientId;
    private String cookId;
    private String currentTime;
    private ArrayList<String> orders;

    public Request(ArrayList<String> mealList, String cookId, String clientId){
        this.orders = mealList;
        this.cookId = cookId;
        this.clientId = clientId;
        this.currentTime = Long.toString(System.currentTimeMillis());

    }

    public void makeOrder(Meal meal){
        orders.add(meal.getName());

    }
    public void removeMeal(int i){
        orders.remove(i);
    }
}

