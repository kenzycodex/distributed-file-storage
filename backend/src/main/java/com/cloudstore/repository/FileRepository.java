package com.cloudstore.repository;

import com.cloudstore.model.entity.FileMetadata;
import com.cloudstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileMetadata, UUID> {
    List<FileMetadata> findByOwner(User owner);
    List<FileMetadata> findByIsPublicTrue();
    long countByOwner(User owner);
}