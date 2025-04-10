package com.example.demo.Service;

import com.example.demo.Model.CV;
import com.example.demo.Repository.CVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CVService {

    private final CVRepository cvRepository;

    @Autowired
    public CVService(CVRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    // Lấy tất cả các CV
    public List<CV> getAllCVs() {
        return cvRepository.findAll();
    }

    // Lấy CV theo ID
    public Optional<CV> getCVById(Long id) {
        return cvRepository.findById(id);
    }

    // Lưu một CV mới
    public CV saveCV(CV cv) {
        return cvRepository.save(cv);
    }

    // Xóa CV theo ID
    public void deleteCV(Long id) {
        cvRepository.deleteById(id);
    }

    // Lấy CV theo userId
    public List<CV> getCVsByUserId(Long userId) {
        return cvRepository.findByUserId(userId);
    }

}
