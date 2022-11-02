package com.example.kachow_now;

public class Complaint {

    private String textReview;
    private Meal mealReviewed;
    private Cook complaintee;
    public long date;

    public Complaint (Meal mealReviewed, String textReview, Cook complaintee, long date){
        this.mealReviewed = mealReviewed;
        this.textReview = textReview;
        this.complaintee = complaintee;
        this.date = date;
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
    public long getDate(){
        return this.date;
    }
    public void setDate(long newDate){
        date = newDate;
    }
}
