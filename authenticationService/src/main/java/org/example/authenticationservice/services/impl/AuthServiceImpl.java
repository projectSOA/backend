package org.example.authenticationservice.services.impl;

import lombok.AllArgsConstructor;
import org.example.authenticationservice.SecurityConfig.CustomUserDetails;
import org.example.authenticationservice.SecurityConfig.JwtService;
import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.LoginRequestDTO;
import org.example.authenticationservice.entities.Role;
import org.example.authenticationservice.services.AuthService;
import org.example.authenticationservice.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void SignUp(CreateUserRequestDTO createUserRequest) {
        userService.createUser(createUserRequest, Role.PASSENGER);
    }

    @Override
    public String signIn(LoginRequestDTO loginRequest) {
        try{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(userDetails);
        return jwtToken;
    } catch (BadCredentialsException e) {
        throw new BadCredentialsException("Invalid email orpassword");
    } catch (Exception e) {
        throw new RuntimeException("Authentication failed", e);
    }
    }
}
