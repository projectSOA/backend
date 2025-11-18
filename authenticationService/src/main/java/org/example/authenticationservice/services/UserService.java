package org.example.authenticationservice.services;

import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.UserDTO;
import org.example.authenticationservice.entities.Role;

import java.util.UUID;

public interface UserService {

    boolean existsByEmail(String email);
    void createUser(CreateUserRequestDTO createUserRequest, Role role) ;
    UserDTO getUserByEmail(String email);
    void deleteUser(UUID userId);
    UserDTO updateUser(UserDTO userDTO);
}
