package com.saralorenzoacebal.porfolio_blog.service;

import com.saralorenzoacebal.porfolio_blog.dto.ContactRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

@Service
public class ContactService {

    private final String sender;
    private final String clientId;
    private final String clientSecret;
    private final String refreshToken;
    private final String recipient;

    public ContactService(
                          @Value("${app.mail.sender}") String sender,
                          @Value("${app.mail.google.client-id}") String clientId,
                          @Value("${app.mail.google.client-secret}") String clientSecret,
                          @Value("${app.mail.google.refresh-token}") String refreshToken,
                          @Value("${app.contact.recipient}") String recipient) {
        this.sender = sender;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.recipient = recipient;
    }

    public void send(ContactRequestDTO request) {
        String text = """
                Nombre y apellidos: %s
                Correo electrónico: %s
                Empresa: %s

                Ideas para futuros proyectos:
                %s
                """.formatted(request.name(),
                request.email(),
                request.company() == null || request.company().isBlank() ? "No indicada" : request.company(),
                request.ideas() == null || request.ideas().isBlank() ? "No indicadas" : request.ideas());

        try {
            var transport = GoogleNetHttpTransport.newTrustedTransport();
            var jsonFactory = GsonFactory.getDefaultInstance();
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(transport)
                    .setJsonFactory(jsonFactory)
                    .setClientSecrets(clientId, clientSecret)
                    .build()
                    .setRefreshToken(refreshToken);
            if (!credential.refreshToken()) {
                throw new MailSendException("Gmail OAuth2 no pudo renovar el token de acceso");
            }

            Gmail gmail = new Gmail.Builder(
                    transport,
                    jsonFactory,
                    credential)
                    .setApplicationName("Porfolio Blog")
                    .build();

            MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
            mimeMessage.setFrom(new InternetAddress(sender));
            mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(recipient));
            mimeMessage.setReplyTo(new InternetAddress[]{new InternetAddress(request.email())});
            mimeMessage.setSubject("Contacto del portfolio: " + request.subject(), StandardCharsets.UTF_8.name());
            mimeMessage.setText(text, StandardCharsets.UTF_8.name());

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            mimeMessage.writeTo(output);
            Message message = new Message()
                    .setRaw(Base64.getUrlEncoder().withoutPadding().encodeToString(output.toByteArray()));
            gmail.users().messages().send("me", message).execute();
        } catch (Exception exception) {
            throw new MailSendException("No se pudo enviar el correo mediante Gmail OAuth2", exception);
        }
    }
}
