package com.example.kachow_now;

import java.util.ArrayList;

public class Client extends User {
    private ArrayList<Number> bank;
    private String address;
    private long phoneNumber;
    private String postalCode;

    public Client() {
    }

    public Client(String password, String firstName, String lastName, String email, long CC, int month, int year, int ccv, String address, String postalCode, long phoneNumber) {
        super(firstName, lastName, password, email);
        this.role = "Client";
        this.bank = new ArrayList<>();
        bank.add(CC);
        bank.add(month);
        bank.add(year);
        bank.add(ccv);
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public void setBank(int creditCardNumber, int expMonth, int expYear, int ccv) {
        ArrayList<Number> bank1 = new ArrayList<>();
        bank1.add(creditCardNumber);
        bank1.add(expMonth);
        bank1.add(expYear);
        bank1.add(ccv);
        bank = bank1;
    }

    public void setAddress(String newAddress) {
        address = newAddress;
    }

    public void setPhoneNumber(long newNumber) {
        phoneNumber = newNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    // getter methods
    public String getAddress() {
        return address;
    }

    // setter methods
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<Number> getBank() {
        return bank;
    }


}