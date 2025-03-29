package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Lưu trực tiếp userId

    private String username;

    @NotBlank(message = "Tiêu đề dự án là bắt buộc")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal budget;

    private String status; // in_progress, completed, canceled

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
