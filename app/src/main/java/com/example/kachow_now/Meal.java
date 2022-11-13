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

    public Meal(){

    }
    public Meal(String Name, String Description, String MealType, double Price, String Cuisine, ArrayList<String> Ingredients, ArrayList<String> Allergens, double servingSize, double Calories) {
        this.Name = Name;
        this.Description = Description;
        this.MealType = MealType;
        this.Price = Price;
        this.Cuisine = Cuisine;
        this.Ingredients = Ingredients;
        this.Allergens = Allergens;
        this.servingSize = servingSize;
        this.Calories = Calories;

    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getMealType() {
        return this.MealType;
    }

    public void setMealType(String MealType) {
        this.MealType = MealType;
    }

    public double getPrice() {
        return this.Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getCuisine() {
        return this.Cuisine;
    }

    public void setCuisine(String Cuisine) {
        this.Cuisine = Cuisine;
    }

    public ArrayList<String> getIngredients() {
        return this.Ingredients;
    }

    public void setIngredients(ArrayList<String> Ingredients) {
        this.Ingredients = Ingredients;
    }

    public ArrayList<String> getAllergens() {
        return this.Allergens;
    }

    public void setAllergens(ArrayList<String> Allergens) {
        this.Allergens = Allergens;
    }

    public double getServingSize() {
        return this.servingSize;
    }

    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    public double getCalories() {
        return this.Calories;
    }

    public void setCalories(double Calories) {
        this.Calories = Calories;
    }

    public String getCookUID() { return this.CookUID;}

    public void setCookUID(String s) {this.CookUID = s;}
}

