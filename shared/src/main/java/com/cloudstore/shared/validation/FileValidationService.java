package com.cloudstore.shared.validation;

import com.cloudstore.shared.exceptions.FileStorageException;
import java.util.Arrays;
import java.util.List;

public class FileValidationService {
    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024 * 1024; // 5GB
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
        "txt", "pdf", "doc", "docx", "jpg", "jpeg", "png", "gif", 
        "mp3", "mp4", "zip", "rar", "csv", "xls", "xlsx"
    );

    public void validateFile(String filename, long fileSize) {
        // Check file size
        if (fileSize > MAX_FILE_SIZE) {
            throw new FileStorageException("File size exceeds maximum limit of 5GB");
        }

        // Check file extension
        String extension = getFileExtension(filename);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new FileStorageException("File type not allowed");
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }
}