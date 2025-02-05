package com.cloudstore.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false)
    private Boolean accountNonLocked = true;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column
    private LocalDateTime lastLoginTimestamp;

    @Column
    private Double profileCompletionPercentage = 0.0;

    @Column
    private Integer failedAttempts = 0;

    @Column
    private LocalDateTime lockTime;

    @Column
    private LocalDateTime passwordChangeDate;  // Add this field

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FileMetadata> files;

    // Enum for User Roles
    public enum UserRole {
        USER, ADMIN, MODERATOR
    }

    // UserDetails interface methods
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Constructors
    public User() {}

    // Builder pattern for user creation
    public static class UserBuilder {
        private User user = new User();

        public UserBuilder username(String username) {
            user.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            user.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            user.password = password;
            return this;
        }

        public UserBuilder role(UserRole role) {
            user.role = role;
            return this;
        }

        public User build() {
            return user;
        }
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(LocalDateTime lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    public Double getProfileCompletionPercentage() {
        return profileCompletionPercentage;
    }

    public void setProfileCompletionPercentage(Double profileCompletionPercentage) {
        this.profileCompletionPercentage = profileCompletionPercentage;
    }

    public List<FileMetadata> getFiles() {
        return files;
    }

    public void setFiles(List<FileMetadata> files) {
        this.files = files;
    }

    // New methods to handle failed login attempts, lock time, and password change date
    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    public LocalDateTime getPasswordChangeDate() {  // Add getter
        return passwordChangeDate;
    }

    public void setPasswordChangeDate(LocalDateTime passwordChangeDate) {  // Add setter
        this.passwordChangeDate = passwordChangeDate;
    }

    public void softDelete() {
        this.isDeleted = true;
        this.enabled = false;
    }

    public void restore() {
        this.isDeleted = false;
        this.enabled = true;
    }

    public void updateLastLogin() {
        this.lastLoginTimestamp = LocalDateTime.now();
        calculateProfileCompletion();
    }

    // Helper class for profile field evaluation
    private class ProfileField {
        private final Object value;
        private final double weight;
        private final boolean required;

        ProfileField(Object value, double weight, boolean required) {
            this.value = value;
            this.weight = weight;
            this.required = required;
        }

        boolean isComplete() {
            if (value == null) {
                return !required;
            }

            if (value instanceof String) {
                return StringUtils.hasText((String) value);
            }

            if (value instanceof Boolean) {
                return (Boolean) value;
            }

            if (value instanceof Collection) {
                return !((Collection<?>) value).isEmpty();
            }

            return true;
        }
    }

    private void calculateProfileCompletion() {
        double totalWeight = 0.0;
        double completedWeight = 0.0;

        // Define fields and their weights
        var profileFields = new ProfileField[] {
            // Essential fields (40% of total)
            new ProfileField(this.username, 15.0, true),
            new ProfileField(this.email, 15.0, true),
            new ProfileField(this.password, 10.0, true),

            // Account status fields (20% of total)
            new ProfileField(this.role, 10.0, true),
            new ProfileField(this.enabled, 5.0, true),
            new ProfileField(this.accountNonLocked, 5.0, true),

            // Activity indicators (25% of total)
            new ProfileField(this.lastLoginTimestamp, 10.0, false),
            new ProfileField(this.createdAt, 5.0, false),
            new ProfileField(this.updatedAt, 5.0, false),
            new ProfileField(!Boolean.TRUE.equals(this.isDeleted), 5.0, true),

            // Additional profile data (15% of total)
            new ProfileField(!this.files.isEmpty(), 15.0, false)
        };

        // Calculate completion percentage
        for (ProfileField field : profileFields) {
            totalWeight += field.weight;
            if (field.isComplete()) {
                completedWeight += field.weight;
            }
        }

        // Update profile completion percentage
        this.profileCompletionPercentage = (completedWeight / totalWeight) * 100.0;
        
        // Round to 2 decimal places
        this.profileCompletionPercentage = Math.round(this.profileCompletionPercentage * 100.0) / 100.0;
    }
}
