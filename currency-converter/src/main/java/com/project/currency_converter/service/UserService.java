package com.project.currency_converter.service;

import com.project.currency_converter.dto.UserMapper;
import com.project.currency_converter.dto.UserRequestDto;
import com.project.currency_converter.dto.UserResponseDto;
import com.project.currency_converter.entity.User;
import com.project.currency_converter.exception.UserNotFoundException;
import com.project.currency_converter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> getAllUsers() {
        log.info("Get all users");
        return userRepository.findAll().stream()
                .map(UserMapper::toDto).toList();
    }

    public UserResponseDto create (UserRequestDto requestDto) {
        User user = new User();
        String generatedApiKey = UUID.randomUUID().toString();
        user.setUsername(requestDto.username());
        user.setApiKey(generatedApiKey);

        User savedUser = userRepository.save(user);
        log.info("Create user with username:{}",requestDto.username());
        return UserMapper.toDto(savedUser);
    }

    public UserResponseDto update (Long id, UserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setUsername(request.username());
        userRepository.save(user);

        log.info("Update user with id:{}",id);
        return UserMapper.toDto(user);
    }

    public void delete (Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
        log.info("Delete user with id:{}",id);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        log.info("Get user with id:{}",id);
        return UserMapper.toDto(user);
    }
}
