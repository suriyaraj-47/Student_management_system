package com.college.management.login;

public class UserLoginInfo {
    private int userId;
    private String role;

    public UserLoginInfo(int userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}