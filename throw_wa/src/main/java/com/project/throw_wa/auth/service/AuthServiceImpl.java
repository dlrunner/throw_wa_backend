package com.project.throw_wa.auth.service;

import com.google.protobuf.Struct;
import com.project.throw_wa.auth.request.EmailCheckRequestDto;
import com.project.throw_wa.auth.response.EmailCheckResponseDto;
import com.project.throw_wa.jwt.dto.ResponseDto;
import com.project.throw_wa.jwt.provider.JwtProvider;
import com.project.throw_wa.user.entity.*;
import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import io.pinecone.unsigned_indices_model.ScoredVectorWithUnsignedIndices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${PINECONE.API.KEY}")
    private String pineconeApiKey;
    @Value("${PINECONE.INDEX.NAME}")
    private String pineconeIndexName;

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto dto) {
        try {

            String email = dto.getEmail();

            String namespace = "user";
            Pinecone pc = new Pinecone.Builder(pineconeApiKey).build();
            Index index = pc.getIndexConnection(pineconeIndexName);

            QueryResponseWithUnsignedIndices queryResponse = index.queryByVectorId(1, dto.getEmail(), namespace, null, true, true);
            log.info("queryResponse: {}", queryResponse);
            if (!queryResponse.getMatchesList().isEmpty()) return EmailCheckResponseDto.duplicateId();

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.databaseError();
        }
        return EmailCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

        log.info("dto: {}", dto);

        try {
            String namespace = "user";
            Pinecone pc = new Pinecone.Builder(pineconeApiKey).build();
            Index index = pc.getIndexConnection(pineconeIndexName);

            QueryResponseWithUnsignedIndices queryResponse = index.queryByVectorId(1, dto.getEmail(), namespace, null, true, true);
            log.info("queryResponse: {}", queryResponse);
            if (!queryResponse.getMatchesList().isEmpty()) return SignUpResponseDto.duplicateId();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            User user = new User(dto);
            log.info("user: {}", user);

            List<Float> values = Arrays.asList(0.1f);
            Struct metaData = Struct.newBuilder()
                    .putFields("email", com.google.protobuf.Value.newBuilder().setStringValue(user.getEmail()).build())
                    .putFields("password", com.google.protobuf.Value.newBuilder().setStringValue(user.getPassword()).build())
                    .putFields("name", com.google.protobuf.Value.newBuilder().setStringValue(user.getName()).build())
                    .putFields("type", com.google.protobuf.Value.newBuilder().setStringValue(user.getType()).build())
                    .putFields("role", com.google.protobuf.Value.newBuilder().setStringValue(user.getRole()).build())
                    .build();
            log.info("metaData: {}", metaData);
            index.upsert(user.getEmail(), values, null, null, metaData, namespace);

            log.info("Data processed successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.databaseError();

        }
        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        String namespace = "user";
        Pinecone pc = new Pinecone.Builder(pineconeApiKey).build();
        Index index = pc.getIndexConnection(pineconeIndexName);

        log.info("dto: {}", dto);

        String token = null;

        try {
            QueryResponseWithUnsignedIndices queryResponse = index.queryByVectorId(1, dto.getEmail(), namespace, null, true, true);
            log.info("queryResponse: {}", queryResponse);

            if (!queryResponse.getMatchesList().isEmpty()) SignInResponseDto.singInFail();

            ScoredVectorWithUnsignedIndices matchedVector = queryResponse.getMatchesList().get(0);

            String password = dto.getPassword();
            log.info("password: {}", password);

            String encodedPassword = matchedVector.getMetadata().getFieldsOrThrow("password").getStringValue();
            log.info("encodedPassword: {}", encodedPassword);

            boolean isMatch = passwordEncoder.matches(password, encodedPassword);
            log.info("isMatch: {}", isMatch);

            if (!isMatch) return SignInResponseDto.singInFail();

            String confirmEmail = matchedVector.getMetadata().getFieldsOrThrow("email").getStringValue();
            log.info("confirmEmail: {}", confirmEmail);
            token = jwtProvider.create(confirmEmail);
            log.info("token: {}", token);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return SignInResponseDto.success(token);
    }
}
