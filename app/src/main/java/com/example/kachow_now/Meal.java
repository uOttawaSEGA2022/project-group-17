package com.example.kachow_now;

import java.util.ArrayList;

public class Meal {

    private String Name;
    private String Description;
    private String MealType;
    private double Price;
    private String Cuisine;
    private ArrayList<String> Ingredients;
    private ArrayList<String> Allergens;
    private double servingSize;
    private double Calories;
    private String CookUID;
    private boolean isOffered;
    private double rating;

    public Meal() {
        isOffered = true;
        rating = 0.0;
    }

    public Meal(final String Name, final String Description, final String MealType, final double Price, final String Cuisine, final ArrayList<String> Ingredients, final ArrayList<String> Allergens, final double servingSize, final double Calories) {
        this.Name = Name;
        this.Description = Description;
        this.MealType = MealType;
        this.Price = Price;
        this.Cuisine = Cuisine;
        this.Ingredients = Ingredients;
        this.Allergens = Allergens;
        this.servingSize = servingSize;
        this.Calories = Calories;
        isOffered = true;
        rating = 0.0;
    }


    public String getName() {
        return Name;
    }

    public void setName(final String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(final String Description) {
        this.Description = Description;
    }

    public String getMealType() {
        return MealType;
    }

    public void setMealType(final String MealType) {
        this.MealType = MealType;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(final double Price) {
        this.Price = Price;
    }

    public String getCuisine() {
        return Cuisine;
    }

    public void setCuisine(final String Cuisine) {
        this.Cuisine = Cuisine;
    }

    public ArrayList<String> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(final ArrayList<String> Ingredients) {
        this.Ingredients = Ingredients;
    }

    public ArrayList<String> getAllergens() {
        return Allergens;
    }

    public void setAllergens(final ArrayList<String> Allergens) {
        this.Allergens = Allergens;
    }

    public double getServingSize() {
        return servingSize;
    }

    public void setServingSize(final double servingSize) {
        this.servingSize = servingSize;
    }

    public double getCalories() {
        return Calories;
    }

    public void setCalories(final double Calories) {
        this.Calories = Calories;
    }

    public String getCookUID() {
        return CookUID;
    }

    public void setCookUID(final String s) {
        CookUID = s;
    }

    public boolean getIsOffered() {
        return isOffered;
    }

    public void setIsOffered(final boolean isOffered) {
        this.isOffered = isOffered;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(final double rating) {
        this.rating = rating % 5.0;
    }
}

