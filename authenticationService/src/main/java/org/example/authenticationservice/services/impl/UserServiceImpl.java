package org.example.authenticationservice.services.impl;

import lombok.AllArgsConstructor;
import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.UserDTO;
import org.example.authenticationservice.entities.Role;
import org.example.authenticationservice.entities.User;
import org.example.authenticationservice.exception.EmailAlreadyExistsException;
import org.example.authenticationservice.mappers.UserMapper;
import org.example.authenticationservice.repository.UserRepo;
import org.example.authenticationservice.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public void createUser(CreateUserRequestDTO createUserRequest, Role role)  {
        if (existsByEmail(createUserRequest.email())){
            throw new EmailAlreadyExistsException("Email already exists: " + createUserRequest.email());
        }
        User user = userMapper.fromCreateUserRequestDTO_to_User(createUserRequest);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepo.findByEmail(email);
        return userMapper.fromUser_to_UserDTO(user);
    }


}
