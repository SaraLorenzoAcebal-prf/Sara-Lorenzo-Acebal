package com.saralorenzoacebal.porfolio_blog.repository;

import com.saralorenzoacebal.porfolio_blog.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatusOrderByCreatedAtDesc(Project.ProjectStatus status);

    Optional<Project> findBySlugAndStatus(String slug, Project.ProjectStatus status);

    Optional<Project> findBySlug(String slug);

}