package com.cloudstore.repository;

import com.cloudstore.model.entity.AuditLog;
import com.cloudstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    
    // Find audit logs by user
    List<AuditLog> findByUser(User user);
    
    // Find audit logs by action type
    List<AuditLog> findByActionType(AuditLog.ActionType actionType);
    
    // Find audit logs within a specific date range
    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    
    // Find security-related audit logs
    @Query("SELECT al FROM AuditLog al WHERE al.actionType IN " +
           "('ACCESS_DENIED', 'SUSPICIOUS_ACTIVITY', 'ACCOUNT_LOCKED')")
    List<AuditLog> findSecurityRelatedLogs();
    
    // Count logs by action type
    long countByActionType(AuditLog.ActionType actionType);
    
    // Find recent suspicious activities
    @Query("SELECT al FROM AuditLog al WHERE " +
           "al.actionType IN ('ACCESS_DENIED', 'SUSPICIOUS_ACTIVITY') " +
           "AND al.timestamp > :thresholdDate")
    List<AuditLog> findRecentSuspiciousActivities(
            @Param("thresholdDate") LocalDateTime thresholdDate
    );
    
    // Find logs by IP address
    List<AuditLog> findByIpAddress(String ipAddress);
    
    // Find logs with specific user actions
    @Query("SELECT al FROM AuditLog al WHERE al.user = :user AND " +
           "al.actionType IN ('USER_LOGIN', 'USER_LOGOUT', 'USER_REGISTRATION')")
    List<AuditLog> findUserAuthenticationLogs(@Param("user") User user);
}