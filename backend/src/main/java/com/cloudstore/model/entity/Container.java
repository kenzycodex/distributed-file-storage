package com.cloudstore.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "storage_containers")
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Container name is required")
    @Size(min = 3, max = 100, message = "Container name must be between 3 and 100 characters")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "Total capacity is required")
    @Min(value = 0, message = "Capacity must be non-negative")
    @Column(nullable = false)
    private Long totalCapacity;

    @Column(nullable = false)
    private Long currentUsedCapacity = 0L;

    @Column(nullable = false)
    private String hostname;

    @Column(nullable = false)
    private Integer port;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContainerStatus status = ContainerStatus.ACTIVE;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FileChunk> storedChunks;

    // Container status enum
    public enum ContainerStatus {
        ACTIVE,       // Container is operational
        MAINTENANCE,  // Container is undergoing maintenance
        OVERLOADED,   // Container has reached critical capacity
        OFFLINE,      // Container is not responding
        SYNC_REQUIRED // Container needs synchronization
    }

    // Nested Builder class
    public static class ContainerBuilder {
        private Container container = new Container();

        public ContainerBuilder name(String name) {
            container.name = name;
            return this;
        }

        public ContainerBuilder totalCapacity(Long totalCapacity) {
            container.totalCapacity = totalCapacity;
            return this;
        }

        public ContainerBuilder hostname(String hostname) {
            container.hostname = hostname;
            return this;
        }

        public ContainerBuilder port(Integer port) {
            container.port = port;
            return this;
        }

        public Container build() {
            return container;
        }
    }

    // Business logic methods
    public boolean canStoreChunk(Long chunkSize) {
        return (currentUsedCapacity + chunkSize) <= totalCapacity;
    }

    public void addChunk(FileChunk chunk) {
        if (canStoreChunk(chunk.getChunkSize())) {
            if (storedChunks == null) {
                storedChunks = new ArrayList<>();
            }
            storedChunks.add(chunk);
            currentUsedCapacity += chunk.getChunkSize();
        } else {
            throw new IllegalStateException("Container capacity exceeded");
        }
    }

    public double getUsagePercentage() {
        return (double) currentUsedCapacity / totalCapacity * 100;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(Long totalCapacity) { this.totalCapacity = totalCapacity; }

    public Long getCurrentUsedCapacity() { return currentUsedCapacity; }
    public void setCurrentUsedCapacity(Long currentUsedCapacity) { 
        this.currentUsedCapacity = currentUsedCapacity; 
    }

    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }

    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }

    public ContainerStatus getStatus() { return status; }
    public void setStatus(ContainerStatus status) { this.status = status; }

    public List<FileChunk> getStoredChunks() { return storedChunks; }
    public void setStoredChunks(List<FileChunk> storedChunks) { 
        this.storedChunks = storedChunks; 
    }
}