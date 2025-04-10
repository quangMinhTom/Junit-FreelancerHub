package com.example.demo.Repository;
import java.util.List;
import com.example.demo.Model.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {

    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
    List<ProjectUser> findByProjectId(Long projectId);
    List<ProjectUser> findByUserId(Long userId);
}
