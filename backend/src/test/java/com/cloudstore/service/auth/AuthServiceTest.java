package com.cloudstore.service.auth;

import com.cloudstore.model.dto.UserDTO;
import com.cloudstore.model.entity.User;
import com.cloudstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();  // Add necessary user fields
        user.setUsername("testuser");
        user.setPassword("test1234");
        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(user));
    }

    @Test
    void testRegister() {
        UserDTO userDTO = UserDTO.forRegistration("testuser", "test@example.com", "test1234");
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO response = authService.register(userDTO);

        assertEquals("testuser", response.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticateInvalidCredentials() {
        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.empty());

        assertThrows(BadCredentialsException.class, () -> {
            authService.authenticate("wronguser", "wrongpassword");
        });
    }
}
