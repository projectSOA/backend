package org.example.authenticationservice.controller;

import lombok.AllArgsConstructor;
import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.LoginRequestDTO;
import org.example.authenticationservice.exception.AccountNotActivatedException;
import org.example.authenticationservice.exception.EmailAlreadyExistsException;
import org.example.authenticationservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    // we should add sign in and sign up , and creating user like driver only a manager can do that and an admin create a manager

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginRequestDTO loginRequest) {
        try {
            return ResponseEntity.ok(authService.signIn(loginRequest));
        }catch (AccountNotActivatedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred during authentication"));
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody CreateUserRequestDTO createUserRequest) {
        try {
            authService.SignUp(createUserRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(EmailAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("email already exists");
        }

    }



}
