package com.saralorenzoacebal.porfolio_blog.dto;

import java.time.LocalDateTime;

import com.saralorenzoacebal.porfolio_blog.model.Post;

public class PostResponseDTO {

    private Long id;
    private String title;
    private String slug;
    private String excerpt;
    private String coverImage;
    private String status;

    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;

    public static PostResponseDTO fromEntity(Post post){
        PostResponseDTO dto = new PostResponseDTO();
        dto.id = post.getId();
        dto.title = post.getTitle();
        dto.slug = post.getSlug();
        dto.excerpt = post.getExcerpt();
        dto.coverImage = post.getCoverImage();
        dto.status = post.getStatus().name();
        dto.publishedAt = post.getPublishedAt();
        dto.createdAt = post.getCreatedAt();
        return dto;
    }

    //getters y setters

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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
