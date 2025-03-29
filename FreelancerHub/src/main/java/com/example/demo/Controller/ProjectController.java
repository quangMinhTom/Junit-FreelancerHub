package com.example.demo.Controller;
import com.example.demo.Model.*;
import com.example.demo.Service.*;
import com.example.demo.Service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    private  ProjectUserService projectUserService;
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Lấy tất cả các dự án
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // Lấy dự án theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lưu một dự án mới
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {


        // Lưu dự án vào database
        Project savedProject = projectService.saveProject(project);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    // Xóa dự án theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.getProjectById(id);
        if (projectOptional.isPresent()) {
            projectService.deleteProject(id); // Xóa dự án
            return ResponseEntity.noContent().build(); // Trả về 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy dự án, trả về 404
        }
    }

    // Cập nhật dự án theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        Optional<Project> projectOptional = projectService.getProjectById(id);
        if (projectOptional.isPresent()) {
            Project existingProject = projectOptional.get();

            // Cập nhật các trường thông tin của dự án
            existingProject.setTitle(projectDetails.getTitle());
            existingProject.setDescription(projectDetails.getDescription());
            existingProject.setBudget(projectDetails.getBudget());
//            existingProject.setcreatedAt(LocalDateTime.now()); // Cập nhật thời gian chỉnh sửa

            // Lưu dự án đã cập nhật
            Project updatedProject = projectService.saveProject(existingProject);

            return ResponseEntity.ok(updatedProject);
        } else {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy dự án với id đó
        }
    }
///   ////0.1//////////////
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Long userId) {
        List<Project> projects = projectService.getProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }



}
