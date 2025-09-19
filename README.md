# FileFlowService 📁

A simple Java Spring Boot demo service for file upload and processing. Upload `.txt` or `.csv` files and get line/word counts!

## 🚀 Quick Start

### 1. Run the Application
```bash
./mvnw spring-boot:run
```

### 2. Upload a File
```bash
curl -X POST -F "file=@yourfile.txt" http://localhost:8080/api/files/upload
```

### 3. View All Records
```bash
curl http://localhost:8080/api/files/records
```

## 📋 What It Does

- ✅ **File Upload**: Accept `.txt` and `.csv` files (max 10MB)
- ✅ **Validation**: Check file type and size
- ✅ **Processing**: Count lines and words in uploaded files
- ✅ **Storage**: Save results to in-memory database
- ✅ **Logging**: Track all operations and errors

## 🔧 API Endpoints

### Upload File
- **POST** `/api/files/upload`
- **Form Data**: `file` (multipart file)

**Success Response:**
```json
{
  "success": true,
  "message": "File processed successfully",
  "fileName": "sample.txt",
  "fileSize": 1024,
  "lineCount": 15,
  "wordCount": 150,
  "recordId": "uuid-123"
}
```

### Get All Records
- **GET** `/api/files/records`

**Response:**
```json
[
  {
    "id": "uuid-123",
    "fileName": "sample.txt",
    "fileSize": 1024,
    "lineCount": 15,
    "wordCount": 150,
    "uploadedAt": "2024-01-01T10:00:00"
  }
]
```

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.5.5**
- **Maven**
- **In-Memory Storage** (ConcurrentHashMap)

## 📁 Project Structure

```
src/main/java/com/jieshenghow/fileflowservice/
├── FileFlowServiceApplication.java     # Main app
├── controller/
│   └── FileUploadController.java       # REST endpoints
├── service/
│   ├── FileValidationService.java      # File validation
│   └── FileProcessingService.java      # File processing
├── repository/
│   └── FileRecordRepository.java       # In-memory database
├── model/
│   └── FileRecord.java                 # Data model
└── dto/
    └── FileProcessingResponse.java     # API response
```

## 🧪 Testing

Run tests:
```bash
./mvnw test
```

## 🔍 Features

### File Validation
- Only `.txt` and `.csv` files allowed
- Maximum file size: 10MB
- Empty files rejected

### Processing
- Counts total lines in file
- Counts total words (split by whitespace)
- Handles empty lines gracefully

### Error Handling
- Graceful error responses
- Detailed logging for debugging
- Service never crashes

## 🏃‍♂️ Example Usage

### Test with Sample Files

Create a test file:
```bash
echo -e "Hello World\nThis is line 2\nLine 3 has more words" > sample.txt
```

Upload and process:
```bash
curl -X POST -F "file=@sample.txt" http://localhost:8080/api/files/upload
```

Expected result:
- **Lines**: 3
- **Words**: 9
- **Status**: Success

## 📝 Notes

- **Data Persistence**: All data is stored in memory and will be lost when the application restarts
- **File Storage**: Uploaded files are processed but not saved to disk
- **Concurrent Safe**: Multiple users can upload files simultaneously

## 🔧 Configuration

Edit `src/main/resources/application.properties`:
```properties
# Server port
server.port=8080

# File upload limits
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Logging
logging.level.com.jieshenghow.fileflowservice=INFO
```

