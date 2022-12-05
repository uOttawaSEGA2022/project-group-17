package com.example.kachow_now;

import java.util.ArrayList;

public class Client extends User {
    private ArrayList<Number> bank;
    private String address;
    private long phoneNumber;
    private String postalCode;

    public Client() {
    }

    public Client(final String password, final String firstName, final String lastName, final String email, final long CC, final int month, final int year, final int ccv, final String address, final String postalCode, final long phoneNumber) {
        super(firstName, lastName, password, email);
        role = "Client";
        bank = new ArrayList<>();
        this.bank.add(CC);
        this.bank.add(month);
        this.bank.add(year);
        this.bank.add(ccv);
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public void setBank(final int creditCardNumber, final int expMonth, final int expYear, final int ccv) {
        final ArrayList<Number> bank1 = new ArrayList<>();
        bank1.add(creditCardNumber);
        bank1.add(expMonth);
        bank1.add(expYear);
        bank1.add(ccv);
        this.bank = bank1;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    // setter methods
    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    // getter methods
    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String newAddress) {
        this.address = newAddress;
    }

    public long getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(final long newNumber) {
        this.phoneNumber = newNumber;
    }

    public ArrayList<Number> getBank() {
        return this.bank;
    }


    @Override
    public String toString() {
        return super.toString() + "\n{" +
                " bank='" + this.getBank() + "'" +
                ", address='" + this.getAddress() + "'" +
                ", phoneNumber='" + this.getPhoneNumber() + "'" +
                ", postalCode='" + this.getPostalCode() + "'" +
                "}";
    }

}