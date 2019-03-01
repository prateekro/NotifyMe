package com.prateek.notifyme.beans;

import java.util.List;

public class User {

    private String email;
    private String fname;
    private String lname;
    private String DOB;
    private String lastLogin;
    private String signupTimestamp;

    public User(String email, String fname, String lname) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
    }

    public User(String email, String fname, String lname, String DOB, String lastLogin, String signupTimestamp) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.DOB = DOB;
        this.lastLogin = lastLogin;
        this.signupTimestamp = signupTimestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getSignupTimestamp() {
        return signupTimestamp;
    }

    public void setSignupTimestamp(String signupTimestamp) {
        this.signupTimestamp = signupTimestamp;
    }
}
