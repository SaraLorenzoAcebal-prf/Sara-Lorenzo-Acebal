package com.saralorenzoacebal.porfolio_blog.controller;

import com.saralorenzoacebal.porfolio_blog.dto.CertificationRequestDTO;
import com.saralorenzoacebal.porfolio_blog.dto.CertificationResponseDTO;
import com.saralorenzoacebal.porfolio_blog.service.CertificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    //Ruta pública

    @GetMapping
    public List<CertificationResponseDTO> getAllCertifications() {
        return certificationService.getAllCertifications();
    }

    //Rutas de admin

    @PostMapping("/admin")
    public ResponseEntity<CertificationResponseDTO> createCertification(@Valid @RequestBody CertificationRequestDTO requestDTO) {
        return ResponseEntity.ok(certificationService.createCertification(requestDTO));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<CertificationResponseDTO> updateCertification(@PathVariable Long id, @Valid @RequestBody CertificationRequestDTO requestDTO) {
        return ResponseEntity.ok(certificationService.updateCertification(id, requestDTO));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
        certificationService.deleteCertification(id);
        return ResponseEntity.noContent().build();
    }
}