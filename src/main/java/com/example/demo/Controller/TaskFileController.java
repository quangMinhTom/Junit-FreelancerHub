package com.example.demo.Controller;

import com.example.demo.Model.TaskFile;
import com.example.demo.Service.TaskFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//
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
@RequestMapping("api/task-files")
public class TaskFileController {

    private final TaskFileService taskFileService;

    @Autowired
    public TaskFileController(TaskFileService taskFileService) {
        this.taskFileService = taskFileService;
    }

    // Lấy tất cả task files
    @GetMapping
    public List<TaskFile> getAllTaskFiles() {
        return taskFileService.getAllTaskFiles();
    }

    // Lấy task file theo ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskFile> getTaskFileById(@PathVariable Long id) {
        Optional<TaskFile> taskFile = taskFileService.getTaskFileById(id);
        return taskFile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo task file mới
    @PostMapping
    public ResponseEntity<TaskFile> createTaskFile(@RequestBody TaskFile taskFile) {
        try {
            TaskFile newTaskFile = taskFileService.saveTaskFile(taskFile);
            return ResponseEntity.ok(newTaskFile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Cập nhật task file
    @PutMapping("/{id}")
    public ResponseEntity<TaskFile> updateTaskFile(@PathVariable Long id, @RequestBody TaskFile taskFileDetails) {
        Optional<TaskFile> taskFileOptional = taskFileService.getTaskFileById(id);
        if (taskFileOptional.isPresent()) {
            TaskFile existingTaskFile = taskFileOptional.get();

            existingTaskFile.setTaskId(taskFileDetails.getTaskId());
            existingTaskFile.setUserId(taskFileDetails.getUserId());
            existingTaskFile.setFilePath(taskFileDetails.getFilePath());
            existingTaskFile.setFileType(taskFileDetails.getFileType());
            existingTaskFile.setProjectId(taskFileDetails.getProjectId());

            TaskFile updatedTaskFile = taskFileService.saveTaskFile(existingTaskFile);
            return ResponseEntity.ok(updatedTaskFile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa task file
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskFile(@PathVariable Long id) {
        try {
            taskFileService.deleteTaskFile(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadTaskFile(@RequestParam("taskId") Long taskId,
                                                 @RequestParam("userId") Long userId,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("fileType") String fileType,
                                                 @RequestParam("projectId") Long projectId) {
        try {
            String filePath = taskFileService.saveUploadedFile(file, taskId, userId, fileType,projectId);
            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload failed");
        }
    }
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskFile>> getTaskFilesByTaskId(@PathVariable Long taskId) {
        List<TaskFile> taskFiles = taskFileService.getTaskFilesByTaskId(taskId);
        if (!taskFiles.isEmpty()) {
            return ResponseEntity.ok(taskFiles);
        } else {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có dữ liệu
        }
    }
    @GetMapping("/task/{taskId}/file/{fileName}")
    public ResponseEntity<Resource> getTaskFile(@PathVariable Long taskId, @PathVariable String fileName) {
        // Sử dụng fileName trực tiếp như đường dẫn đầy đủ tới file
        File file = new File(fileName);

        // Kiểm tra nếu file tồn tại
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Đọc file và trả về như một Resource
        Resource resource = new FileSystemResource(file);

        // Trả về file với MediaType phù hợp (PDF, Excel, v.v...)
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)  // MediaType mặc định nếu không xác định được
                .body(resource);
    }

    private String getFileType(String fileName) {
        if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (fileName.endsWith(".xlsx")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (fileName.endsWith(".xls")) {
            return "application/vnd.ms-excel";
        } else if (fileName.endsWith(".docx")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else {
            return "application/octet-stream";  // Nếu không nhận dạng được kiểu file
        }
    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskFile>> getTaskFilesByProjectId(@PathVariable Long projectId) {
        List<TaskFile> taskFiles = taskFileService.getTaskFilesByProjectId(projectId);
        if (!taskFiles.isEmpty()) {
            return ResponseEntity.ok(taskFiles);
        } else {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có dữ liệu
        }
    }

}
