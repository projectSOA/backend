package org.example.authenticationservice.mappers;

import org.example.authenticationservice.dtos.CreateUserRequestDTO;
import org.example.authenticationservice.dtos.UserDTO;
import org.example.authenticationservice.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromCreateUserRequestDTO_to_User(CreateUserRequestDTO createUserRequestDTO);

    UserDTO fromUser_to_UserDTO(User user);

    List<UserDTO> fromListUser_to_ListUserDTO(List<User> users);

    User fromUserDTO_to_User(UserDTO user);

    CreateUserRequestDTO fromUser_to_CreateUserRequestDTO(User user);
}
