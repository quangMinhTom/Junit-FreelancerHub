package com.example.demo.Repository;
import java.util.List;
import com.example.demo.Model.ProjectJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//public interface ProjectJobRepository extends JpaRepository<ProjectJob, Long> {}
//public interface ProjectJobRepository extends JpaRepository<ProjectJob, Long> {
//    List<ProjectJob> findAllByProjectId(Long projectId);
//}
public interface ProjectJobRepository extends JpaRepository<ProjectJob, Long> {
    List<ProjectJob> findByProjectId(Long projectId);
}
