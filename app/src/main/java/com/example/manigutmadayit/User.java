package com.example.manigutmadayit;

public class User {
    public String email;
    public String role;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }
}
