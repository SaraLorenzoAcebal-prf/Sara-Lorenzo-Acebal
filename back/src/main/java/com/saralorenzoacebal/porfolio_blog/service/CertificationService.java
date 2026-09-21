package com.saralorenzoacebal.porfolio_blog.service;

import com.saralorenzoacebal.porfolio_blog.dto.CertificationRequestDTO;
import com.saralorenzoacebal.porfolio_blog.dto.CertificationResponseDTO;
import com.saralorenzoacebal.porfolio_blog.model.Certification;
import com.saralorenzoacebal.porfolio_blog.repository.CertificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;

    public CertificationService(CertificationRepository certificationRepository) {
        this.certificationRepository = certificationRepository;
    }

    //lectura pública (todas)

    public List<CertificationResponseDTO> getAllCertifications() {
        return certificationRepository.findAllByOrderByIssueDateDesc()
                .stream()
                .map(CertificationResponseDTO::fromEntity)
                .toList();
    }

    //escritura, admin

    public CertificationResponseDTO createCertification(CertificationRequestDTO requestDTO) {
        Certification cert = new Certification();
        applyFields(cert, requestDTO);
        return CertificationResponseDTO.fromEntity(certificationRepository.save(cert));
    }

    public CertificationResponseDTO updateCertification(Long id, CertificationRequestDTO requestDTO) {
        Certification cert = certificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado no encontrado"));
        applyFields(cert, requestDTO);
        return CertificationResponseDTO.fromEntity(certificationRepository.save(cert));
    }

    public void deleteCertification(Long id) {
        certificationRepository.deleteById(id);
    }

    //helper privado

    private void applyFields(Certification cert, CertificationRequestDTO dto) {
        cert.setTitle(dto.getTitle());
        cert.setIssuer(dto.getIssuer());
        cert.setIssuerLogo(dto.getIssuerLogo());
        cert.setIssueDate(dto.getIssueDate());
        cert.setExpirationDate(dto.getExpirationDate());
        cert.setCredentialUrl(dto.getCredentialUrl());
        cert.setDocumentPath(dto.getDocumentPath());
        cert.setSkillsLearned(dto.getSkillsLearned());
    }
}