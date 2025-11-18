package org.example.authenticationservice.dtos;

import org.example.authenticationservice.entities.Role;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String password,
        Role role,
        boolean isAccountActivated
) {
}
