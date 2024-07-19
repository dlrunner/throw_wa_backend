package com.project.throw_wa.s3.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.project.throw_wa.s3.dto.S3CreateDto;
import com.project.throw_wa.s3.dto.S3DownloadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class FileDownloadController {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("file: {}", file);

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
        }

        String key = UUID.randomUUID().toString();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        log.info("objectMetadata: {}", objectMetadata);

        try {
            // 업로드
            amazonS3Client.putObject(bucket, key, file.getInputStream(), objectMetadata);
            // URL 조회
            String url = amazonS3Client.getUrl(bucket, key).toString();
            log.info("File uploaded successfully to S3. URL: {}", url);

            return ResponseEntity.ok(new S3CreateDto(file.getOriginalFilename(), key, url));
        } catch (IOException e) {
            log.error("Failed to upload file to S3: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("파일 업로드에 실패했습니다.");
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다.");
        }
    }

    @PostMapping("/download")
    public ResponseEntity<?> download(@RequestBody S3DownloadDto s3DownloadDto) throws Exception {

        log.info("s3DownloadDto: {}", s3DownloadDto);

        // S3에서 파일 다운로드
        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucket, s3DownloadDto.getKey()));
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        // 임시 파일 생성
        File tempFile = File.createTempFile("temp", null);
        FileOutputStream fos = new FileOutputStream(tempFile);
        byte[] readBuf = new byte[1024];
        int readLen = 0;
        while ((readLen = inputStream.read(readBuf)) > 0) {
            fos.write(readBuf, 0, readLen);
        }
        fos.close();
        inputStream.close();

        // ResourceLoader를 사용하여 파일 리소스 생성
        Resource resource = new FileSystemResource(tempFile);
        log.info("resource: {}", resource);

        // Content-Disposition 헤더 설정
        String contentDisposition = "attachment; filename=\"" + URLEncoder.encode(s3DownloadDto.getOriginalFilename(), "UTF-8") + "\"";

        // 임시 파일 삭제 (ResponseEntity가 전송된 후)
        tempFile.deleteOnExit();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
