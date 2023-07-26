package com.example.projectandroid;

public class User {
    String FName, LName, Email;

    public User() {

    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public User(String FName, String LName, String email) {
        this.FName = FName;
        this.LName = LName;
        Email = email;
    }

}
