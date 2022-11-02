package com.example.kachow_now;

public class Complaint {

    public Complaint (Meal mealReviewed, String textReview, Cook complaintee){
        this.mealReviewed = mealReviewed;
        this.textReview = textReview;
        this.complaintee = complaintee;
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
}
