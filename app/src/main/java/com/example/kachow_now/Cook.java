package com.example.kachow_now;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Cook extends User{

    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String postalcode;
    private long phoneNumber;
    private ArrayList<Integer> bank;
    private double rating;
    private String description;
    private boolean isBanned;



    public Cook(String firstname, String lastname, String password, String email,
        String address, String postalcode,long phoneNumber, int transit, int institution, int account){
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.address = address;
        this.postalcode = postalcode;
        this.phoneNumber = phoneNumber;
        this.bank = new ArrayList();
        bank.add(transit);
        bank.add(institution);
        bank.add(account);
        this.rating = 0.0;
        this.description = "";
        this.isBanned = false;


    }
    // setter methods

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return this.lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
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

    public void setPostalCode(String postalcode) { this.postalcode = postalcode;}

    public String getPostalCode() {return this.postalcode;}

    public long getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber){
        this.phoneNumber = phoneNumber;
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



    public static int getDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }
    /*
    public void addToMenu(Meal meal, int i){

    }

    public void removeFromMenu(Meal meal, int i) {

    }


    public void setMenu(ArrayList<Meal> menu, int i){
        //if(i==1){

    }*/

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("password", password);
        result.put("firstName", firstname);
        result.put("lastName", lastname);
        result.put("email", email);
        result.put("address", address);
        result.put("bank", bank);
        result.put("rating", rating);
        result.put("description", description);
        result.put("isBanned", isBanned);

        return result;
    }


}
