package com.example.demo.Service;

import com.example.demo.Model.Job;
import com.example.demo.Model.Project;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.ApplicationRepository;
import com.example.demo.Repository.ProjectJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private  ApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Lấy tất cả các project
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Lấy project theo ID
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    // Lưu một project mới
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    // Xóa project theo ID
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> getProjectsByUserId(Long userId) {
        return projectRepository.findByUserId(userId);
    }

    // Các phương thức khác nếu cần
}
