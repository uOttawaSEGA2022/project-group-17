package com.example.kachow_now;

public class Complaint {

    private String textReview;
    private Meal mealReviewed;
    private Cook complaintee;
    private int day, month, year;

    public Complaint (Meal mealReviewed, String textReview, Cook complaintee, int day, int month, int year){
        this.mealReviewed = mealReviewed;
        this.textReview = textReview;
        this.complaintee = complaintee;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // get and set methods
    public Meal getMealReviewed(){
        return this.mealReviewed;
    }
    public void setMealReviewed(Meal newMealReviewed){
        mealReviewed = newMealReviewed;
    }
    public String getTextReview(){
        return this.textReview;
    }
    public void setTextReview(String newReview){
        textReview = newReview;
    }
    public Cook getComplaintee(){
        return complaintee;
    }
    public void setComplaintee(Cook newComplaintee){
        complaintee = newComplaintee;
    }
    public int getDay(){
        return this.day;
    }
    public void setDay(int newDay){
        day = newDay;
    }
    public int getMonth(){
        return this.month;
    }
    public void setMonth(int newMonth){
        month = newMonth;
    }
    public int getYear(){
        return this.year;
    }
    public void setYear(int newYear){
        year = newYear;
    }
}
