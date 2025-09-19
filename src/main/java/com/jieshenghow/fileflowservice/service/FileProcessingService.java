package com.jieshenghow.fileflowservice.service;

import com.jieshenghow.fileflowservice.dto.FileProcessingResponse;
import com.jieshenghow.fileflowservice.model.FileRecord;
import com.jieshenghow.fileflowservice.repository.FileRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class FileProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(FileProcessingService.class);

    private final FileValidationService fileValidationService;
    private final FileRecordRepository fileRecordRepository;

    public FileProcessingService(FileValidationService fileValidationService, 
                                FileRecordRepository fileRecordRepository) {
        this.fileValidationService = fileValidationService;
        this.fileRecordRepository = fileRecordRepository;
    }

    public FileProcessingResponse processFile(MultipartFile file) {
        logger.info("Starting file processing for file: {}", file.getOriginalFilename());

        try {
            // Validate the file first
            fileValidationService.validateFile(file);

            // Process the file to count lines and words
            FileProcessingResult result = countLinesAndWords(file);

            // Save to in-memory database
            FileRecord record = saveToDatabase(file, result);

            // Create response
            FileProcessingResponse response = new FileProcessingResponse();
            response.setSuccess(true);
            response.setMessage("File processed successfully");
            response.setFileName(file.getOriginalFilename());
            response.setFileSize(file.getSize());
            response.setLineCount(result.getLineCount());
            response.setWordCount(result.getWordCount());
            response.setRecordId(record.getId());

            logger.info("File processing completed successfully for file: {} (Lines: {}, Words: {})", 
                       file.getOriginalFilename(), result.getLineCount(), result.getWordCount());

            return response;

        } catch (IllegalArgumentException e) {
            logger.error("File validation failed: {}", e.getMessage());
            return new FileProcessingResponse(false, e.getMessage());
        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage());
            return new FileProcessingResponse(false, "Error reading file content");
        } catch (Exception e) {
            logger.error("Unexpected error during file processing: {}", e.getMessage(), e);
            return new FileProcessingResponse(false, "An unexpected error occurred while processing the file");
        }
    }

    private FileProcessingResult countLinesAndWords(MultipartFile file) throws IOException {
        int lineCount = 0;
        int wordCount = 0;

        logger.debug("Starting line and word count for file: {}", file.getOriginalFilename());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                // Count words in this line (split by whitespace and filter empty strings)
                String[] words = line.trim().split("\\s+");
                if (!line.trim().isEmpty()) {
                    wordCount += words.length;
                }
            }
        }

        logger.debug("Completed counting for file: {} - Lines: {}, Words: {}", 
                    file.getOriginalFilename(), lineCount, wordCount);

        return new FileProcessingResult(lineCount, wordCount);
    }

    private FileRecord saveToDatabase(MultipartFile file, FileProcessingResult result) {
        logger.debug("Saving file record to database for file: {}", file.getOriginalFilename());

        FileRecord record = new FileRecord(
                null, // ID will be generated
                file.getOriginalFilename(),
                file.getSize(),
                result.getLineCount(),
                result.getWordCount()
        );

        FileRecord savedRecord = fileRecordRepository.save(record);
        logger.info("File record saved with ID: {}", savedRecord.getId());
        return savedRecord;
    }

    public List<FileRecord> getAllRecords() {
        logger.debug("Retrieving all file records from database");
        List<FileRecord> records = fileRecordRepository.findAll();
        logger.info("Retrieved {} file records", records.size());
        return records;
    }

    // Inner class to hold processing results
    private static class FileProcessingResult {
        private final int lineCount;
        private final int wordCount;

        public FileProcessingResult(int lineCount, int wordCount) {
            this.lineCount = lineCount;
            this.wordCount = wordCount;
        }

        public int getLineCount() {
            return lineCount;
        }

        public int getWordCount() {
            return wordCount;
        }
    }
}