package com.example.kachow_now;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Request {

    private String clientId;
    private String cookId;
    private long currentTime;
    private ArrayList<Pair<String,Long>> orders;

    public class Pair<String, Long> {
        private String left;
        private Long right;

        public Pair(String mealName,Long currentTime){
            this.left = mealName;
            this.right = currentTime;
        }
    }

    public Request(){
        this.orders = new ArrayList<>();
    }

    public void orderMeal(Meal meal){
        orders.add(new Pair(meal.getName(),System.currentTimeMillis()));

    }
}

