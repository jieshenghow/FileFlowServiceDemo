package com.jieshenghow.fileflowservice.controller;

import com.jieshenghow.fileflowservice.dto.FileProcessingResponse;
import com.jieshenghow.fileflowservice.service.FileProcessingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileProcessingService fileProcessingService;

    public FileUploadController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileProcessingResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileProcessingResponse response = fileProcessingService.processFile(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            FileProcessingResponse errorResponse = new FileProcessingResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("Error processing file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/records")
    public ResponseEntity<?> getAllRecords() {
        try {
            return ResponseEntity.ok(fileProcessingService.getAllRecords());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving records: " + e.getMessage());
        }
    }
}