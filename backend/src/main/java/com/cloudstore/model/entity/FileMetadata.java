package com.cloudstore.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Filename is required")
    @Column(nullable = false)
    private String filename;

    @NotNull(message = "File size is required")
    @Min(value = 0, message = "File size must be non-negative")
    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String contentType;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime uploadedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "fileMetadata", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FileChunk> chunks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileStatus status = FileStatus.ACTIVE;

    // File status enum
    public enum FileStatus {
        ACTIVE, 
        DELETED, 
        ARCHIVED, 
        SHARED
    }

    // Nested Builder class
    public static class FileMetadataBuilder {
        private FileMetadata fileMetadata = new FileMetadata();

        public FileMetadataBuilder filename(String filename) {
            fileMetadata.filename = filename;
            return this;
        }

        public FileMetadataBuilder fileSize(Long fileSize) {
            fileMetadata.fileSize = fileSize;
            return this;
        }

        public FileMetadataBuilder contentType(String contentType) {
            fileMetadata.contentType = contentType;
            return this;
        }

        public FileMetadataBuilder owner(User owner) {
            fileMetadata.owner = owner;
            return this;
        }

        public FileMetadata build() {
            return fileMetadata;
        }
    }

    // Additional business logic methods
    public void addChunk(FileChunk chunk) {
        if (this.chunks == null) {
            this.chunks = new ArrayList<>();
        }
        this.chunks.add(chunk);
        chunk.setFileMetadata(this);
    }

    public long getTotalChunks() {
        return this.chunks != null ? this.chunks.size() : 0;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public List<FileChunk> getChunks() { return chunks; }
    public void setChunks(List<FileChunk> chunks) { this.chunks = chunks; }

    public FileStatus getStatus() { return status; }
    public void setStatus(FileStatus status) { this.status = status; }
}