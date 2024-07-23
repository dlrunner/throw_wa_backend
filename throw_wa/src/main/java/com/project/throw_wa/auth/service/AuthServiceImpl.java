package com.project.throw_wa.auth.service;

import com.project.throw_wa.auth.request.EmailCheckRequestDto;
import com.project.throw_wa.auth.response.EmailCheckResponseDto;
import com.project.throw_wa.jwt.dto.ResponseDto;
import com.project.throw_wa.jwt.provider.JwtProvider;
import com.project.throw_wa.user.entity.*;
import com.project.throw_wa.user.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRespository userRespository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto dto) {
        try {

            String email = dto.getEmail();
            boolean isExistEmail = userRespository.existsByEmail(email);
            if (isExistEmail) return EmailCheckResponseDto.duplicateId();

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
            String email = dto.getEmail();

            boolean isExistEmail = userRespository.existsByEmail(email);
            if (isExistEmail) return SignUpResponseDto.duplicateId();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            User user = new User(dto);
            log.info("user: {}", user);
            userRespository.save(user);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.databaseError();

        }
        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        log.info("dto: {}", dto);

        String token = null;

        try {
            String email = dto.getEmail();
            log.info("email: {}", email);
            User user = userRespository.findByEmail(email);
            log.info("user: {}", user);
            if (user == null) SignInResponseDto.singInFail();

            String password = dto.getPassword();
            log.info("password: {}", password);
            String encodedPassword = user.getPassword();
            log.info("encodedPassword: {}", encodedPassword);
            boolean isMatch = passwordEncoder.matches(password, encodedPassword);
            log.info("isMatch: {}", isMatch);
            if (!isMatch) return SignInResponseDto.singInFail();

            token = jwtProvider.create(email);
            log.info("token: {}", token);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return SignInResponseDto.success(token);
    }
}
