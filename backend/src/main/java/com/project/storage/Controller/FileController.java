package com.project.storage.Controller;

import com.project.storage.dto.ResponseUrl;
import com.project.storage.service.StorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final StorageService fileStorageService;

    public FileController(StorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    @PostMapping("/upload")
    public ResponseEntity<ResponseUrl> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.uploadFile(file);
        ResponseUrl responseUrl = ResponseUrl.builder().fileUrl(fileName).build();
        return ResponseEntity.ok(responseUrl);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<InputStreamResource> viewFile(@PathVariable String fileName) {
        InputStream stream = fileStorageService.get_File(fileName);
        String contentType = fileStorageService.getContentType(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(new InputStreamResource(stream));
    }

}
