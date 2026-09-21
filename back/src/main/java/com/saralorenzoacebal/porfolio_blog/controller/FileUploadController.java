package com.saralorenzoacebal.porfolio_blog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    private static final Map<String, String> ALLOWED_TYPES = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/webp", ".webp",
            "application/pdf", ".pdf");

    @Value("${app.upload.dir}")
    private String uploadDir;

    @PostMapping("/admin")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.badRequest().body(Map.of("error", "El archivo está vacío o supera 10 MB"));
        }

        try {
            String extension = ALLOWED_TYPES.get(file.getContentType());
            if (extension == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Tipo de archivo no permitido"));
            }
            if (!hasExpectedContent(file.getBytes(), contentType)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "El contenido no coincide con el tipo de archivo"));
            }
            String filename = UUID.randomUUID() + extension;

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(file.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(Map.of("path", "/uploads/" + filename));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error al guardar el archivo"));
        }
    }

    private boolean hasExpectedContent(byte[] content, String contentType) {
        if (contentType != null && contentType.startsWith("image/")) {
            try {
                return ImageIO.read(new ByteArrayInputStream(content)) != null;
            } catch (IOException exception) {
                return false;
            }
        }
        return content.length >= 4
                && content[0] == '%'
                && content[1] == 'P'
                && content[2] == 'D'
                && content[3] == 'F';
    }
}