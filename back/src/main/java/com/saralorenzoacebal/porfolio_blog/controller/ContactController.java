package com.saralorenzoacebal.porfolio_blog.controller;

import com.saralorenzoacebal.porfolio_blog.dto.ContactRequestDTO;
import com.saralorenzoacebal.porfolio_blog.service.ContactService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> sendContact(@Valid @RequestBody ContactRequestDTO request) {
        try {
            contactService.send(request);
            return ResponseEntity.ok(Map.of("message", "Mensaje enviado correctamente."));
        } catch (MailException exception) {
            logger.error("No se pudo enviar el formulario de contacto", exception);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("message", "No se pudo enviar el mensaje. Inténtalo de nuevo más tarde."));
        }
    }
}
