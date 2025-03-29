package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId; // Chỉ lưu ID của Project

    @Column(name = "assigned_to")
    private Long assignedTo; // Chỉ lưu ID của User

//    @NotBlank(message = "Tiêu đề task là bắt buộc")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status; // pending, in_progress, completed

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    private String priority; // high, medium, low

    private Integer progress; // 0-100

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 9/12/2024
    private String projectname;

    private Long jobId;

    private String asignedname;

    private String jobname;
}
