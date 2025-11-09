package com.project.storage.service;

import com.project.storage.entity.Media;
import com.project.storage.entity.MediaRepository;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class StorageService {

    private MinioClient minioClient;
    private final MediaRepository MediaRepository;
    @Value("${minio.bucket.name}")
    private String bucketName = System.getenv("MINIO_BUCKET_NAME");
    @Autowired
    public StorageService(MinioClient minioClient, MediaRepository MediaRepository) {
        this.minioClient = minioClient;
        this.MediaRepository = MediaRepository;
        try {
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!found) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucketName).build()
                );
                System.out.println("✅ Bucket created: " + bucketName);
            } else {
                System.out.println("✅ Bucket already exists: " + bucketName);
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Error checking/creating bucket: " + bucketName, e);
        }
    }
    public String uploadFile( MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        System.out.println(fileName);
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            String fileUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(fileName)
                    .toUriString();

            Media media = Media.builder().urlFile(fileUrl).build();
            MediaRepository.save(media);
            return fileUrl;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to MinIO", e);
        }


    }
    @SneakyThrows
    public InputStream  get_File(String fileName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file from MinIO", e);
        }
    }
//    public String getFileUrl(String fileName) {
//        try {
//            return minioClient.getPresignedObjectUrl(
//                    GetPresignedObjectUrlArgs.builder()
//                            .method(Method.GET)
//                            .bucket(bucketName)
//                            .object(fileName)
//                            .expiry(60 * 60) // expires in 1 hour
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("Error generating file URL", e);
//        }
//    }

    public String getContentType(String fileName) {
        try {
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
            return stat.contentType(); // returns "image/png", "application/pdf", etc.
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }

}
