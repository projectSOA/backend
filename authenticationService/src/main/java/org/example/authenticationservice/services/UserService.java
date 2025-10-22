package org.example.authenticationservice.services;

import org.example.authenticationservice.dtos.CreateUserRequestDTO;

public interface UserService {

    boolean existsByEmail(String email);
    public void createUser(CreateUserRequestDTO createUserRequest);
}
