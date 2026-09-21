package com.saralorenzoacebal.porfolio_blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(length = 500)
    private String excerpt;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private String coverImage;

    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.DRAFT;

    public enum PostStatus{
        DRAFT, PUBLISHED;
    }

    private LocalDateTime publishedAt;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "related_project_id")
    private Project relatedProject;

    @ManyToOne
    @JoinColumn(name = "related_certification_id")
    private Certification relatedCertification;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }



    // getters y setters

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

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}