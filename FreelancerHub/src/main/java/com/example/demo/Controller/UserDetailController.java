package com.example.demo.Controller;

import com.example.demo.Model.UserDetail;
import com.example.demo.Service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
///  image
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/api/user-details")
public class UserDetailController {

    private final UserDetailService userDetailService;

    // Constructor Injection for UserDetailService
    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    // API để tạo hoặc cập nhật thông tin chi tiết người dùng
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createUserDetail(
            @PathVariable Long userId,
            @RequestBody UserDetail userDetail) {

        // Kiểm tra nếu userId từ URL và userDetail là giống nhau
        if (!userId.equals(userDetail.getUserId())) {
            return ResponseEntity.badRequest().body("userId trong URL và trong body không khớp");
        }

        try {
            // Gọi service để xử lý tạo hoặc cập nhật UserDetail
            UserDetail createdUserDetail = userDetailService.createOrUpdateUserDetail(userDetail);
            return ResponseEntity.ok(createdUserDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi: " + e.getMessage());
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUserDetail(
            @PathVariable Long userId,
            @RequestBody UserDetail userDetail) {

        if (!userId.equals(userDetail.getUserId())) {
            return ResponseEntity.badRequest().body("userId trong URL và trong body không khớp");
        }

        try {
            UserDetail updatedUserDetail = userDetailService.updateUserDetail(userDetail);
            return ResponseEntity.ok(updatedUserDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi: " + e.getMessage());
        }
    }
    // API để lấy thông tin chi tiết người dùng
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetail(@PathVariable Long userId) {
        try {
            UserDetail userDetail = userDetailService.getUserDetailByUserId(userId);
            if (userDetail == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy thông tin người dùng");
            }
            return ResponseEntity.ok(userDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi: " + e.getMessage());
        }
    }



    ///  image ///
    @PostMapping("/{userId}/upload-image")
    public ResponseEntity<String> uploadUserImage(
            @PathVariable Long userId,
            @RequestParam("image") MultipartFile file) {
        try {
            String fileName = userDetailService.uploadUserImage(userId, file);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi upload file: " + e.getMessage());
        }
    }

    // Lấy ảnh user
    @GetMapping("/image/{fileName}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable String fileName) {
        try {
            byte[] imageData = userDetailService.getUserImage(fileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
