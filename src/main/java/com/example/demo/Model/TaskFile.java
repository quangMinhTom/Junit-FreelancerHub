package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_files")
public class TaskFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id", nullable = false)
    private Long taskId; // Chỉ lưu ID của Task

    @Column(name = "user_id", nullable = false)
    private Long userId; // Chỉ lưu ID của User
    @Column(name = "projectId", nullable = false)
    private Long projectId;
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_type", nullable = false)
    private String fileType; // e.g., pdf, image, document

    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp createdAt = new java.sql.Timestamp(System.currentTimeMillis());
}
