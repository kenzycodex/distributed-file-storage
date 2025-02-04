package com.cloudstore.repository;

import com.cloudstore.model.entity.FileMetadata;
import com.cloudstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileMetadata, UUID> {
    
    // Find files by owner
    List<FileMetadata> findByOwner(User owner);
    
    // Find files by filename
    List<FileMetadata> findByFilename(String filename);
    
    // Find files uploaded after a specific date
    List<FileMetadata> findByUploadedAtAfter(LocalDateTime date);
    
    // Find files by content type
    List<FileMetadata> findByContentType(String contentType);
    
    // Find files by status
    List<FileMetadata> findByStatus(FileMetadata.FileStatus status);
    
    // Count files by owner
    long countByOwner(User owner);
    
    // Custom query to find files larger than a specific size
    @Query("SELECT f FROM FileMetadata f WHERE f.fileSize > :size")
    List<FileMetadata> findFilesLargerThan(@Param("size") Long size);
    
    // Find files uploaded by a specific user within a date range
    @Query("SELECT f FROM FileMetadata f WHERE f.owner = :user AND f.uploadedAt BETWEEN :startDate AND :endDate")
    List<FileMetadata> findFilesByOwnerAndDateRange(
        @Param("user") User user, 
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );
    
    // Find top N largest files
    @Query("SELECT f FROM FileMetadata f ORDER BY f.fileSize DESC")
    List<FileMetadata> findLargestFiles(org.springframework.data.domain.Pageable pageable);
}