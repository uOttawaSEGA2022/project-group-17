package com.example.kachow_now;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Request {

    private String clientId;
    private String cookId;
    private long currentTime;
    private ArrayList orders;

    public Request(){
        this.orders = new ArrayList();
    }

    public void orderMeal(Meal meal){
        orders.add(meal);
    }
}
