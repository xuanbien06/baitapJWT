package com.cmc.jwtauthenticationdemo.model;

public class AuthRequest {
    private String username;
    private String password;

    // Tự động tạo Getter/Setter (Alt+Insert -> Getter and Setter)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}