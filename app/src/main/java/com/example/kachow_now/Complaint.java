package com.example.kachow_now;

public class Complaint {

    private String textReview, mealReviewed;
    private int day, month, year;
    private long time;
    private Cook complaintee;

    public Complaint() {
    }

    public Complaint(String mealReviewed, Cook complaintee, String textReview, int day, int month, int year) {
        this.mealReviewed = mealReviewed;
        this.textReview = textReview;
        this.complaintee = complaintee;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = System.currentTimeMillis();
    }

    // get and set methods
    public String getMealReviewed() {
        return this.mealReviewed;
    }

    public void setMealReviewed(String newMealReviewed) {
        mealReviewed = newMealReviewed;
    }

    public Cook getComplaintee() {
        return this.complaintee;
    }

    public void setComplaintee(Cook newComplaintee) {
        this.complaintee = newComplaintee;
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
