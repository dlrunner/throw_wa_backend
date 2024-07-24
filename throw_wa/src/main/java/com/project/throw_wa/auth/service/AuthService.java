package com.project.throw_wa.auth.service;

import com.project.throw_wa.auth.request.EmailCheckRequestDto;
import com.project.throw_wa.auth.response.EmailCheckResponseDto;
import com.project.throw_wa.user.entity.SignInRequestDto;
import com.project.throw_wa.user.entity.SignInResponseDto;
import com.project.throw_wa.user.entity.SignUpRequestDto;
import com.project.throw_wa.user.entity.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto dto);
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
