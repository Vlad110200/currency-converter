package com.project.currency_converter.service;

import com.project.currency_converter.dto.UserRequestDto;
import com.project.currency_converter.dto.UserResponseDto;
import com.project.currency_converter.entity.User;
import com.project.currency_converter.exception.UserNotFoundException;
import com.project.currency_converter.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRequestDto requestDto;

    @BeforeEach
    void setUp () {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("old username");
        testUser.setApiKey("some uuid apiKey");
        testUser.setHistory(new ArrayList<>());

        requestDto = new UserRequestDto("new username");
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists () {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        UserResponseDto result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("old username", result.username());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound () {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void create_shouldReturnSavedUser () {
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto result = userService.create(requestDto);

        assertNotNull(result);
        assertEquals("new username", result.username());
        assertNotNull(result.apiKey());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void update_shouldUpdateUsername_whenUserExists () {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserResponseDto result = userService.update(1L, requestDto);

        assertNotNull(result);
        assertEquals("new username", result.username());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void delete_shouldDeleteUser_whenUserExists () {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        userService.delete(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(testUser);
    }
}
