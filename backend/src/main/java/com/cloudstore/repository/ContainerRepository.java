package com.cloudstore.repository;

import com.cloudstore.model.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContainerRepository extends JpaRepository<Container, UUID> {
    
    // Find container by name
    Optional<Container> findByName(String name);
    
    // Find containers by status
    List<Container> findByStatus(Container.ContainerStatus status);
    
    // Find containers with usage percentage above a threshold
    @Query("SELECT c FROM Container c WHERE (c.currentUsedCapacity * 100.0 / c.totalCapacity) > :threshold")
    List<Container> findContainersAboveUsageThreshold(@Param("threshold") double usageThreshold);
    
    // Find available containers for chunk storage
    @Query("SELECT c FROM Container c WHERE " +
           "(c.currentUsedCapacity + :chunkSize) <= c.totalCapacity " +
           "AND c.status = 'ACTIVE' " +
           "ORDER BY (c.currentUsedCapacity * 100.0 / c.totalCapacity)")
    List<Container> findAvailableContainersForChunk(@Param("chunkSize") Long chunkSize);
    
    // Count containers by status
    long countByStatus(Container.ContainerStatus status);
    
    // Find containers with low available capacity
    @Query("SELECT c FROM Container c WHERE " +
           "(c.totalCapacity - c.currentUsedCapacity) < :criticalThreshold")
    List<Container> findLowCapacityContainers(@Param("criticalThreshold") Long criticalThreshold);
    
    // Find containers by hostname
    List<Container> findByHostname(String hostname);
    
    // Custom method to get total system storage capacity
    @Query("SELECT SUM(c.totalCapacity) FROM Container c")
    Long getTotalSystemCapacity();
    
    // Custom method to get total used system storage
    @Query("SELECT SUM(c.currentUsedCapacity) FROM Container c")
    Long getTotalUsedSystemCapacity();
}