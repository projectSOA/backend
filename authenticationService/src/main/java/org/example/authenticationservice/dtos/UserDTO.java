package org.example.authenticationservice.dtos;

import org.example.authenticationservice.entities.Role;

public record UserDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String password,
        Role role,
        boolean isAccountActivated
) {
}
