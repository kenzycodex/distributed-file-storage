package com.cloudstore.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "file_chunks")
public class FileChunk {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "file_metadata_id", nullable = false)
    private FileMetadata fileMetadata;

    @Column(nullable = false)
    private int chunkNumber;

    @Column(nullable = false)
    private long chunkSize;

    @Column(nullable = false)
    private String storageContainerId;

    @Column(nullable = false)
    private String chunckHash;

    @Column(nullable = false)
    private boolean isVerified = false;
}