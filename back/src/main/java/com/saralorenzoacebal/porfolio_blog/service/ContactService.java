package com.saralorenzoacebal.porfolio_blog.service;

import com.saralorenzoacebal.porfolio_blog.dto.ContactRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final JavaMailSender mailSender;
    private final String recipient;

    public ContactService(JavaMailSender mailSender,
                          @Value("${app.contact.recipient}") String recipient) {
        this.mailSender = mailSender;
        this.recipient = recipient;
    }

    public void send(ContactRequestDTO request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setReplyTo(request.email());
        message.setSubject("Contacto del portfolio: " + request.subject());
        message.setText("""
                Nombre y apellidos: %s
                Correo electrónico: %s
                Empresa: %s

                Ideas para futuros proyectos:
                %s
                """.formatted(request.name(),
                request.email(),
                request.company() == null || request.company().isBlank() ? "No indicada" : request.company(),
                request.ideas() == null || request.ideas().isBlank() ? "No indicadas" : request.ideas()));

        mailSender.send(message);
    }
}
