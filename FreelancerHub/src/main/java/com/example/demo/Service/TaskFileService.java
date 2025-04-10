package com.example.demo.Service;

import com.example.demo.Model.TaskFile;
import com.example.demo.Repository.TaskFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TaskFileService {
    private final String UPLOAD_DIR = "uploads/";

    private final TaskFileRepository taskFileRepository;

    @Autowired
    public TaskFileService(TaskFileRepository taskFileRepository) {
        this.taskFileRepository = taskFileRepository;
    }

    // Lấy tất cả các task file
    public List<TaskFile> getAllTaskFiles() {
        return taskFileRepository.findAll();
    }

    // Lấy task file theo ID
    public Optional<TaskFile> getTaskFileById(Long id) {
        return taskFileRepository.findById(id);
    }

    // Lưu một task file mới
    public TaskFile saveTaskFile(TaskFile taskFile) {
        return taskFileRepository.save(taskFile);
    }

    // Xóa task file theo ID
    public void deleteTaskFile(Long id) {
        taskFileRepository.deleteById(id);
    }

    public String saveUploadedFile(MultipartFile file, Long taskId, Long userId, String fileType, Long projectId) throws IOException {
        // 1. Kiểm tra file có rỗng không
        if (file.isEmpty()) {
            throw new IllegalStateException("File không được để trống");
        }

        // 2. Lấy thông tin file
        String originalFileName = file.getOriginalFilename();
        String newFileName = System.currentTimeMillis() + "_" + originalFileName; // Đặt tên file duy nhất
        Path filePath = Paths.get(UPLOAD_DIR + newFileName);

        // 3. Lưu file vào server
        Files.copy(file.getInputStream(), filePath);

        // 4. Tạo đối tượng TaskFile và lưu vào database
        TaskFile taskFile = new TaskFile();
        taskFile.setTaskId(taskId);
        taskFile.setUserId(userId);
        taskFile.setFilePath(newFileName);
        taskFile.setFileType(fileType);
        taskFile.setProjectId(projectId);

        taskFileRepository.save(taskFile);

        // 5. Trả về đường dẫn file đã lưu
        return filePath.toString();
    }

    public List<TaskFile> getTaskFilesByTaskId(Long taskId) {
        return taskFileRepository.findByTaskId(taskId);
    }
    public List<TaskFile> getTaskFilesByProjectId(Long projectId) {
        return taskFileRepository.findByProjectId(projectId);
    }
}
