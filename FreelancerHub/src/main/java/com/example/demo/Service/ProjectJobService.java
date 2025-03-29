package com.example.demo.Service;

import com.example.demo.Model.ProjectJob;
import com.example.demo.Repository.ProjectJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectJobService {

    private final ProjectJobRepository projectJobRepository;

    @Autowired
    public ProjectJobService(ProjectJobRepository projectJobRepository) {
        this.projectJobRepository = projectJobRepository;
    }

    // Lấy tất cả các ProjectJob
    public List<ProjectJob> getAllProjectJobs() {
        return projectJobRepository.findAll();
    }

    // Lấy ProjectJob theo ID
    public Optional<ProjectJob> getProjectJobById(Long id) {
        return projectJobRepository.findById(id);
    }

    // Lưu một ProjectJob mới
    public ProjectJob saveProjectJob(ProjectJob projectJob) {
        return projectJobRepository.save(projectJob);
    }

    // Xóa ProjectJob theo ID
    public void deleteProjectJob(Long id) {
        projectJobRepository.deleteById(id);
    }

    public List<ProjectJob> getJobsByProjectId(Long projectId) {
        return projectJobRepository.findByProjectId(projectId);
    }
}
