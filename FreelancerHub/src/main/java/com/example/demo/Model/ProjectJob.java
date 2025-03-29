package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_jobs")
public class ProjectJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId; // Thay thế đối tượng Project bằng projectId

    @Column(name = "job_id", nullable = false)
    private Long jobId; // Thay thế đối tượng Job bằng jobId

    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp createdAt = new java.sql.Timestamp(System.currentTimeMillis());

    private String jobname; // in_progress, completed, canceled
    private String projectname; // in_progress, completed, canceled
}
