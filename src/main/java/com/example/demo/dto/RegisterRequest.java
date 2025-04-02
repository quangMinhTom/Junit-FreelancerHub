package com.example.demo.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    public RegisterRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private String email;
    private String password;
    private String name;
}
