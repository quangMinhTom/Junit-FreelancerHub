package com.example.demo.Controller;
import java.util.Map;
import com.example.demo.Model.Application;
import com.example.demo.Model.Status;
import com.example.demo.Service.ApplicationService;
import com.example.demo.Repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // Lấy tất cả applications
    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    // Lấy application theo ID
    @GetMapping("/{id}")
    public Optional<Application> getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id);
    }

    @PostMapping
    public ResponseEntity<Application> saveApplication(@RequestBody Application application) {
        // Lưu application mới và trả về response với mã 201 (Created)
        Application savedApplication = applicationService.saveApplication(application);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    // Xóa application theo ID
//    @DeleteMapping("/{id}")
//    public void deleteApplication(@PathVariable Long id) {
//        applicationService.deleteApplication(id);
//    }
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application applicationDetails) {
        // Tìm application theo id
        Optional<Application> applicationOptional = applicationService.getApplicationById(id);
        if (applicationOptional.isPresent()) {
            Application existingApplication = applicationOptional.get();

            // Cập nhật các trường thông tin của application
            existingApplication.setJobId(applicationDetails.getJobId());
            existingApplication.setUserId(applicationDetails.getUserId());
            existingApplication.setCvId(applicationDetails.getCvId());
            existingApplication.setStatus(applicationDetails.getStatus());
            existingApplication.setJobtitle(applicationDetails.getJobtitle());
            existingApplication.setUsername(applicationDetails.getUsername());
            existingApplication.setReason(applicationDetails.getReason());
            // Nếu cần, có thể cập nhật thêm các trường khác, chẳng hạn như thời gian tạo hoặc cập nhật.

            // Lưu application đã cập nhật
            Application updatedApplication = applicationService.saveApplication(existingApplication);

            // Trả về response với status OK và thông tin application đã cập nhật
            return ResponseEntity.ok(updatedApplication);
        } else {
            // Nếu không tìm thấy application với id, trả về response "Not Found"
            return ResponseEntity.notFound().build();
        }
    }


    // Các endpoint khác nếu cần
    ///0.1////////
    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsByJobId(@PathVariable Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    // Cập nhật trạng thái đơn đăng ký
    @PutMapping("update/{applicationId}")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long applicationId, @RequestBody Map<String, String> statusUpdate) {

        Optional<Application> application = applicationRepository.findById(applicationId);
        if (application.isPresent()) {
            Application app = application.get();
            app.setStatus(statusUpdate.get("status"));
            applicationRepository.save(app);
            return ResponseEntity.ok(app);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Xóa đơn đăng ký
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long applicationId) {
        Optional<Application> application = applicationRepository.findById(applicationId);
        if (application.isPresent()) {
            applicationRepository.delete(application.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
