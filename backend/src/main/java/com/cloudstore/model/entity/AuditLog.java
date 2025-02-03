package com.cloudstore.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Action type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType actionType;

    @Size(max = 255, message = "Description must be less than 255 characters")
    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private String userAgent;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime timestamp;

    // Enum for different types of actions
    public enum ActionType {
        // User-related actions
        USER_LOGIN,
        USER_LOGOUT,
        USER_REGISTRATION,
        USER_PROFILE_UPDATE,
        USER_PASSWORD_CHANGE,
        ACCOUNT_LOCKED,

        // File-related actions
        FILE_UPLOAD,
        FILE_DOWNLOAD,
        FILE_SHARE,
        FILE_DELETE,
        FILE_UPDATE,

        // System actions
        CONTAINER_STATUS_CHANGE,
        CONTAINER_SYNC,
        SYSTEM_MAINTENANCE,

        // Security-related actions
        ACCESS_DENIED,
        SUSPICIOUS_ACTIVITY
    }

    // Nested Builder class
    public static class AuditLogBuilder {
        private AuditLog auditLog = new AuditLog();

        public AuditLogBuilder user(User user) {
            auditLog.user = user;
            return this;
        }

        public AuditLogBuilder actionType(ActionType actionType) {
            auditLog.actionType = actionType;
            return this;
        }

        public AuditLogBuilder description(String description) {
            auditLog.description = description;
            return this;
        }

        public AuditLogBuilder ipAddress(String ipAddress) {
            auditLog.ipAddress = ipAddress;
            return this;
        }

        public AuditLogBuilder userAgent(String userAgent) {
            auditLog.userAgent = userAgent;
            return this;
        }

        public AuditLog build() {
            return auditLog;
        }
    }

    // Additional utility methods
    public boolean isSecurityRelatedAction() {
        return actionType.name().startsWith("SECURITY_") 
            || actionType == ActionType.ACCESS_DENIED 
            || actionType == ActionType.SUSPICIOUS_ACTIVITY;
    }

    public boolean isUserAction() {
        return actionType.name().startsWith("USER_");
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public ActionType getActionType() { return actionType; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public LocalDateTime getTimestamp() { return timestamp; }
}