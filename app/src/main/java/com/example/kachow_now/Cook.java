package com.example.kachow_now;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Cook{

    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private ArrayList<Integer> bank;
    private double rating;
    private String description;
    private boolean isBanned;
    private int currentDay;

    private ArrayList monday;
    private ArrayList tuesday;
    private ArrayList wednesday;
    private ArrayList thursday;
    private ArrayList friday;
    private ArrayList saturday;
    private ArrayList sunday;



    public Cook(String firstname, String lastname, String password, String email,
        String address, int transit, int institution, int account){

        this.firstName = firstname;
        this.lastName = lastname;
        this.password = password;
        this.email = email;
        this.address = address;
        this.bank = new ArrayList();
        bank.add(transit);
        bank.add(institution);
        bank.add(account);
        this.rating = 0.0;
        this.description = "";
        this.isBanned = false;
        this.currentDay = getDate();

        monday = new ArrayList<Meal>();
        tuesday = new ArrayList<Meal>();
        wednesday = new ArrayList<Meal>();
        thursday = new ArrayList<Meal>();
        friday = new ArrayList<Meal>();
        saturday = new ArrayList<Meal>();
        sunday = new ArrayList<Meal>();

    }
    // setter methods

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Integer> getBank() {
        return this.bank;
    }

    public void setBank(int transit, int institution, int account) {
        ArrayList<Integer> bank1 = new ArrayList<>();
        bank1.add(transit);
        bank1.add(institution);
        bank1.add(account);
        this.bank = bank1;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean getIsBanned() {
        return this.isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public int getCurrentDay() {
        return this.currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
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

    public static int getDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    public void addToMenu(Meal meal){

    }
    public void setMenu(ArrayList menu, int i){
        if(i==1){

        }
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("password", password);
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("email", email);
        result.put("address", address);
        result.put("bank", bank);
        result.put("rating", rating);
        result.put("description", description);
        result.put("isBanned", isBanned);
        result.put("currentDay", currentDay);

        result.put("monday", monday);
        result.put("tuesday", tuesday);
        result.put("wednesday", wednesday);
        result.put("thursday", thursday);
        result.put("friday", friday);
        result.put("saturday", saturday);
        result.put("sunday", sunday);

        return result;
    }


}
