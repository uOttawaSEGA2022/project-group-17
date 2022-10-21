package com.example.kachow_now;

import java.util.ArrayList;

public class Menu {
    private ArrayList monday;
    private ArrayList tuesday;
    private ArrayList wednesday;
    private ArrayList thursday;
    private ArrayList friday;
    private ArrayList saturday;
    private ArrayList sunday;
    private int currentDay;

    public Menu(ArrayList sunday, ArrayList monday, ArrayList tuesday, ArrayList wednesday, ArrayList thursday, ArrayList friday, ArrayList saturday){
        this.sunday=sunday;
        this.monday=monday;
        this.tuesday=tuesday;
        this.wednesday=wednesday;
        this.thursday=thursday;
        this.friday=friday;
        this.saturday=saturday;


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

    public int getCurrentDay() {
        return this.currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }


}
