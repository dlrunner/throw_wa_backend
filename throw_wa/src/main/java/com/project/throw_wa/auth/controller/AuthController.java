package com.project.throw_wa.auth.controller;

import com.project.throw_wa.auth.request.EmailCheckRequestDto;
import com.project.throw_wa.auth.response.EmailCheckResponseDto;
import com.project.throw_wa.auth.service.AuthService;
import com.project.throw_wa.user.entity.SignInRequestDto;
import com.project.throw_wa.user.entity.SignInResponseDto;
import com.project.throw_wa.user.entity.SignUpRequestDto;
import com.project.throw_wa.user.entity.SignUpResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "chrome-extension://haiopfamngobgjlahbofhgdomhkgjobl", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/email-check")
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(@RequestBody @Valid EmailCheckRequestDto emailCheckRequestDto) {
        ResponseEntity<? super EmailCheckResponseDto> response = authService.emailCheck(emailCheckRequestDto);
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        log.info("Sign up request: {}", signUpRequestDto);
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(signUpRequestDto);
        log.info("Sign up response: {}", response);
        return response;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(signInRequestDto);
        log.info("Sign In response: {}", response);
        return response;
    }
}
