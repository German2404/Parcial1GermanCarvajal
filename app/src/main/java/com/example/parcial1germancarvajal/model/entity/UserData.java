package com.example.parcial1germancarvajal.model.entity;

public class UserData {
    private String username;
    private int points;

    public UserData(String username) {
        this.username = username;
        this.points=0;
    }

    public UserData() {
    }

    public UserData(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
