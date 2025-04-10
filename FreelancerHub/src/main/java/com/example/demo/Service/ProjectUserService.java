package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectUserService {

    @Autowired
    private ProjectJobRepository projectJobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProjectUserRepository projectUserRepository;

    @Autowired
    private  JobRepository jobRepository;

    @Autowired
    private  ProjectRepository projectRepository;

    @Autowired
    public ProjectUserService(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

    // Lấy tất cả các ProjectUser
    public List<ProjectUser> getAllProjectUsers() {
        return projectUserRepository.findAll();
    }

    // Lấy ProjectUser theo ID
    public Optional<ProjectUser> getProjectUserById(Long id) {
        return projectUserRepository.findById(id);
    }

    // Lưu một ProjectUser mới
    public ProjectUser saveProjectUser(ProjectUser projectUser) {
        return projectUserRepository.save(projectUser);
    }

    // Xóa ProjectUser theo ID
    public void deleteProjectUser(Long id) {
        projectUserRepository.deleteById(id);
    }

    @Transactional
    public void addUsersFromJobApplicationsToProject(Long projectId, Long jobId) {
        // Lấy tất cả ứng viên cho công việc với jobId và chỉ lấy những ứng viên có status là "good"
//        List<Application> applications = applicationRepository.findByJobIdAndStatus(jobId, "good");
//
//        // Kiểm tra xem có ứng viên nào có status = "good" không
//        if (applications.isEmpty()) {
//            throw new RuntimeException("Không có ứng viên có status 'good' cho công việc này!");
//        }
//
//        // Thêm người dùng vào project_user
//        for (Application application : applications) {
//            ProjectUser projectUser = new ProjectUser();
//            projectUser.setProjectId(projectId);
//            projectUser.setUserId(application.getUserId());  // Lấy userId từ Application
//            projectUser.setRole("freelancer");  // Ví dụ, tất cả ứng viên có status 'good' là "freelancer"
//            projectUserRepository.save(projectUser);  // Thêm vào bảng project_user
//        }
        List<Application> applications = applicationRepository.findByJobIdAndStatus(jobId, "approved");
        Optional<Job> jobs = jobRepository.findById(jobId);
        Optional<Project> project = projectRepository.findById(projectId);

        // Kiểm tra xem có ứng viên nào có status = "good" không
        if (applications.isEmpty()) {
            throw new RuntimeException("Không có ứng viên có status 'approved' cho công việc này!");
        }

        // Thêm người dùng vào project_user
        for (Application application : applications) {
            Long userId = application.getUserId(); // Lấy userId từ Application
            String jobname = jobs.get().getTitle();
            String projectname = project.get().getTitle();
            String username = application.getUsername(); //


            // Kiểm tra xem user đã có trong project_user chưa
            boolean exists = projectUserRepository.existsByProjectIdAndUserId(projectId, userId);

            if (exists) {
                // Nếu user đã tồn tại, báo cáo và bỏ qua
                System.out.println("User ID " + userId + " đã tồn tại trong Project ID " + projectId);
                continue; // Bỏ qua và không thêm user này
            }

            // Tạo mới ProjectUser nếu chưa tồn tại
            ProjectUser projectUser = new ProjectUser();
            projectUser.setProjectId(projectId);
            projectUser.setUserId(userId);
            projectUser.setRole("freelancer"); // Ví dụ: gán role là freelancer
            projectUser.setJobname(jobname);
            projectUser.setProjectname(projectname);
            projectUser.setUsername(username);
            projectUser.setJobid(jobId);
            projectUserRepository.save(projectUser); // Thêm vào bảng project_user
        }
    }
    public List<ProjectUser> getUsersByProjectId(Long projectId) {
        return projectUserRepository.findByProjectId(projectId);
    }

    public List<ProjectUser> getProjectsByUserId(Long userId) {
        return projectUserRepository.findByUserId(userId);
    }
}
