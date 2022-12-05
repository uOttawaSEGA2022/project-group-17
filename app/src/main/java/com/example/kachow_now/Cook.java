package com.example.kachow_now;

import java.util.ArrayList;
import java.util.Calendar;

public class Cook extends User {

    private String address;
    private String postalCode;
    private long phoneNumber;
    private ArrayList bank;
    private double rating;
    private String description;
    private boolean isBanned;
    private boolean isSuspended;
    private int daySus;


    public Cook() {
    }

    public Cook(final String firstName, final String lastname, final String password, final String email,
                final String address, final String postalCode, final long phoneNumber, final int transit, final int institution, final double account) {
        super(firstName, lastname, password, email);
        role = "Cook";
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        bank = new ArrayList();
        this.bank.add(transit);
        this.bank.add(institution);
        this.bank.add(account);
        rating = 0.0;
        description = "";
        isSuspended = false;
        isBanned = false;
    }
    // setter methods

    public static int getDate() {
        final Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalcode) {
        postalCode = postalcode;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList getBank() {
        return bank;
    }

    public void setBank(final ArrayList bank1) {
        //setBank(int transit, int institution, int account)
        //ArrayList bank1 = new ArrayList<>();
        //bank1.add(transit);
        //bank1.add(institution);
        //bank1.add(account);
        bank = bank1;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(final boolean isBanned) {
        this.isBanned = isBanned;
    }

    public boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(final boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public int getDaySus() {
        return daySus;
    }

    public void setDaySus(final int daySus) {
        this.daySus = daySus;
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


    @Override
    public String toString() {
        return super.toString() + "\n{" +
                " address='" + this.getAddress() + "'" +
                ", postalCode='" + this.getPostalCode() + "'" +
                ", phoneNumber='" + this.getPhoneNumber() + "'" +
                ", bank='" + this.getBank() + "'" +
                ", rating='" + this.getRating() + "'" +
                ", description='" + this.getDescription() + "'" +
                ", isBanned='" + this.getIsBanned() + "'" +
                ", isSuspended='" + this.getIsSuspended() + "'" +
                ", daySus='" + this.getDaySus() + "'" +
                "}";
    }

}
