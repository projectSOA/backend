package org.example.authenticationservice.services;

import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.LoginRequestDTO;
import org.example.authenticationservice.dtos.UserDTO;

public interface AuthService {

    UserDTO SignUp(CreateUserRequestDTO createUserRequest);
    String signIn(LoginRequestDTO request);
}
