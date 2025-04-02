package com.example.demo.Model;

public class LoginRequest {
    private String email, password;
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
