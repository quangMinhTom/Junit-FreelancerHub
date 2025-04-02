package com.example.demo.Model;

// Placeholder DTO classes (replace with your actual implementations)
public class RegisterRequest {
    private String email, password, name;
    public RegisterRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}


