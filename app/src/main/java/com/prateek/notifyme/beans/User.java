package com.prateek.notifyme.beans;

import java.util.List;

public class User {

    private String ID;
    private String password;
    private String name;
    private String email;

    public User(String ID, String password, String name, String email) {
        this.ID = ID;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
