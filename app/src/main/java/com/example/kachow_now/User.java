package com.example.kachow_now;

public class User {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String postalcode;

    public User(String firstname, String lastname, String password, String email, String address, String postalcode){
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.postalcode = postalcode;
    }
}
