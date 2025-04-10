package com.example.demo.Repository;
import java.util.List;
import com.example.demo.Model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJobId(Long jobId);
    List<Application> findByJobIdAndStatus(Long jobId, String status);

}

