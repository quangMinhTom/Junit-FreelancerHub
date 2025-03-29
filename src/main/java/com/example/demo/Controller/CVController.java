package com.example.demo.Controller;

import com.example.demo.Model.CV;
import com.example.demo.Model.ProjectJob;
import com.example.demo.Service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import com.example.demo.Repository.CVRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("api/cvs")
public class CVController {
    @Autowired
    private CVRepository cvRepository;
    private final CVService cvService;

    private static final String UPLOAD_DIR = "uploads/";
    @PostMapping("/{userId}")
    public ResponseEntity<CV> createCV(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file,@ModelAttribute CV cvDetails) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            // Lưu file vào thư mục
            String filePath = saveFile(file);

            // Tạo và lưu CV
            CV newCV = new CV();
            newCV.setUserId(userId);
            newCV.setTitle(cvDetails.getTitle());
            newCV.setPdfFilePath(filePath);

            CV savedCV = cvRepository.save(newCV);
            return new ResponseEntity<>(savedCV, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Hàm lưu file vào hệ thống
    private String saveFile(MultipartFile file) throws IOException {
        // Tạo thư mục nếu không có
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Đặt tên file và lưu vào thư mục
        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(path, file.getBytes());

        return path.toString();
    }
    @GetMapping("/cv/{cvId}/pdf")
    public ResponseEntity<Resource> getCvPdf(@PathVariable Long cvId) {
        // Tìm kiếm CV theo ID
        CV cv = cvRepository.findById(cvId).orElse(null);

        if (cv == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Đọc file PDF từ đường dẫn đã lưu
        File file = new File(cv.getPdfFilePath());
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Trả về file dưới dạng Resource
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    /// //

    @Autowired
    public CVController(CVService cvService) {
        this.cvService = cvService;
    }

    // Lấy tất cả các CV
    @GetMapping
    public List<CV> getAllCVs() {
        return cvService.getAllCVs();
    }

    // Lấy danh sách CV của một user cụ thể
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CV>> getCVsByUserId(@PathVariable Long userId) {
        List<CV> cvs = cvService.getCVsByUserId(userId);
        if (!cvs.isEmpty()) {
            return ResponseEntity.ok(cvs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Thêm CV mới cho một user
    @PostMapping("/user/{userId}")
    public ResponseEntity<CV> addCVForUser(@PathVariable Long userId, @RequestBody CV cv) {
        cv.setUserId(userId);
        CV savedCV = cvService.saveCV(cv);
        return ResponseEntity.status(201).body(savedCV); // Trả về mã 201 Created
    }

    // Lấy CV theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CV> getCVById(@PathVariable Long id) {
        Optional<CV> cv = cvService.getCVById(id);
        if (cv.isPresent()) {
            return ResponseEntity.ok(cv.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Cập nhật CV theo ID
    @PutMapping("/{id}")
    public ResponseEntity<CV> updateCV(@PathVariable Long id, @RequestBody CV cvDetails) {
        Optional<CV> cvOptional = cvService.getCVById(id);
        if (cvOptional.isPresent()) {
            CV existingCV = cvOptional.get();

            // Cập nhật thông tin CV
//            existingCV.setUserId(cvDetails.getUserId());
            existingCV.setPdfFilePath(cvDetails.getPdfFilePath());

            CV updatedCV = cvService.saveCV(existingCV);
            return ResponseEntity.ok(updatedCV);
        } else {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy CV
        }
    }

    // Xóa CV theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCV(@PathVariable Long id) {
        Optional<CV> cvOptional = cvService.getCVById(id);
        if (cvOptional.isPresent()) {
            cvService.deleteCV(id);
            return ResponseEntity.noContent().build(); // Mã 204 No Content khi xóa thành công
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
