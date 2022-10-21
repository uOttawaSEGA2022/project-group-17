package com.example.kachow_now;

public class Admin {

    private String email;
    private String password;
    private String nickname;

    public Admin(String email, String password, String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String email){
        this.password = password;
    }
    public String getNickname(){
        return nickname;
    }
    public void setNickname(String email){
        this.nickname = nickname;
    }

}
