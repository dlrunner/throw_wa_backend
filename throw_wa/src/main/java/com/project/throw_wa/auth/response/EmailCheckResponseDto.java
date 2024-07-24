package com.project.throw_wa.auth.response;

import com.project.throw_wa.jwt.common.ResponseCode;
import com.project.throw_wa.jwt.common.ResponseMessage;
import com.project.throw_wa.jwt.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EmailCheckResponseDto extends ResponseDto {

    private EmailCheckResponseDto() {
        super();
    }

    public static ResponseEntity<EmailCheckResponseDto> success() {
        EmailCheckResponseDto responseDto = new EmailCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<ResponseDto> duplicateId() {
        ResponseDto responseDto = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
