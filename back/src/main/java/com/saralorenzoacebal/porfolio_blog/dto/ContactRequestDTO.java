package com.saralorenzoacebal.porfolio_blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactRequestDTO(
        @NotBlank(message = "El nombre y apellidos son obligatorios")
        @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
        String name,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico no es válido")
        @Size(max = 254, message = "El correo electrónico no puede superar los 254 caracteres")
        String email,

        @Size(max = 120, message = "La empresa no puede superar los 120 caracteres")
        String company,

        @NotBlank(message = "El asunto es obligatorio")
        @Size(max = 160, message = "El asunto no puede superar los 160 caracteres")
        @Pattern(regexp = "^[^\\r\\n]*$", message = "El asunto no puede contener saltos de línea")
        String subject,

        @Size(max = 4000, message = "El mensaje no puede superar los 4000 caracteres")
        String ideas
) {
}
