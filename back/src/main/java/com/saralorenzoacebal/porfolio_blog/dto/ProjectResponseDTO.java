package com.saralorenzoacebal.porfolio_blog.dto;

import com.saralorenzoacebal.porfolio_blog.model.Project;
import java.time.LocalDateTime;

public class ProjectResponseDTO {
    
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String content;
    private String repoUrl;
    private String demoUrl;
    private String coverImage;
    private String technologies;
    private String status;
    private LocalDateTime createdAt;

    public static ProjectResponseDTO fromEntity(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.id = project.getId();
        dto.title = project.getTitle();
        dto.slug = project.getSlug();
        dto.description = project.getDescription();
        dto.content = project.getContent();
        dto.repoUrl = project.getRepoUrl();
        dto.demoUrl = project.getDemoUrl();
        dto.coverImage = project.getCoverImage();
        dto.technologies = project.getTechnologies();
        dto.status = project.getStatus().name();
        dto.createdAt = project.getCreatedAt();
        return dto;
    }

    //getter y setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getDemoUrl() {
        return demoUrl;
    }

    public void setDemoUrl(String demoUrl) {
        this.demoUrl = demoUrl;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    
}

