package com.jieshenghow.fileflowservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class FileValidationService {

    private static final Logger logger = LoggerFactory.getLogger(FileValidationService.class);
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("txt", "csv");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public boolean isAllowedFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            logger.warn("File validation failed: File is null or empty");
            return false;
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            logger.warn("File validation failed: File size {} exceeds maximum allowed size {}", 
                       file.getSize(), MAX_FILE_SIZE);
            return false;
        }

        // Check file extension
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.trim().isEmpty()) {
            logger.warn("File validation failed: No filename provided");
            return false;
        }

        String fileExtension = getFileExtension(fileName);
        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            logger.warn("File validation failed: Extension '{}' not allowed. Allowed extensions: {}", 
                       fileExtension, ALLOWED_EXTENSIONS);
            return false;
        }

        logger.info("File validation passed for file: {}", fileName);
        return true;
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }

    public void validateFile(MultipartFile file) throws IllegalArgumentException {
        if (!isAllowedFile(file)) {
            throw new IllegalArgumentException("Invalid file. Only .txt and .csv files up to 10MB are allowed.");
        }
    }
}