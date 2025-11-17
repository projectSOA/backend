package org.example.authenticationservice.mappers;

import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.UserDTO;
import org.example.authenticationservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromCreateUserRequestDTO_to_User(CreateUserRequestDTO createUserRequestDTO);

    UserDTO fromUser_to_UserDTO(User user);

    User fromUserDTO_to_User(UserDTO user);
}
