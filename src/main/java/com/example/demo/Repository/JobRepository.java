package com.example.demo.Repository;

import com.example.demo.Model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByUserId(Long userId);
//    List<Job> findById(Long id);
    List<Job> findByProjectid(Long projectId);
}
