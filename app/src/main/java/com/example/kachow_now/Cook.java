package com.example.kachow_now;

import java.util.ArrayList;
import java.util.Calendar;

public class Cook{

    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private ArrayList bank;
    private double rating;
    private Menu[] menu;
    private String description;
    private boolean isBanned;
    private int currentDay;



    public Cook(String firstname, String lastname, String password, String email, String address, int transit, int institution, int account, double rating, String description, Menu[] menu){

        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.address = address;
        this.bank = new ArrayList();
        bank.add(transit);
        bank.add(institution);
        bank.add(account);
        this.rating = rating;
        this.menu = menu;
        this.description = description;
        this.isBanned = isBanned;
        this.currentDay = getDate();

    }
    // setter methods
    public void setFirstName(String newFirstName) {
        firstname = newFirstName;
    }
    public void setLastName(String newLastName){
        lastname = newLastName;
    }
    public void setPassword(String newPassword){
        password = newPassword;
    }
    public void setAddress(String newAddress){
        address = newAddress;
    }
    public void setEmail(String newEmail){
        email = newEmail;
    }
    public void setBank(int transit, int institution, int account){
        this.bank.clear();
        bank.add(transit);
        bank.add(institution);
        bank.add(account);
    }
    public void setRating(double newRating){
        rating = newRating;
    }
    // idk abt these
    public void setMenu(){
    }

    public void setDescription(String newDescription){
        description = newDescription;
    }
    public void setIsBanned(boolean newIsBanned){
        isBanned = newIsBanned;
    }

    // getter methods
    public String getFirstName() {
        return firstname;
    }
    public String getLastName(){
        return lastname;
    }
    public String getPassword(){
        return password;
    }
    public String address(){
        return address;
    }
    public String getEmail(){
        return email;
    }
    public ArrayList getBank(){
        return bank;
    }
    public double getRating(){
        return rating;
    }
    public Menu[] getMenu(){
        return menu;
    }
    public String getDescription(){
        return description;
    }
    public boolean getIsBanned(){
        return isBanned;
    }
    private int getDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        currentDay = day;
        return currentDay;
    }


}
