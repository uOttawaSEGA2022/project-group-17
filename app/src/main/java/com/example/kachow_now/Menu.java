package com.example.kachow_now;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Menu {
    private int currentDay;

    private ArrayList sunday;
    private ArrayList monday;
    private ArrayList tuesday;
    private ArrayList wednesday;
    private ArrayList thursday;
    private ArrayList friday;
    private ArrayList saturday;


    public Menu(){

        sunday = new ArrayList<Meal>();
        monday = new ArrayList<Meal>();
        tuesday = new ArrayList<Meal>();
        wednesday = new ArrayList<Meal>();
        thursday = new ArrayList<Meal>();
        friday = new ArrayList<Meal>();
        saturday = new ArrayList<Meal>();

        this.currentDay = getDate();
    }

    public int getCurrentDay() {
        return this.currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public ArrayList getMonday() {
        return this.monday;
    }

    public void setMonday(ArrayList monday) {
        this.monday = monday;
    }

    public ArrayList getTuesday() {
        return this.tuesday;
    }

    public void setTuesday(ArrayList tuesday) {
        this.tuesday = tuesday;
    }

    public ArrayList getWednesday() {
        return this.wednesday;
    }

    public void setWednesday(ArrayList wednesday) {
        this.wednesday = wednesday;
    }

    public ArrayList getThursday() {
        return this.thursday;
    }

    public void setThursday(ArrayList thursday) {
        this.thursday = thursday;
    }

    public ArrayList getFriday() {
        return this.friday;
    }

    public void setFriday(ArrayList friday) {
        this.friday = friday;
    }

    public ArrayList getSaturday() {
        return this.saturday;
    }

    public void setSaturday(ArrayList saturday) {
        this.saturday = saturday;
    }

    public ArrayList getSunday() {
        return this.sunday;
    }

    public void setSunday(ArrayList sunday) {
        this.sunday = sunday;
    }

    public static int getDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("monday", monday);
        result.put("tuesday", tuesday);
        result.put("wednesday", wednesday);
        result.put("thursday", thursday);
        result.put("friday", friday);
        result.put("saturday", saturday);
        result.put("sunday", sunday);
        result.put("currentDay", currentDay);

        return result;
    }
}
