package com.cloudstore.controller;

import com.cloudstore.model.dto.UserDTO;
import com.cloudstore.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService)).build();
    }

    @Test
    void testRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO();  // Set your mock response

        when(authService.register(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType("application/json")
                .content("{\"username\":\"testuser\", \"email\":\"test@example.com\", \"password\":\"test1234\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(authService, times(1)).register(any(UserDTO.class));
    }

    @Test
    void testLogin() throws Exception {
        UserDTO userDTO = new UserDTO();  // Set your mock response

        when(authService.authenticate(anyString(), anyString())).thenReturn(userDTO);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType("application/json")
                .content("{\"username\":\"testuser\", \"password\":\"test1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(authService, times(1)).authenticate(anyString(), anyString());
    }

    @Test
    void testChangePassword() throws Exception {
        doNothing().when(authService).changePassword(anyString(), anyString(), anyString());

        mockMvc.perform(post("/api/v1/auth/change-password")
                .header("Authorization", "Bearer mock-token")
                .contentType("application/json")
                .content("{\"username\":\"testuser\", \"oldPassword\":\"test1234\", \"newPassword\":\"newPass1234\"}"))
                .andExpect(status().isOk());

        verify(authService, times(1)).changePassword(anyString(), anyString(), anyString());
    }

    @Test
    void testLogout() throws Exception {
        doNothing().when(authService).logout(anyString());

        mockMvc.perform(post("/api/v1/auth/logout")
                .header("Authorization", "Bearer mock-token"))
                .andExpect(status().isOk());

        verify(authService, times(1)).logout(anyString());
    }
}
