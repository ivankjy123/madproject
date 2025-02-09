package com.example.madproject;

public class User {
    private String username;  // New field for the username
    private String email;
    private int icno;
    private String password;

    // Constructor updated to include username
    public User(String username, String email, int icno, String password) {
        this.username = username;
        this.email = email;
        this.icno = icno;
        this.password = password;
    }

    // Getter and setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Getter for IC number
    public int getIcno() {
        return icno;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Optional setter for email, IC number, and password if needed
    public void setEmail(String email) {
        this.email = email;
    }

    public void setIcno(int icno) {
        this.icno = icno;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
