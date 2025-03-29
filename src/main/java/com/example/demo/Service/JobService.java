package com.example.demo.Service;

import com.example.demo.Model.Job;
import com.example.demo.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Lấy tất cả các job
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Lấy job theo ID
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // Lưu một job mới
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    // Xóa job theo ID
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
    // other
    public List<Job> getJobsByUserId(Long userId) {
        return jobRepository.findByUserId(userId);
    }
    public List<Job> getJobsByProjectId(Long projectId) {
        return jobRepository.findByProjectid(projectId);
    }
}
