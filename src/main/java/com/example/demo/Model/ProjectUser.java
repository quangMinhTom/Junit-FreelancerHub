package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_users")
public class ProjectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId; // Thay thế đối tượng Project bằng projectId

    @Column(name = "user_id", nullable = false)
    private Long userId; // Thay thế đối tượng User bằng userId

    @Column(name = "role", nullable = false)
    private String role; // "freelancer" hoặc "manager"

    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp createdAt = new java.sql.Timestamp(System.currentTimeMillis());

    //8/12/2024
    private String projectname;
    private String username;
    private String jobname;
    private Long jobid;
}
