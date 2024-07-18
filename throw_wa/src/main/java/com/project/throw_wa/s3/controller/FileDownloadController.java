package com.project.throw_wa.s3.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.throw_wa.s3.dto.S3CreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    @PostMapping("/download")
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

//    public ResponseEntity<?> download(AttachmentDetailDto attachmentDetailDto) throws UnsupportedEncodingException {
//        // http:// https://은 UrlResource 구현 객체를 통해 자원을 획득
//        Resource resource = resourceLoader.getResource(attachmentDetailDto.getUrl());
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + URLEncoder.encode(attachmentDetailDto.getOriginalFilename(), "UTF-8"))
//                .body(resource);
//    }
}
