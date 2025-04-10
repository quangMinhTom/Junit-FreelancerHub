package com.example.demo.Service;

import com.example.demo.Model.UserDetail;
import com.example.demo.Repository.UserDetailRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailService {

    @Value("${image.upload.dir}")
    private String uploadDir;

    private final UserDetailRepository userDetailRepository;

    // Constructor Injection for UserDetailRepository
    public UserDetailService(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    // Phương thức tạo hoặc cập nhật thông tin chi tiết người dùng
    public UserDetail createOrUpdateUserDetail(UserDetail userDetail) {
        // Kiểm tra nếu userDetail đã tồn tại, nếu không thì tạo mới
        return userDetailRepository.save(userDetail);
    }

    // Phương thức lấy thông tin chi tiết người dùng theo userId
    public UserDetail getUserDetailByUserId(Long userId) {
        return userDetailRepository.findByUserId(userId).orElse(null);
    }

    public UserDetail updateUserDetail(UserDetail userDetail) throws Exception {
        // Kiểm tra xem thông tin chi tiết của người dùng có tồn tại không
        Optional<UserDetail> existingDetailOpt = userDetailRepository.findById(userDetail.getId());
        if (existingDetailOpt.isEmpty()) {
            throw new Exception("Thông tin chi tiết không tồn tại!");
        }

        UserDetail existingDetail = existingDetailOpt.get();
        // Cập nhật các thuộc tính
        existingDetail.setPhone(userDetail.getPhone());
        existingDetail.setAddress(userDetail.getAddress());
        existingDetail.setUsername(userDetail.getUsername());
        existingDetail.setEmail(userDetail.getEmail());
        existingDetail.setDescription(userDetail.getDescription());
        // Cập nhật thêm thuộc tính khác nếu cần

        return userDetailRepository.save(existingDetail);
    }


    public String uploadUserImage(Long userId, MultipartFile file) throws IOException {
        Optional<UserDetail> userOptional = userDetailRepository.findById(userId);


        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User không tồn tại");

        }

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File không được rỗng");
        }

        // Tạo tên file duy nhất
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;

        // Lưu file vào thư mục upload
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // Cập nhật cột image trong bảng UserDetail
        UserDetail user = userOptional.get();
        user.setProfileImage(fileName);
        userDetailRepository.save(user);

        return fileName; // Trả về tên file
    }

    // Lấy ảnh
    public byte[] getUserImage(String fileName) throws IOException {
        String filePath = uploadDir + File.separator + fileName;
        return Files.readAllBytes(Paths.get(filePath));
    }
}
