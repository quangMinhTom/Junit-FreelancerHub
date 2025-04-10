package com.example.demo.Controller;

import com.example.demo.Model.ProjectUser;
import com.example.demo.Service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projectusers")
public class ProjectUserController {

    private final ProjectUserService projectUserService;

    @Autowired
    public ProjectUserController(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    // Lấy tất cả các project user
    @GetMapping
    public List<ProjectUser> getAllProjectUsers() {
        return projectUserService.getAllProjectUsers();
    }

    // Lấy project user theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectUser> getProjectUserById(@PathVariable Long id) {
        Optional<ProjectUser> projectUser = projectUserService.getProjectUserById(id);
        return projectUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lưu một project user mới
    @PostMapping
    public ResponseEntity<ProjectUser> createProjectUser(@RequestBody ProjectUser projectUser) {
        ProjectUser savedProjectUser = projectUserService.saveProjectUser(projectUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProjectUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectUser> updateProjectUser(@PathVariable Long id, @RequestBody ProjectUser projectUserDetails) {
        Optional<ProjectUser> projectUserOptional = projectUserService.getProjectUserById(id);
        if (projectUserOptional.isPresent()) {
            ProjectUser existingProjectUser = projectUserOptional.get();

            // Cập nhật thông tin ProjectUser
            existingProjectUser.setProjectId(projectUserDetails.getProjectId());
            existingProjectUser.setUserId(projectUserDetails.getUserId());
            existingProjectUser.setRole(projectUserDetails.getRole());

            ProjectUser updatedProjectUser = projectUserService.saveProjectUser(existingProjectUser);
            return ResponseEntity.ok(updatedProjectUser);
        } else {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy ProjectUser
        }
    }
    // Xóa project user theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectUser(@PathVariable Long id) {
        projectUserService.deleteProjectUser(id);
        return ResponseEntity.noContent().build();
    }
//    @PostMapping("/add/{projectId}/{jobId}")
//    public ResponseEntity<String> addUserToProject(
//            @PathVariable Long projectId,
//            @PathVariable Long jobId) {
//        String response = projectUserService.addUserToProject(projectId, jobId);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
    @PostMapping("/add/{projectId}/{jobId}")
    public ResponseEntity<String> addUsersToProject(@PathVariable Long projectId, @PathVariable Long jobId) {
//        try {
//            projectUserService.addUsersFromJobApplicationsToProject(projectId, jobId);
//            return ResponseEntity.ok("Người dùng đã được thêm vào dự án!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Đã xảy ra lỗi: " + e.getMessage());
//        }
        try {
            projectUserService.addUsersFromJobApplicationsToProject(projectId, jobId);
            return ResponseEntity.ok("Người dùng đã được thêm vào dự án!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi: " + e.getMessage());
        }

    }
    /// /
    @GetMapping("/project/{projectId}/users")
    public ResponseEntity<List<ProjectUser>> getUsersByProjectId(@PathVariable Long projectId) {
        List<ProjectUser> users = projectUserService.getUsersByProjectId(projectId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{userId}/projects")
    public ResponseEntity<List<ProjectUser>> getProjectsByUserId(@PathVariable Long userId) {
        List<ProjectUser> projects = projectUserService.getProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }
}
