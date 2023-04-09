package com.example.electronics_service;

public class Appointment {
    private User user;
    private String device;
    private String date;
    private String description;

    public Appointment() {}

    public Appointment(User user, String device, String date, String description) {
        this.user = user;
        this.device = device;
        this.date = date;
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public String getDevice() {
        return device;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
