package com.example.demo.Controller;

import com.example.demo.Model.Job;
import com.example.demo.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Lấy tất cả các job
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }


    // Lấy job theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lưu một job mới
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        if (job.getStartDate() == null) {
            job.setStartDate(LocalDateTime.now()); // Gán ngày hiện tại làm ngày bắt đầu nếu không có
        }
        if (job.getEndDate() == null) {
            job.setEndDate(job.getStartDate().plusMonths(1)); // Gán ngày kết thúc là 1 tháng sau ngày bắt đầu
        }

        // Lưu job vào database
        Job savedJob = jobService.saveJob(job);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

    // Xóa job theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        Optional<Job> jobOptional = jobService.getJobById(id);
        if (jobOptional.isPresent()) {
            jobService.deleteJob(id); // Xóa công việc
            return ResponseEntity.noContent().build(); // Trả về 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy công việc, trả về 404
        }
    }


    // Cập nhật job theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job jobDetails) {
        Optional<Job> jobOptional = jobService.getJobById(id);
        if (jobOptional.isPresent()) {
            Job existingJob = jobOptional.get();

            // Cập nhật các trường thông tin của công việc
            existingJob.setTitle(jobDetails.getTitle());
            existingJob.setDescription(jobDetails.getDescription());
            existingJob.setStatus(jobDetails.getStatus());
            existingJob.setSalary(jobDetails.getSalary());
            existingJob.setStartDate(jobDetails.getStartDate());
            existingJob.setEndDate(jobDetails.getEndDate());
            existingJob.setUpdatedAt(LocalDateTime.now()); // Cập nhật thời gian chỉnh sửa

            // Lưu công việc đã cập nhật
            Job updatedJob = jobService.saveJob(existingJob);

            return ResponseEntity.ok(updatedJob);
        } else {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy job với id đó
        }
    }
    // other
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Job>> getJobsByUserId(@PathVariable Long userId) {
        List<Job> jobs = jobService.getJobsByUserId(userId);
        return ResponseEntity.ok(jobs);
    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Job>> getJobsByProjectId(@PathVariable Long projectId) {
        List<Job> jobs = jobService.getJobsByProjectId(projectId);
        if (jobs.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 nếu không có job nào
        }
        return ResponseEntity.ok(jobs); // Trả về danh sách job theo projectId
    }
}
