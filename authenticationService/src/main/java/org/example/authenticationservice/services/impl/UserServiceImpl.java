package org.example.authenticationservice.services.impl;

import lombok.AllArgsConstructor;
import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.mappers.UserMapper;
import org.example.authenticationservice.repository.UserRepo;
import org.example.authenticationservice.services.UserService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public void createUser(CreateUserRequestDTO createUserRequest) throws RuntimeException {
        if (existsByEmail(createUserRequest.email())){
            throw new RuntimeException("Email already exists");
        }
        userRepo.save(userMapper.fromCreateUserRequestDTO_to_User(createUserRequest));
    }
}
