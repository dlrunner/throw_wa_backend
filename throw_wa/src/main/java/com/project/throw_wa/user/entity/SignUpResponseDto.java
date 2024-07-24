package com.project.throw_wa.user.entity;

import com.project.throw_wa.jwt.common.ResponseCode;
import com.project.throw_wa.jwt.common.ResponseMessage;
import com.project.throw_wa.jwt.dto.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignUpResponseDto extends ResponseDto {

    private SignUpResponseDto() {
        super();
    }

    public static ResponseEntity<SignUpResponseDto> success() {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(signUpResponseDto);
    }

    public static ResponseEntity<ResponseDto> duplicateId () {
        ResponseDto responseDto = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    public static ResponseEntity<ResponseDto> certificationFail () {
        ResponseDto responseDto = new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }
}
