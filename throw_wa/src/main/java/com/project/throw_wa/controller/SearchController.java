package com.project.throw_wa.controller;

import com.project.throw_wa.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final RestTemplate restTemplate;
    @Value("${PINECONE.API.KEY}")
    private String pineconeApiKey;
    @Value("${PINECONE.INDEX.NAME.USER}")
    private String pineconeIndexName;

    private final JwtProvider jwtProvider;

    @PostMapping("/validated_search")
    public ResponseEntity<?> search(@RequestBody Map<String, String> request) {
        log.info("search 호출됨");
        log.info("request: {}", request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

        Map<String, String> requestData = new HashMap<>();

        String token = request.get("token");
        log.info("token: {}", token);

        String email = jwtProvider.validate(token);
        log.info("email: {}", email);

        requestData.put("email", email);

        return new ResponseEntity<>(requestData, headers, HttpStatus.OK);
    }
}
