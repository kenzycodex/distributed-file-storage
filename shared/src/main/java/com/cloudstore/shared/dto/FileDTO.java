package com.cloudstore.shared.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO implements Serializable {
    private String fileId;
    private String fileName;
    private long fileSize;
    private String ownerUsername;
    private LocalDateTime uploadedAt;
    private boolean isShared;
}