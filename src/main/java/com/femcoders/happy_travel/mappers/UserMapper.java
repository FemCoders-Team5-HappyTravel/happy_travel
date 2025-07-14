package com.femcoders.happy_travel.mappers;

import com.femcoders.happy_travel.dtos.UserDto;
import com.femcoders.happy_travel.models.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        if(user == null) return  null;
        return UserDto.builder()
        .id((user.getId()))
                .username((user.getUsername()))
                .email((user.getEmail()))
                .roles(user.getRoles())
                .enabled(user.isEnabled())
                .build();
    }
    public static User toEntity(UserDto dto) {
        if (dto == null) return null;
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .roles(dto.getRoles())
                .enabled(dto.isEnabled())
                .build();
    }
}
