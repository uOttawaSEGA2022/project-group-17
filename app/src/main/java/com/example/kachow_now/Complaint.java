package com.example.kachow_now;

public class Complaint {

    public Complaint (Meal mealReviewed, String textReview, Cook complaintee, String date){
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
    public String getDate(){
        return this.date;
    }
    public void setDate(String newDate){
        date = newDate;
    }
}
