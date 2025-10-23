package org.example.authenticationservice.services;

import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.entities.Role;

public interface UserService {

    boolean existsByEmail(String email);
    void createUser(CreateUserRequestDTO createUserRequest, Role role) ;
}
