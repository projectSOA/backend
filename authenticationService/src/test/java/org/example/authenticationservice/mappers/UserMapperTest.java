package org.example.authenticationservice.mappers;

import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void should_map_createUserRequestDTO_to_user() {
       CreateUserRequestDTO  request = new CreateUserRequestDTO("hamid","chaouni",
               "hamidchaouni@gmail.com","0703347745","tes");

       User user = userMapper.fromCreateUserRequestDTO_to_User(request);

       assertThat(user.getEmail()).isEqualTo(request.email());
       assertThat(user.getPassword()).isEqualTo(request.password());
       assertThat(user.getFirstName()).isEqualTo( request.firstName());
       assertThat(user.getLastName()).isEqualTo( request.lastName());
       assertThat(user.getPhoneNumber()).isEqualTo(request.phoneNumber());
       assertThat(user.isAccountActivated()).isFalse();
    }
}
