package com.alephalpha.shopping_list_manager.service;

import com.alephalpha.shopping_list_manager.dto.UserCreateDto;
import com.alephalpha.shopping_list_manager.dto.UserDto;
import com.alephalpha.shopping_list_manager.entity.User;
import com.alephalpha.shopping_list_manager.mapper.UserMapper;
import com.alephalpha.shopping_list_manager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void getById_shouldReturnUserDto_whenUserExists() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        UserDto userDto = new UserDto();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getById(userId);

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userRepository).findById(userId);
        verify(userMapper).toDto(user);
    }

    @Test
    void getById_shouldThrow_whenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.getById(userId));
        assertEquals("User not found", exception.getReason());
        verify(userRepository).findById(userId);
    }

    @Test
    void getAll_shouldReturnAllUserDtos() {
        List<User> users = List.of(new User(), new User());
        List<UserDto> userDtos = List.of(new UserDto(), new UserDto());

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(users.get(0))).thenReturn(userDtos.get(0));
        when(userMapper.toDto(users.get(1))).thenReturn(userDtos.get(1));

        List<UserDto> result = userService.getAll();

        assertEquals(userDtos.size(), result.size());
        assertEquals(userDtos, result);
        verify(userRepository).findAll();
        verify(userMapper, times(2)).toDto(any(User.class));
    }

    @Test
    void create_shouldSaveUserAndReturnDto() {
        UserCreateDto createDto = new UserCreateDto();
        User user = new User();
        User savedUser = new User();
        UserDto userDto = new UserDto();

        when(userMapper.toEntity(createDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userDto);

        UserDto result = userService.create(createDto);

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userMapper).toEntity(createDto);
        verify(userRepository).save(user);
        verify(userMapper).toDto(savedUser);
    }
}
