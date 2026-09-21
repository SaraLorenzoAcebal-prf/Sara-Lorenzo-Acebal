package com.saralorenzoacebal.porfolio_blog.service;

import com.saralorenzoacebal.porfolio_blog.dto.PostRequestDTO;
import com.saralorenzoacebal.porfolio_blog.dto.PostResponseDTO;
import com.saralorenzoacebal.porfolio_blog.model.Post;
import com.saralorenzoacebal.porfolio_blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //para la lectura pública

    public List<PostResponseDTO> getPublishedPosts() {
        return postRepository.findByStatusOrderByPublishedAtDesc(Post.PostStatus.PUBLISHED)
            .stream()
            .map(PostResponseDTO::fromEntity)
            .toList();
    }

    public PostResponseDTO getPublishedPostBySlug(String slug) {
        Post post = postRepository.findBySlugAndStatus(slug, Post.PostStatus.PUBLISHED)
            .orElseThrow(() -> new RuntimeException("Post no encontrado"));
        return PostResponseDTO.fromEntity(post);
    }

    //escritura, admin

    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll()
            .stream()
            .map(PostResponseDTO::fromEntity)
            .toList();
    }

    public PostResponseDTO createPost(PostRequestDTO requestDTO) {
        Post post = new Post();
        post.setTitle(requestDTO.getTitle());
        post.setExcerpt(requestDTO.getExcerpt());
        post.setContent(requestDTO.getContent());
        post.setCoverImage(requestDTO.getCoverImage());
        post.setSlug(generateUniqueSlug(requestDTO.getTitle()));
        applyPublishState(post, requestDTO.isPublish());

        return PostResponseDTO.fromEntity(postRepository.save(post));

    }

    public PostResponseDTO updatePost(Long id, PostRequestDTO requestDTO) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post no encontrado"));
        
        post.setTitle(requestDTO.getTitle());
        post.setExcerpt(requestDTO.getExcerpt());
        post.setContent(requestDTO.getContent());
        post.setCoverImage(requestDTO.getCoverImage());
        applyPublishState(post, requestDTO.isPublish());

        return PostResponseDTO.fromEntity(postRepository.save(post));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);

    }

    //Helpers privados

    private void applyPublishState(Post post, boolean publish) {
        if (publish && post.getStatus() != Post.PostStatus.PUBLISHED) {
            post.setStatus(Post.PostStatus.PUBLISHED);
            post.setPublishedAt(LocalDateTime.now());
        } else if (!publish) {
            post.setStatus(Post.PostStatus.DRAFT);
        }
    }

    private String generateUniqueSlug(String title) {
        String base = title.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");

        String slug = base;
        int counter = 1;
        while (postRepository.findBySlug(slug).isPresent()) {
            slug = base + "-" + counter;
            counter++;
        }
        return slug;
    }
    
}
