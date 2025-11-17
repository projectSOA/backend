package org.example.authenticationservice.services;

import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.LoginRequestDTO;

public interface AuthService {

    void SignUp(CreateUserRequestDTO createUserRequest);
    String signIn(LoginRequestDTO request);
}
