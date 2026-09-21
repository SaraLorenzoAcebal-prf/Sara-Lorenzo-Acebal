package com.saralorenzoacebal.porfolio_blog.repository;

import com.saralorenzoacebal.porfolio_blog.model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    // No hay status DRAFT/PUBLISHED aqui, asi que listamos todas ordenadas por fecha
    List<Certification> findAllByOrderByIssueDateDesc();
}
