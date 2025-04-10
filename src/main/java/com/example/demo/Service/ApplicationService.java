package com.example.demo.Service;

import com.example.demo.Model.Application;
import com.example.demo.Repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // Lấy tất cả applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Lấy application theo ID
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    // Lưu application mới
    public Application saveApplication(Application application) {
        // Xử lý logic khi lưu application (nếu cần)
        return applicationRepository.save(application);
    }

    // Xóa application theo ID
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    // Các phương thức khác nếu cần
    /// //0.1////

}
