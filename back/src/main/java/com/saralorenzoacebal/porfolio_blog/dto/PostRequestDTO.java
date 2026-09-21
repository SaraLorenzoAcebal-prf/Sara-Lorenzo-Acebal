package com.saralorenzoacebal.porfolio_blog.dto;

import jakarta.validation.constraints.NotBlank;

public class PostRequestDTO {
    
    @NotBlank(message = "El título es obligatorio")
    private String title;

    private String excerpt;

    @NotBlank(message = "El contenido es obligatorio")
    private String content;

    private String coverImage;

    private boolean publish; //true = publicar ya; false = guardar como borrador


    //getters y setters
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    
}
