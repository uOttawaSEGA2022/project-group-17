package com.example.kachow_now;

public class Cook{

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private int[] bank;
    private double rating;
    private Meal[] menu;
    private Meal[] log;
    private String description;
    private boolean isBanned;



    public Cook(String firstname, String lastname, String username, String password, String email, String address, int[] bank, double rating, String description, Meal[] menu, Meal[] log, boolean isBanned){

        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.bank = bank;
        this.rating = rating;
        this.menu = menu;
        this.log = log;
        this.description = description;
        this.isBanned = isBanned;

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
    public void setBank(int transit, int institution, int account){
        int bank[] = new int[3];
        bank[0] = transit;
        bank[1] = institution;
        bank[2] = account;
    }
    public void setRating(double newRating){
        rating = newRating;
    }
    // idk abt these
    public void setLog(){
    }
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
    public int[] getBank(){
        return bank;
    }
    public double getRating(){
        return rating;
    }
    public Meal[] getLog(){
        return log;
    }
    public Meal[] getMenu(){
        return menu;
    }
    public String getDescription(){
        return description;
    }
    public boolean getIsBanned(){
        return isBanned;
    }



    // getMethods
    import { getDatabase } from "firebase/database";

        const database = getDatabase();

    // setMethods
    import { getDatabase, ref, set } from "firebase/database";

    function writeUserData(userId, name, email, imageUrl) {
      const db = getDatabase();
            set(ref(db, 'users/' + userId), {
                    username: name,
                    email: email,
                    profile_picture : imageUrl
      });
    }
}
