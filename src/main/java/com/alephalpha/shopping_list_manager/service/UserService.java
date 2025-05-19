package com.alephalpha.shopping_list_manager.service;

import com.alephalpha.shopping_list_manager.dto.UserCreateDto;
import com.alephalpha.shopping_list_manager.dto.UserDto;
import com.alephalpha.shopping_list_manager.entity.User;
import com.alephalpha.shopping_list_manager.mapper.UserMapper;
import com.alephalpha.shopping_list_manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toDto(user);
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto create(UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

}
