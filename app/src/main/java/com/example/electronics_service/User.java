package com.example.electronics_service;

public class User {
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String phoneType;
    private String address;

    public User() {}

    public User(String lastName, String firstName, String email, String phoneNumber, String phoneType, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.address = address;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public String getAddress() {
        return address;
    }
}
