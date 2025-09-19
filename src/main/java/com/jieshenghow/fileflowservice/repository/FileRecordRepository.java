package com.jieshenghow.fileflowservice.repository;

import com.jieshenghow.fileflowservice.model.FileRecord;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileRecordRepository {

    private final Map<String, FileRecord> records = new ConcurrentHashMap<>();

    public FileRecord save(FileRecord record) {
        if (record.getId() == null) {
            record.setId(UUID.randomUUID().toString());
        }
        records.put(record.getId(), record);
        return record;
    }

    public Optional<FileRecord> findById(String id) {
        return Optional.ofNullable(records.get(id));
    }

    public List<FileRecord> findAll() {
        return new ArrayList<>(records.values());
    }

    public void deleteById(String id) {
        records.remove(id);
    }

    public long count() {
        return records.size();
    }
}