package com.cloudstore.service.auth;

import com.cloudstore.model.dto.UserDTO;
import com.cloudstore.model.entity.User;
import com.cloudstore.repository.UserRepository;
import com.cloudstore.security.event.AuthenticationFailureEvent;
import com.cloudstore.security.event.AuthenticationSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService blacklistService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            TokenBlacklistService blacklistService,
            ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.blacklistService = blacklistService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UserDTO register(UserDTO registrationRequest) {
        validateRegistrationRequest(registrationRequest);

        User user = new User.UserBuilder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(User.UserRole.USER)
                .build();

        User savedUser = userRepository.save(user);
        log.info("New user registered: {}", savedUser.getUsername());

        return generateUserResponse(savedUser);
    }

    @Transactional
    public UserDTO authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BadCredentialsException("User not found"));

            if (!user.getAccountNonLocked()) {
                throw new LockedException("Account is locked");
            }

            user.updateLastLogin();
            userRepository.save(user);

            eventPublisher.publishEvent(new AuthenticationSuccessEvent(user));
            log.info("User successfully authenticated: {}", username);

            return generateUserResponse(user);

        } catch (BadCredentialsException e) {
            handleFailedLogin(username);
            throw e;
        }
    }

    @Transactional
    public UserDTO refreshToken(String refreshToken) {
        if (blacklistService.isBlacklisted(refreshToken)) {
            throw new BadCredentialsException("Refresh token has been revoked");
        }

        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        if (jwtService.isTokenValid(refreshToken, user)) {
            log.info("Token refreshed for user: {}", username);
            return generateUserResponse(user);
        }

        throw new BadCredentialsException("Invalid refresh token");
    }

    @Transactional
    public void logout(String token) {
        blacklistService.blacklistToken(token);
        log.info("Token blacklisted during logout");
    }

    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            eventPublisher.publishEvent(new AuthenticationFailureEvent(username));
            throw new BadCredentialsException("Invalid old password");
        }

        validateNewPassword(newPassword);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordChangeDate(LocalDateTime.now());
        userRepository.save(user);

        log.info("Password changed for user: {}", username);
    }

    private UserDTO generateUserResponse(User user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return UserDTO.withTokens(user, accessToken, refreshToken);
    }

    private void handleFailedLogin(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.incrementFailedAttempts();
            if (user.getFailedAttempts() >= 5) {
                user.setAccountNonLocked(false);
                user.setLockTime(LocalDateTime.now());
                log.warn("Account locked for user: {}", username);
            }
            userRepository.save(user);
            eventPublisher.publishEvent(new AuthenticationFailureEvent(username));
        });
        log.warn("Failed login attempt for username: {}", username);
    }

    private void validateRegistrationRequest(UserDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadCredentialsException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadCredentialsException("Email already exists");
        }
        validateNewPassword(request.getPassword());
    }

    private void validateNewPassword(String password) {
        if (password.length() < 8) {
            throw new BadCredentialsException("Password must be at least 8 characters long");
        }
        // Add more password validation rules as needed
    }
}
