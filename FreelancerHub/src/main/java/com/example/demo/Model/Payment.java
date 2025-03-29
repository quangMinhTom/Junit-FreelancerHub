package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jobId", nullable = false)
    private Long jobId; // Chỉ lưu ID của Project

    @Column(name = "user_id", nullable = false)
    private Long userId; // Chỉ lưu ID của User

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status; // pending, paid

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
