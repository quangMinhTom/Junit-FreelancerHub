package com.example.demo;

import com.example.demo.Model.Job;
import com.example.demo.Repository.JobRepository;
import com.example.demo.Service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper method để tạo Job giả
    private Job createJob(Long id, String title, Long userId, Long projectId) {
        Job job = new Job();
        job.setId(id);
        job.setTitle(title);
        job.setUserId(userId);
        job.setProjectid(projectId);
        job.setDescription("Mô tả công việc");
        job.setStatus("open");
        job.setSalary(1000f);
        job.setStartDate(LocalDateTime.now());
        job.setEndDate(LocalDateTime.now().plusDays(30));
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        job.setUsername("user" + userId);
        job.setProjectname("project" + projectId);
        return job;
    }

    @Test
    void testGetAllJobs() {
        Job job1 = createJob(1L, "Job 1", 10L, 100L);
        Job job2 = createJob(2L, "Job 2", 20L, 200L);
        when(jobRepository.findAll()).thenReturn(Arrays.asList(job1, job2));

        List<Job> jobs = jobService.getAllJobs();

        assertEquals(2, jobs.size());
        assertEquals("Job 1", jobs.get(0).getTitle());
        verify(jobRepository, times(1)).findAll();
    }

    @Test
    void testGetJobById_Found() {
        Job job = createJob(1L, "Test Job", 10L, 100L);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        Optional<Job> result = jobService.getJobById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Job", result.get().getTitle());
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void testGetJobById_NotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Job> result = jobService.getJobById(1L);

        assertFalse(result.isPresent());
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveJob() {
        Job job = createJob(null, "New Job", 5L, 55L);
        when(jobRepository.save(job)).thenReturn(job);

        Job saved = jobService.saveJob(job);

        assertEquals("New Job", saved.getTitle());
        assertEquals(5L, saved.getUserId());
        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void testDeleteJob() {
        jobService.deleteJob(5L);
        verify(jobRepository, times(1)).deleteById(5L);
    }

    @Test
    void testGetJobsByUserId() {
        Job job = createJob(3L, "User Job", 99L, 999L);
        when(jobRepository.findByUserId(99L)).thenReturn(List.of(job));

        List<Job> jobs = jobService.getJobsByUserId(99L);

        assertEquals(1, jobs.size());
        assertEquals(99L, jobs.get(0).getUserId());
        verify(jobRepository, times(1)).findByUserId(99L);
    }

    @Test
    void testGetJobsByProjectId() {
        Job job = createJob(4L, "Project Job", 88L, 888L);
        when(jobRepository.findByProjectid(888L)).thenReturn(List.of(job));

        List<Job> jobs = jobService.getJobsByProjectId(888L);

        assertEquals(1, jobs.size());
        assertEquals(888L, jobs.get(0).getProjectid());
        verify(jobRepository, times(1)).findByProjectid(888L);
    }
}
