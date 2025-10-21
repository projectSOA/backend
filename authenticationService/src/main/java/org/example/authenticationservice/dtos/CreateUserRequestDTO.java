package org.example.authenticationservice.dtos;

public record CreateUserRequestDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String password
) {
}
