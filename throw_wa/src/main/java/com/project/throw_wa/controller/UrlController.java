package com.project.throw_wa.controller;

import com.google.protobuf.Struct;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {

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

    @PostMapping("/url")
    public ResponseEntity<Map<String, Object>> processUrl(@RequestBody Map<String, String> request) {
        log.info("processUrl 호출됨");
        log.info("Request body: {}", request);  // 요청 본문 로그 출력
        String url = request.get("url");
        log.info("Received URL: {}", url);

        String linkType = detectLinkType(url);
        log.info("Detected link type: {}", linkType);

        String apiUrl;
        switch (linkType) {
            case "youtube":
                apiUrl = "http://localhost:8000/api/youtube_text";
                break;
            case "pdf":
                apiUrl = "http://localhost:8000/api/pdf_text";
                break;
            case "image":
                apiUrl = "http://localhost:8000/api/image_embedding";
                break;
            case "web":
                apiUrl = "http://localhost:8000/api/crawler";
                break;
            default:
                log.warn("Unsupported URL type detected: {}", linkType);
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "지원하지 않는 URL 유형입니다.");
                return ResponseEntity.badRequest().body(response);
        }

        // 파이썬 API에 요청 보내기
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

            // 파이썬 API에 보낼 데이터
            Map<String, String> requestData = new HashMap<>();
            requestData.put("url", url);
            log.info("url: {}", url);
            requestData.put("date", currentDate);
            log.info("Sending request to Python API: {}", apiUrl);

            // jwt 토큰 -> userId
            String token = request.get("token");
//            log.info("token: {}", token);
            String email = jwtProvider.validate(token);
//            log.info("email: {}", email);

            String namespace = "user";
            Pinecone pc = new Pinecone.Builder(pineconeApiKey).build();
            Index index = pc.getIndexConnection(pineconeIndexName);
            List<Float> values = Arrays.asList(0.1f);
            Struct filter = Struct.newBuilder()
                    .putFields("email", com.google.protobuf.Value.newBuilder()
                            .setStructValue(Struct.newBuilder()
                                    .putFields("$eq", com.google.protobuf.Value.newBuilder()
                                            .setStringValue(email)
                                            .build()))
                            .build())
                    .build();

            QueryResponseWithUnsignedIndices queryResponse = index.query(1, values, null, null, null, namespace, filter, false, true);
            log.info("queryResponse: {}", queryResponse);
            ScoredVectorWithUnsignedIndices matchedVector = queryResponse.getMatchesList().get(0);
            String userName = matchedVector.getMetadata().getFieldsOrThrow("name").getStringValue();

            requestData.put("userId", email);
            requestData.put("userName", userName);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestData, headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    apiUrl, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

            // 파이썬 API로부터 응답 받기
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
        } catch (Exception e) {
            log.error("Exception occurred while calling Python API: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "서버 오류: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    private String detectLinkType(String url) {
        String youtubePattern = "(https?://)?(www\\.)?(youtube|youtu|youtube-nocookie)\\.(com|be)/";
        String pdfPattern = "\\.pdf$";
        String imagePattern = "\\.(jpeg|jpg|gif|png|bmp|tiff|webp)$";

        if (Pattern.compile(youtubePattern).matcher(url).find()) {
            return "youtube";
        } else if (Pattern.compile(pdfPattern).matcher(url).find()) {
            return "pdf";
        } else if (Pattern.compile(imagePattern, Pattern.CASE_INSENSITIVE).matcher(url).find()) {
            return "image";
        } else {
            return "web";
        }
    }


}
