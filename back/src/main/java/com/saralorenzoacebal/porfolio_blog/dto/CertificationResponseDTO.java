package com.saralorenzoacebal.porfolio_blog.dto;

import com.saralorenzoacebal.porfolio_blog.model.Certification;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CertificationResponseDTO {

    private Long id;
    private String title;
    private String issuer;
    private String issuerLogo;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String credentialUrl;
    private String documentPath;
    private LocalDateTime createdAt;

    private String skillsLearned;

    

    public static CertificationResponseDTO fromEntity(Certification cert) {
        CertificationResponseDTO dto = new CertificationResponseDTO();
        dto.id = cert.getId();
        dto.title = cert.getTitle();
        dto.issuer = cert.getIssuer();
        dto.issuerLogo = cert.getIssuerLogo();
        dto.issueDate = cert.getIssueDate();
        dto.expirationDate = cert.getExpirationDate();
        dto.credentialUrl = cert.getCredentialUrl();
        dto.documentPath = cert.getDocumentPath();
        dto.createdAt = cert.getCreatedAt();
        dto.skillsLearned = cert.getSkillsLearned();

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

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuerLogo() {
        return issuerLogo;
    }

    public void setIssuerLogo(String issuerLogo) {
        this.issuerLogo = issuerLogo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public String getSkillsLearned() {
        return skillsLearned;
    }
    
}