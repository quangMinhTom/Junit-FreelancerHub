package com.example.demo.Repository;

import com.example.demo.Model.TaskFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TaskFileRepository extends JpaRepository<TaskFile, Long> {

    List<TaskFile> findByTaskId(Long taskId);
    List<TaskFile> findByProjectId(Long projectId);
}
