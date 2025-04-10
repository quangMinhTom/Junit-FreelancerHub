package com.example.demo.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Status {
    private String status;

    // Getter và Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}