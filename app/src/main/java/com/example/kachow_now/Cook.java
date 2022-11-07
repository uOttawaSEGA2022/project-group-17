package com.example.kachow_now;

import java.util.ArrayList;
import java.util.Calendar;

public class Cook extends User {

    private String address;
    private String postalCode;
    private long phoneNumber;
    private ArrayList<Number> bank;
    private double rating;
    private String description;
    private boolean isBanned;
    private boolean isSuspended;
    private int daySus;


    public Cook() {
    }

    public Cook(String firstName, String lastname, String password, String email,
                String address, String postalCode, long phoneNumber, int transit, int institution, double account) {
        super(firstName, lastname, password, email);
        this.role = "Cook";
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.bank = new ArrayList<Number>();
        bank.add(transit);
        bank.add(institution);
        bank.add(account);
        this.rating = 0.0;
        this.description = "";
        this.isSuspended = false;
        this.isBanned = false;
    }
    // setter methods

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalcode) {
        this.postalCode = postalcode;
    }

    public long getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Number> getBank() {
        return this.bank;
    }

    public void setBank(int transit, int institution, int account) {
        ArrayList<Number> bank1 = new ArrayList<>();
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

    public static int getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    public boolean getIsSuspended() {
        return this.isSuspended;
    }

    public void setIsSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public int getDaySus() {
        return this.daySus;
    }

    public void setDaySus() {
        this.daySus = getDate();
    }
    /*
    public void addToMenu(Meal meal, int i){

    }

    public void removeFromMenu(Meal meal, int i) {

    }


    public void setMenu(ArrayList<Meal> menu, int i){
        //if(i==1){

    }*/

/*    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("password", password);
        result.put("firstName", firstName);
        result.put("lastName", lastname);
        result.put("email", email);
        result.put("address", address);
        result.put("bank", bank);
        result.put("rating", rating);
        result.put("description", description);
        result.put("isBanned", isBanned);

        return result;
    }*/


}
