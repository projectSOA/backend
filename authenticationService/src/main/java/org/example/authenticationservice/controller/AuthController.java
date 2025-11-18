package org.example.authenticationservice.controller;

import lombok.AllArgsConstructor;
import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.LoginRequestDTO;
import org.example.authenticationservice.dtos.UserDTO;
import org.example.authenticationservice.entities.User;
import org.example.authenticationservice.exception.AccountNotActivatedException;
import org.example.authenticationservice.exception.EmailAlreadyExistsException;
import org.example.authenticationservice.mappers.UserMapper;
import org.example.authenticationservice.services.AuthService;
import org.example.authenticationservice.services.EmailService;
import org.example.authenticationservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private UserMapper userMapper;
    private EmailService emailService;

    private static final Integer PASSWORD_LENGTH = 6;
    private final SecureRandom random;

    @Autowired
    public AuthController(UserService userService, AuthService authService, UserMapper userMapper, EmailService emailService){
        this.userService = userService;
        this.authService = authService;
        this.userMapper = userMapper;
        this.random = new SecureRandom();
        this.emailService = emailService;
    }
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

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId){
        userService.deleteUser(userId);
        return  ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        UserDTO user = userService.updateUser(userDTO);
        return  ResponseEntity.ok(user);
    }

    @PostMapping("/add-driver")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
        User user = userMapper.fromUserDTO_to_User(userDTO);
        String password = generatePassword();
        user.setPassword(password);
        CreateUserRequestDTO createUserRequest = userMapper.fromUser_to_CreateUserRequestDTO(user);
        try {
            authService.SignUp(createUserRequest);
            emailService.sendPasswordEmail(createUserRequest.email(),password);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(EmailAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("email already exists");
        }

    }

    private String generatePassword(){
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i=0;i<6;i++){
            if(random.nextBoolean()){
                char letter = (char) (random.nextBoolean()
                        ? 'A' + random.nextInt(26)
                        : 'a' + random.nextInt(26));
                password.append(letter);
            }
            else{
                password.append(random.nextInt(10));
            }
        }
        return password.toString();
    }


}
