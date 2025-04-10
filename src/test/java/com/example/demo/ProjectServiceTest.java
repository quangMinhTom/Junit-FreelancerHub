package com.example.demo;

import com.example.demo.Model.Project;
import com.example.demo.Service.ProjectService;
import com.example.demo.Repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project1;
    private Project project2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project1 = new Project(1L, 101L, "user1", "Project A", "Mô tả A", BigDecimal.valueOf(1000), "in_progress", null);
        project2 = new Project(2L, 102L, "user2", "Project B", "Mô tả B", BigDecimal.valueOf(2000), "completed", null);
    }

    @Test
    void testGetAllProjects() {
        // Arrange
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        // Act
        List<Project> projects = projectService.getAllProjects();

        // Assert
        assertEquals(2, projects.size());
        verify(projectRepository, times(1)).findAll();
        for(Project project:projects) {
            System.out.println(project);
        }
    }

    @Test
    void testGetProjectById_Found() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));

        // Act
        Optional<Project> foundProject = projectService.getProjectById(1L);

        // Assert
        assertTrue(foundProject.isPresent());
        assertEquals("Project A", foundProject.get().getTitle());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProjectById_NotFound() {
        // Arrange
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Project> foundProject = projectService.getProjectById(999L);

        // Assert
        assertFalse(foundProject.isPresent());
        verify(projectRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveProject() {
        // Arrange
        when(projectRepository.save(project1)).thenReturn(project1);

        // Act
        Project saved = projectService.saveProject(project1);

        // Assert
        assertNotNull(saved);
        assertEquals("Project A", saved.getTitle());
        verify(projectRepository, times(1)).save(project1);
    }

    @Test
    void testDeleteProject() {
        // Act
        projectService.deleteProject(1L);

        // Assert
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetProjectsByUserId() {
        // Arrange
        when(projectRepository.findByUserId(101L)).thenReturn(List.of(project1));

        // Act
        List<Project> userProjects = projectService.getProjectsByUserId(101L);

        // Assert
        assertEquals(1, userProjects.size());
        assertEquals(101L, userProjects.get(0).getUserId());
        verify(projectRepository, times(1)).findByUserId(101L);
    }
}
