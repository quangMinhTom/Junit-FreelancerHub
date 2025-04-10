//package com.example.demo.Model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Setter
//@Getter
//@RequiredArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "applications")
//public class Application {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "job_id", nullable = false)
//    private Job job;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "cv_id", nullable = false)
//    private CV cv;
//
//    private String status; // pending, approved, rejected
//
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//}
package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id", nullable = false)
    private Long jobId; // Chỉ lưu id job

    private String jobtitle;

    @Column(name = "user_id", nullable = false)
    private Long userId; // Chỉ lưu id user

    private String username;

    private String reason;

    @Column(name = "cv_id", nullable = false)
    private Long cvId; // Chỉ lưu id CV

    private String status; // pending, approved, rejected

    @Column(name = "aptest")
    private int aptest = 1; // Trường test điểm (kiểu int)

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
