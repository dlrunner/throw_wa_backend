package com.project.throw_wa.user.entity;

import com.project.throw_wa.jwt.common.ResponseCode;
import com.project.throw_wa.jwt.common.ResponseMessage;
import com.project.throw_wa.jwt.dto.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends ResponseDto {

    private String token;
    private String username;
    private int expirationTime;

    private SignInResponseDto (String token, String username) {
        super();
        this.token = token;
        this.username = username;
        this.expirationTime = 3600;
    }

    public static ResponseEntity<SignInResponseDto> success (String token, String username) {
        SignInResponseDto signInResponseDto = new SignInResponseDto(token, username);
        return ResponseEntity.status(HttpStatus.OK).body(signInResponseDto);
    }

    public static ResponseEntity<ResponseDto> singInFail () {
        ResponseDto responseDto = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }
}
