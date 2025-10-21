package org.example.authenticationservice.services;

import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.mappers.UserMapper;
import org.example.authenticationservice.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    private CreateUserRequestDTO request1;

    @BeforeEach
    void setUp() {
        request1 = new CreateUserRequestDTO("hamid","chaouni",
                "hamidchaouni@gmail.com","0734993388","test");
    }

    @Test
    void should_return_true_because_user_exist(){
        userService.createUser(request1);

        boolean exists = userRepo.existsByEmail(request1.email());
        assertThat(exists).isTrue();
    }
}
