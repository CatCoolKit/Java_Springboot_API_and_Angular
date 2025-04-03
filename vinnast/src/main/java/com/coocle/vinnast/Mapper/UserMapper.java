package com.coocle.vinnast.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.coocle.vinnast.DTO.UserDTO;
import com.coocle.vinnast.Entity.User;
import com.coocle.vinnast.Response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toUser(UserDTO request);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserDTO userDTO);

    // @Mapping(source = "firstname", target = "lastname")
    UserResponse toUserResponse(User user);
}
