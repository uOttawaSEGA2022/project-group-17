package com.example.kachow_now;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

public class Meal {
    private LinkedList monday;
    private LinkedList tuesday;
    private LinkedList wednesday;
    private LinkedList thursday;
    private LinkedList friday;
    private LinkedList saturday;
    private LinkedList sunday;
    private int currentDay;

    public Meal(LinkedList sunday, LinkedList monday, LinkedList tuesday, LinkedList wednesday, LinkedList thursday, LinkedList friday, LinkedList saturday){
        this.sunday=sunday;
        this.monday=monday;
        this.tuesday=tuesday;
        this.wednesday=wednesday;
        this.thursday=thursday;
        this.friday=friday;
        this.saturday=saturday;


    }


}
