package com.example.kachow_now;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private ArrayList bank;
    private String address;
    private long phoneNumber;

    public Client(String password, String firstname, String lastname, String email, long CC, int month, int year, int ccv, String address, long phoneNumber) {
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.bank = new ArrayList<>();
        bank.add(CC);
        bank.add(month);
        bank.add(year);
        bank.add(ccv);
        this.address = address;
        this.phoneNumber = phoneNumber;
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
    public void setPhoneNumber(long newNumber){
        phoneNumber = newNumber;
    }
    public void setBank(int creditCardNumber, int expMonth, int expYear, int ccv){
        ArrayList bank1 = new ArrayList<>();
        bank1.add(creditCardNumber);
        bank1.add(expMonth);
        bank1.add(expYear);
        bank1.add(ccv);
        bank = bank1;
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
    public String getAddress(){
        return address;
    }
    public String getEmail(){
        return email;
    }
    public long getPhoneNumber(){
        return phoneNumber;
    }


    public ArrayList getBank(){
        return bank;
    }


}