package com.project.throw_wa.controller;

import com.project.throw_wa.model.EmbeddingRequest;
import com.project.throw_wa.model.EmbeddingS3Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class EmbeddingController {

    private static final Logger log = LoggerFactory.getLogger(EmbeddingController.class);

    @PostMapping("/embedding")
    public ResponseEntity<String> receiveEmbedding(@RequestBody EmbeddingRequest request) {
//        log.info("임베딩: {}", request.getEmbedding());
//        log.info("아이디: {}", request.getId());
//        log.info("링크: {}", request.getLink());
//        log.info("type: {}", request.getType());
//        log.info("date: {}", request.getDate());
//        log.info("summary: {}", request.getSummary());
//        log.info("keyword: {}", request.getKeyword());
//        log.info("title: {}", request.getTitle());

        try {
            // FastAPI 서버로 전송
            RestTemplate restTemplate = new RestTemplate();
            String fastapiUrl = "http://localhost:8000/api/vector_upsert";
            HttpEntity<EmbeddingRequest> httpRequest = new HttpEntity<>(request);
            log.info("파이썬 서버로 찌르는 url : {}", fastapiUrl);

            ResponseEntity<String> response = restTemplate.postForEntity(fastapiUrl, httpRequest, String.class);
            log.info("Received response from FastAPI server: {}", response);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Failed to send data to FastAPI server: {}", response.getStatusCode());
                return new ResponseEntity<>("Failed to send data to FastAPI server", response.getStatusCode());
            }

            log.info("Data processed successfully");
            return new ResponseEntity<>("Data processed successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/embeddingS3")
    public ResponseEntity<String> receiveEmbeddingS3(@RequestBody EmbeddingS3Request request) {
//        log.info("임베딩: {}", request.getEmbedding());
//        log.info("아이디: {}", request.getId());
//        log.info("링크: {}", request.getLink());
//        log.info("type: {}", request.getType());
//        log.info("date: {}", request.getDate());
//        log.info("summary: {}", request.getSummary());
//        log.info("keyword: {}", request.getKeyword());
//        log.info("title: {}", request.getTitle());
        log.info("S3OriginalFilename: {}", request.getS3OriginalFilename());
        log.info("S3Key: {}", request.getS3Key());
        log.info("S3Url: {}", request.getS3Url());

        try {
            // FastAPI 서버로 전송
            RestTemplate restTemplate = new RestTemplate();
            String fastapiUrl = "http://localhost:8000/api/vector_s3_upsert";
            HttpEntity<EmbeddingS3Request> httpRequest = new HttpEntity<>(request);
            log.info("파이썬 서버로 찌르는 url : {}", fastapiUrl);

            ResponseEntity<String> response = restTemplate.postForEntity(fastapiUrl, httpRequest, String.class);
            log.info("Received response from FastAPI server: {}", response);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Failed to send data to FastAPI server: {}", response.getStatusCode());
                return new ResponseEntity<>("Failed to send data to FastAPI server", response.getStatusCode());
            }

            log.info("Data processed successfully");
            return new ResponseEntity<>("Data processed successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
