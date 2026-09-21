package com.saralorenzoacebal.porfolio_blog.repository;

import com.saralorenzoacebal.porfolio_blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>{
    // solo para el listado público del blog
    List<Post> findByStatusOrderByPublishedAtDesc(Post.PostStatus status);

    //detalle público de un post por su slug
    Optional<Post> findBySlugAndStatus(String slug, Post.PostStatus status);
    
    //comprobar que hay duplicados
    Optional<Post> findBySlug(String slug);
} 
