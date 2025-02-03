package com.cloudstore.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "file_chunks")
public class FileChunk {
    // Static logger for error handling and logging
    private static final Logger logger = LoggerFactory.getLogger(FileChunk.class);

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Chunk number is required")
    @Min(value = 0, message = "Chunk number must be non-negative")
    @Column(nullable = false)
    private Integer chunkNumber;

    @NotNull(message = "Chunk size is required")
    @Min(value = 0, message = "Chunk size must be non-negative")
    @Column(nullable = false)
    private Long chunkSize;

    @Column(nullable = false)
    private String chunkPath;

    @Column(nullable = false)
    private String checksumMd5;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_metadata_id", nullable = false)
    private FileMetadata fileMetadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChunkStatus status = ChunkStatus.ACTIVE;

    // Chunk status enum
    public enum ChunkStatus {
        ACTIVE, 
        CORRUPTED, 
        DELETED, 
        PENDING_SYNC
    }

    // Nested Builder class
    public static class FileChunkBuilder {
        private FileChunk fileChunk = new FileChunk();

        public FileChunkBuilder chunkNumber(Integer chunkNumber) {
            fileChunk.chunkNumber = chunkNumber;
            return this;
        }

        public FileChunkBuilder chunkSize(Long chunkSize) {
            fileChunk.chunkSize = chunkSize;
            return this;
        }

        public FileChunkBuilder chunkPath(String chunkPath) {
            fileChunk.chunkPath = chunkPath;
            return this;
        }

        public FileChunkBuilder checksumMd5(String checksumMd5) {
            fileChunk.checksumMd5 = checksumMd5;
            return this;
        }

        public FileChunkBuilder fileMetadata(FileMetadata fileMetadata) {
            fileChunk.fileMetadata = fileMetadata;
            return this;
        }

        public FileChunkBuilder container(Container container) {
            fileChunk.container = container;
            return this;
        }

        public FileChunk build() {
            return fileChunk;
        }
    }

    // Improved Validation method
    public boolean validateChunkIntegrity() {
        try {
            // Check if chunk path is valid
            if (chunkPath == null || chunkPath.isEmpty()) {
                logger.warn("Chunk path is null or empty");
                return false;
            }

            // Limit file size to prevent potential out-of-memory errors (e.g., 1GB)
            long maxFileSize = 1_073_741_824L; // 1 GB
            if (Files.size(Paths.get(chunkPath)) > maxFileSize) {
                logger.warn("Chunk size exceeds maximum allowed size");
                return false;
            }

            // Read the chunk file from the chunk path
            byte[] fileBytes = Files.readAllBytes(Paths.get(chunkPath));
            
            // Calculate MD5 checksum
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] calculatedChecksum = md.digest(fileBytes);
            
            // Convert calculated checksum to hexadecimal string
            StringBuilder calculatedChecksumHex = new StringBuilder();
            for (byte b : calculatedChecksum) {
                calculatedChecksumHex.append(String.format("%02x", b));
            }
            
            // Compare calculated checksum with stored checksum
            boolean isIntact = calculatedChecksumHex.toString().equals(this.checksumMd5);
            
            // Update status based on integrity check
            if (!isIntact) {
                this.status = ChunkStatus.CORRUPTED;
                logger.warn("Chunk integrity check failed for chunk: {}", id);
            }
            
            return isIntact;
        } catch (IOException e) {
            logger.error("Error reading chunk file: {}", e.getMessage(), e);
            this.status = ChunkStatus.CORRUPTED;
            return false;
        } catch (java.security.NoSuchAlgorithmException e) {
            // This should never happen as MD5 is a standard algorithm
            logger.error("MD5 algorithm not found: {}", e.getMessage(), e);
            this.status = ChunkStatus.CORRUPTED;
            return false;
        }
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Integer getChunkNumber() { return chunkNumber; }
    public void setChunkNumber(Integer chunkNumber) { this.chunkNumber = chunkNumber; }

    public Long getChunkSize() { return chunkSize; }
    public void setChunkSize(Long chunkSize) { this.chunkSize = chunkSize; }

    public String getChunkPath() { return chunkPath; }
    public void setChunkPath(String chunkPath) { this.chunkPath = chunkPath; }

    public String getChecksumMd5() { return checksumMd5; }
    public void setChecksumMd5(String checksumMd5) { this.checksumMd5 = checksumMd5; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public FileMetadata getFileMetadata() { return fileMetadata; }
    public void setFileMetadata(FileMetadata fileMetadata) { this.fileMetadata = fileMetadata; }

    public Container getContainer() { return container; }
    public void setContainer(Container container) { this.container = container; }

    public ChunkStatus getStatus() { return status; }
    public void setStatus(ChunkStatus status) { this.status = status; }
}