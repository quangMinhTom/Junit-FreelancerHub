package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;



    private String profileImage;
    private String address;
    private String phone;

    //8/12/2024
    private String username;
    private String email;
    private String description;
}
