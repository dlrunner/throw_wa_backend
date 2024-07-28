package com.project.throw_wa.controller;

import com.project.throw_wa.jwt.provider.JwtProvider;
import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import io.pinecone.unsigned_indices_model.ScoredVectorWithUnsignedIndices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.ParameterizedTypeReference;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(UrlController.class);

    @Value("${PYTHON.API.URL}")
    private String pythonApiUrl;
    @Value("${PINECONE.API.KEY}")
    private String pineconeApiKey;
    @Value("${PINECONE.INDEX.NAME.USER}")
    private String pineconeIndexName;

    @Autowired
    private RestTemplate restTemplate;
    private final JwtProvider jwtProvider;

    @PostMapping("/FileUpload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("token") String token) {
        log.info("uploadFile 호출됨");

        try {
            // JWT 토큰을 통해 사용자 정보 추출
            String email = jwtProvider.validate(token);
            String namespace = "user";
            Pinecone pc = new Pinecone.Builder(pineconeApiKey).build();
            Index index = pc.getIndexConnection(pineconeIndexName);
            QueryResponseWithUnsignedIndices queryResponse = index.queryByVectorId(1, email, namespace, null, true, true);
            ScoredVectorWithUnsignedIndices matchedVector = queryResponse.getMatchesList().get(0);
            String userName = matchedVector.getMetadata().getFieldsOrThrow("name").getStringValue();

            // 파일을 Base64로 인코딩
            String base64File = Base64.getEncoder().encodeToString(file.getBytes());

            // 현재 날짜 및 시간 가져오기
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

            // 파이썬 API에 보낼 데이터 준비
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("file", base64File);
            requestData.put("date", currentDate);
            requestData.put("userId", email);
            requestData.put("userName", userName);
            requestData.put("fileName", file.getOriginalFilename());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestData, headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    pythonApiUrl + "/upload_pdf", HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully received response from Python API");
                return ResponseEntity.ok(response.getBody());
            } else {
                log.error("Python API call failed with status code: {}", response.getStatusCode());
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "파이썬 API 호출 실패: " + response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body(errorResponse);
            }
        } catch (IOException e) {
            log.error("Exception occurred while processing the file: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "파일 처리 오류: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        } catch (Exception e) {
            log.error("Exception occurred while calling Python API: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "서버 오류: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
