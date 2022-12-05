package com.example.kachow_now;

public class User {
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String role;
    protected String UID;

    public User() {
    }

    public User(final String firstName, final String lastName, final String password, final String email) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(final String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "{" +
                " email='" + this.getEmail() + "'" +
                ", password='" + this.getPassword() + "'" +
                ", firstName='" + this.getFirstName() + "'" +
                ", lastName='" + this.getLastName() + "'" +
                ", role='" + this.getRole() + "'" +
                ", UID='" + this.getUID() + "'" +
                "}";
    }

}
