package com.cloudstore.shared.constants;

public enum FileOperationStatus {
    UPLOAD_INITIATED,
    UPLOAD_IN_PROGRESS,
    UPLOAD_COMPLETE,
    DOWNLOAD_INITIATED,
    DOWNLOAD_IN_PROGRESS,
    DOWNLOAD_COMPLETE,
    FAILED,
    CHUNKING_IN_PROGRESS,
    CHUNK_TRANSFERRED
}