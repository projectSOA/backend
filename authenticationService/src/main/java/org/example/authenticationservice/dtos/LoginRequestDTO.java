package org.example.authenticationservice.dtos;

public record LoginRequestDTO(
        String email,
        String password
) {
}
