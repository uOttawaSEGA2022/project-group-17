package com.example.kachow_now;

public class Client {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private int[] bank;
    private String address;
    private long phoneNumber;

    public Client(String username, String password, String firstname, String lastname, String email, int[] bank, String address, long phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.bank = bank;
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
    public void setUserName(String newUserName){
        username = newUserName;
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
        int bank[] = new int[4];
        bank[0] = creditCardNumber;
        bank[1] = expMonth;
        bank[2] = expYear;
        bank[3] = ccv;
    }

    // getter methods
    public String getFirstName() {
        return firstname;
    }
    public String getLastName(){
        return lastname;
    }
    public String getUserName(){
        return username;
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
    public long getPhoneNumber(){
        return phoneNumber;
    }
    public int[] getBank(){
        return bank;
    }


}