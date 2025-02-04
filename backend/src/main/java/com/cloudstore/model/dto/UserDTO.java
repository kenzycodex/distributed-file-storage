package com.cloudstore.model.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;
import com.cloudstore.model.entity.User.UserRole;
import com.cloudstore.model.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private UUID id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;  // Only used for registration/password update

    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginTimestamp;
    private Double profileCompletionPercentage;
    private boolean enabled;
    private boolean accountNonLocked;

    // DTOs for API responses
    private String accessToken;
    private String refreshToken;

    // Static factory methods for different use cases
    public static UserDTO forRegistration(String username, String email, String password) {
        UserDTO dto = new UserDTO();
        dto.setUsername(username);
        dto.setEmail(email);
        dto.setPassword(password);
        return dto;
    }

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLoginTimestamp(user.getLastLoginTimestamp());
        dto.setProfileCompletionPercentage(user.getProfileCompletionPercentage());
        dto.setEnabled(user.isEnabled());
        dto.setAccountNonLocked(user.isAccountNonLocked());
        return dto;
    }

    // Token response factory method
    public static UserDTO withTokens(User user, String accessToken, String refreshToken) {
        UserDTO dto = fromEntity(user);
        dto.setAccessToken(accessToken);
        dto.setRefreshToken(refreshToken);
        return dto;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastLoginTimestamp() { return lastLoginTimestamp; }
    public void setLastLoginTimestamp(LocalDateTime lastLoginTimestamp) { 
        this.lastLoginTimestamp = lastLoginTimestamp; 
    }

    public Double getProfileCompletionPercentage() { return profileCompletionPercentage; }
    public void setProfileCompletionPercentage(Double profileCompletionPercentage) { 
        this.profileCompletionPercentage = profileCompletionPercentage; 
    }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public boolean isAccountNonLocked() { return accountNonLocked; }
    public void setAccountNonLocked(boolean accountNonLocked) { 
        this.accountNonLocked = accountNonLocked; 
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}