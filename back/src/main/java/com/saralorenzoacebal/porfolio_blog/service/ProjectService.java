package com.saralorenzoacebal.porfolio_blog.service;

import com.saralorenzoacebal.porfolio_blog.dto.ProjectRequestDTO;
import com.saralorenzoacebal.porfolio_blog.dto.ProjectResponseDTO;
import com.saralorenzoacebal.porfolio_blog.model.Project;
import com.saralorenzoacebal.porfolio_blog.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    //Lectura pública

    public List<ProjectResponseDTO> getPublishedProjects() {
        return projectRepository.findByStatusOrderByCreatedAtDesc(Project.ProjectStatus.PUBLISHED)
                .stream()
                .map(ProjectResponseDTO::fromEntity)
                .toList();
    }

    public ProjectResponseDTO getPublishedProjectBySlug(String slug) {
        Project project = projectRepository.findBySlugAndStatus(slug, Project.ProjectStatus.PUBLISHED)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        return ProjectResponseDTO.fromEntity(project);
    }

    // Escritura, admin

    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectResponseDTO::fromEntity)
                .toList();
    }

    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {
        Project project = new Project();
        project.setTitle(requestDTO.getTitle());
        project.setDescription(requestDTO.getDescription());
        project.setContent(requestDTO.getContent());
        project.setRepoUrl(requestDTO.getRepoUrl());
        project.setDemoUrl(requestDTO.getDemoUrl());
        project.setCoverImage(requestDTO.getCoverImage());
        project.setTechnologies(requestDTO.getTechnologies());
        project.setSlug(generateUniqueSlug(requestDTO.getTitle()));
        applyPublishState(project, requestDTO.isPublish());

        return ProjectResponseDTO.fromEntity(projectRepository.save(project));
    }

    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO requestDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        project.setTitle(requestDTO.getTitle());
        project.setDescription(requestDTO.getDescription());
        project.setContent(requestDTO.getContent());
        project.setRepoUrl(requestDTO.getRepoUrl());
        project.setDemoUrl(requestDTO.getDemoUrl());
        project.setCoverImage(requestDTO.getCoverImage());
        project.setTechnologies(requestDTO.getTechnologies());
        applyPublishState(project, requestDTO.isPublish());

        return ProjectResponseDTO.fromEntity(projectRepository.save(project));
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    //helpers privados

    private void applyPublishState(Project project, boolean publish) {
        if (publish) {
            project.setStatus(Project.ProjectStatus.PUBLISHED);
        } else {
            project.setStatus(Project.ProjectStatus.DRAFT);
        }
    }

    private String generateUniqueSlug(String title) {
        String base = title.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");

        String slug = base;
        int counter = 1;
        while (projectRepository.findBySlug(slug).isPresent()) {
            slug = base + "-" + counter;
            counter++;
        }
        return slug;
    }
}
