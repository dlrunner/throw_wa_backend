//package com.project.throw_wa.jwt.dto;
//
//import com.project.throw_wa.jwt.common.ResponseCode;
//import com.project.throw_wa.jwt.common.ResponseMessage;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//@Getter
//@AllArgsConstructor
//public class ResponseDto {
//    private String code;
//    private String message;
//
//    public ResponseDto() {
//        this.code = ResponseCode.SUCCESS;
//        this.message = ResponseMessage.SUCCESS;
//    }
//
//    public static ResponseEntity<ResponseDto> databaseError() {
//        ResponseDto responseDto = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
//    }
//
//    public static ResponseEntity<ResponseDto> validationFail() {
//        ResponseDto responseDto = new ResponseDto(ResponseCode.VALIDATE_FAILED, ResponseMessage.VALIDATE_FAILED);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
//    }
//}
