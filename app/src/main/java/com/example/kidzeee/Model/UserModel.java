package com.example.kidzeee.Model;

public class UserModel {

    private String ID;
    private String Email;

    public UserModel() {
    }
    public UserModel(String ID, String email) {
        this.ID = ID;
        Email = email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
