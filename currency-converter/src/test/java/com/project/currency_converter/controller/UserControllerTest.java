package com.project.currency_converter.controller;

import com.project.currency_converter.dto.UserRequestDto;
import com.project.currency_converter.dto.UserResponseDto;
import com.project.currency_converter.exception.CustomAccessDeniedHandler;
import com.project.currency_converter.exception.CustomAuthenticationEntryPoint;
import com.project.currency_converter.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @MockitoBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @MockitoBean
    private com.project.currency_converter.repository.UserRepository userRepository;

    private UserResponseDto responseDto;
    private UserRequestDto requestDto;

    @BeforeEach
    void setUp() {
        responseDto = new UserResponseDto(1L, "testUsername", "testApiKey", new ArrayList<>());
        requestDto = new UserRequestDto("testUserName");
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].username").value("testUsername"));
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("testUsername"));
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        Mockito.when(userService.create(any(UserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        Mockito.when(userService.update(eq(1L), any(UserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUsername"));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNoContent());

        Mockito.verify(userService, Mockito.times(1)).delete(1L);
    }
}
