//package com.example.demo.Model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import jakarta.validation.constraints.NotBlank;
//
//import java.time.LocalDateTime;
//
//@Setter
//@Getter
//@RequiredArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "jobs")
//public class Job {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @NotBlank(message = "Tiêu đề công việc là bắt buộc")
//    private String title;
//
//    @Column(columnDefinition = "TEXT", nullable = false)
//    private String description;
//
//    private String status; // open, in_progress, completed
//
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt = LocalDateTime.now();
//
//    // Ngày bắt đầu công việc
//    @Column(name = "start_date")
//    private LocalDateTime startDate;
//
//    // Ngày kết thúc công việc
//    @Column(name = "end_date")
//    private LocalDateTime endDate;
//
//    @Column(nullable = false)
//    private Float salary;
//
//
//    public Long getUserId() {
//        return user != null ? user.getId() : null;
//    }
//}
package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Thay thế quan hệ @ManyToOne bằng trường userId
    @Column(name = "user_id", nullable = false)
    private Long userId; // Lưu trực tiếp userId

//    @NotBlank(message = "Tiêu đề công việc là bắt buộc")
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private String status; // open, in_progress, completed

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Ngày bắt đầu công việc
    @Column(name = "start_date")
    private LocalDateTime startDate;

    // Ngày kết thúc công việc
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Float salary;

    //9/12/2024
    private String username;

    private Long projectid;

    private String projectname;

     // open, in_progress, completed
}

