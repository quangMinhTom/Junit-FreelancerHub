package com.example.demo.Controller;

import com.example.demo.Model.ProjectJob;
import com.example.demo.Service.ProjectJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/project-jobs")
public class ProjectJobController {

    private final ProjectJobService projectJobService;

    @Autowired
    public ProjectJobController(ProjectJobService projectJobService) {
        this.projectJobService = projectJobService;
    }

    // Lấy tất cả các ProjectJob
    @GetMapping
    public List<ProjectJob> getAllProjectJobs() {
        return projectJobService.getAllProjectJobs();
    }

    // Lấy ProjectJob theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectJob> getProjectJobById(@PathVariable Long id) {
        Optional<ProjectJob> projectJob = projectJobService.getProjectJobById(id);
        if (projectJob.isPresent()) {
            return ResponseEntity.ok(projectJob.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Lưu một ProjectJob mới
    @PostMapping
    public ResponseEntity<ProjectJob> saveProjectJob(@RequestBody ProjectJob projectJob) {
        ProjectJob savedProjectJob = projectJobService.saveProjectJob(projectJob);
        return ResponseEntity.status(201).body(savedProjectJob); // Trả về mã 201 Created
    }

    // Cập nhật ProjectJob theo ID
    @PutMapping("/{id}")
    public ResponseEntity<ProjectJob> updateProjectJob(@PathVariable Long id, @RequestBody ProjectJob projectJobDetails) {
        Optional<ProjectJob> projectJobOptional = projectJobService.getProjectJobById(id);
        if (projectJobOptional.isPresent()) {
            ProjectJob existingProjectJob = projectJobOptional.get();

            // Cập nhật thông tin ProjectJob
            existingProjectJob.setProjectId(projectJobDetails.getProjectId());
            existingProjectJob.setJobId(projectJobDetails.getJobId());

            ProjectJob updatedProjectJob = projectJobService.saveProjectJob(existingProjectJob);
            return ResponseEntity.ok(updatedProjectJob);
        } else {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy ProjectJob
        }
    }

    // Xóa ProjectJob theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectJob(@PathVariable Long id) {
        Optional<ProjectJob> projectJobOptional = projectJobService.getProjectJobById(id);
        if (projectJobOptional.isPresent()) {
            projectJobService.deleteProjectJob(id);
            return ResponseEntity.noContent().build(); // Mã 204 No Content khi xóa thành công
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/project/{projectId}")
    public List<ProjectJob> getJobsByProjectId(@PathVariable Long projectId) {
        return projectJobService.getJobsByProjectId(projectId);
    }


}
