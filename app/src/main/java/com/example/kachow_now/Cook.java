package com.example.kachow_now;

public class Cook{

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int[] bankInfo;
    private double rating;
    private String description;
    private boolean isBanned;

    public Cook(String firstName, String lastName, String username, String password, String email, int[] bankInfo, double rating, String description, boolean isBanned){

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bankInfo = bankInfo;

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
