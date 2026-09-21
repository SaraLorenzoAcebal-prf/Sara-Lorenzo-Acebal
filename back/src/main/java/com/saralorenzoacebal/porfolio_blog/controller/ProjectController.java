package com.saralorenzoacebal.porfolio_blog.controller;

import com.saralorenzoacebal.porfolio_blog.dto.ProjectRequestDTO;
import com.saralorenzoacebal.porfolio_blog.dto.ProjectResponseDTO;
import com.saralorenzoacebal.porfolio_blog.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // --- Rutas públicas ---

    @GetMapping
    public List<ProjectResponseDTO> getPublishedProjects() {
        return projectService.getPublishedProjects();
    }

    @GetMapping("/{slug}")
    public ProjectResponseDTO getProjectBySlug(@PathVariable String slug) {
        return projectService.getPublishedProjectBySlug(slug);
    }

    // --- Rutas de admin ---

    @GetMapping("/admin/all")
    public List<ProjectResponseDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/admin")
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO requestDTO) {
        return ResponseEntity.ok(projectService.createProject(requestDTO));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequestDTO requestDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, requestDTO));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    
}