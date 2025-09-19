package com.jieshenghow.fileflowservice.model;

import java.time.LocalDateTime;

public class FileRecord {
    private String id;
    private String fileName;
    private long fileSize;
    private int lineCount;
    private int wordCount;
    private LocalDateTime uploadedAt;

    public FileRecord() {}

    public FileRecord(String id, String fileName, long fileSize, int lineCount, int wordCount) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.lineCount = lineCount;
        this.wordCount = wordCount;
        this.uploadedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}