package com.project.throw_wa.s3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class S3CreateDto {
    private String originalFilename; // 업로드한 파일명
    private String key; // S3파일 식별자
    private String url; // S3파일 url
}
