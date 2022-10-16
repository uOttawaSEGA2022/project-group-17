package com.example.kachow_now;

public class Client {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private int creditCardNumber;
    private int expMonth;
    private int expYear;
    private int ccv;

    public Client(String userName, String firstName, String lastName, String email) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}