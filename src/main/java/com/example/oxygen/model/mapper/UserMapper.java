package com.example.oxygen.model.mapper;

import com.example.oxygen.entity.User;
import com.example.oxygen.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);
}
