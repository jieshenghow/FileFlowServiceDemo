package com.jieshenghow.fileflowservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class FileValidationServiceTest {

    private FileValidationService fileValidationService;

    @BeforeEach
    void setUp() {
        fileValidationService = new FileValidationService();
    }

    @Test
    void testValidTxtFile() {
        MultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello World".getBytes());
        
        assertTrue(fileValidationService.isAllowedFile(file));
    }

    @Test
    void testValidCsvFile() {
        MultipartFile file = new MockMultipartFile(
                "file", "test.csv", "text/csv", "name,age\nJohn,25".getBytes());
        
        assertTrue(fileValidationService.isAllowedFile(file));
    }

    @Test
    void testInvalidFileExtension() {
        MultipartFile file = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "PDF content".getBytes());
        
        assertFalse(fileValidationService.isAllowedFile(file));
    }

    @Test
    void testEmptyFile() {
        MultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "".getBytes());
        
        assertFalse(fileValidationService.isAllowedFile(file));
    }

    @Test
    void testNullFile() {
        assertFalse(fileValidationService.isAllowedFile(null));
    }
}