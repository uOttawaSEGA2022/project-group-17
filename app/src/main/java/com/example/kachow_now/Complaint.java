package com.example.kachow_now;

public class Complaint {

    private String textReview, mealReviewed;
    private Cook complaintee;
    private int day, month, year;
    private long time;

    public Complaint() {
    }

    public Complaint(String mealReviewed, String textReview, Cook complaintee, int day, int month, int year) {
        this.mealReviewed = mealReviewed;
        this.textReview = textReview;
        this.complaintee = complaintee;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = System.currentTimeMillis();
    }

    // get and set methods
    public String getMealReviewed(){
        return this.mealReviewed;
    }
    public void setMealReviewed(String newMealReviewed){
        mealReviewed = newMealReviewed;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTextReview() {
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
