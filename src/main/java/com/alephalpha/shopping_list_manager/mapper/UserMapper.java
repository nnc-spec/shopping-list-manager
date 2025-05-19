package com.alephalpha.shopping_list_manager.mapper;

import com.alephalpha.shopping_list_manager.dto.UserDto;
import com.alephalpha.shopping_list_manager.dto.UserCreateDto;
import com.alephalpha.shopping_list_manager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public User toEntity(UserCreateDto dto) {
        return User.builder()
                .name(dto.getName())
                .build();
    }
}
