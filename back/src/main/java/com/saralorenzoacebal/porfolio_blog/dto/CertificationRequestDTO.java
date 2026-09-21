package com.saralorenzoacebal.porfolio_blog.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CertificationRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El emisor es obligatorio")
    private String issuer;

    private String issuerLogo;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String credentialUrl;
    private String documentPath;
    private String skillsLearned;

    //getters y setters
    
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
    public String getSkillsLearned() {
        return skillsLearned;
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

    
}