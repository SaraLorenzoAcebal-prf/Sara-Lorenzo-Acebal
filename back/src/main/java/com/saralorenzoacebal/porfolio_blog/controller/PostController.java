package com.saralorenzoacebal.porfolio_blog.controller;

import com.saralorenzoacebal.porfolio_blog.dto.PostRequestDTO;
import com.saralorenzoacebal.porfolio_blog.dto.PostResponseDTO;
import com.saralorenzoacebal.porfolio_blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // --- Rutas públicas ---

    @GetMapping
    public List<PostResponseDTO> getPublishedPosts() {
        return postService.getPublishedPosts();
    }

    @GetMapping("/{slug}")
    public PostResponseDTO getPostBySlug(@PathVariable String slug) {
        return postService.getPublishedPostBySlug(slug);
    }

    // --- Rutas de admin (protegidas por JWT, via SecurityConfig) ---

    @GetMapping("/admin/all")
    public List<PostResponseDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/admin")
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO requestDTO) {
        return ResponseEntity.ok(postService.createPost(requestDTO));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequestDTO requestDTO) {
        return ResponseEntity.ok(postService.updatePost(id, requestDTO));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}