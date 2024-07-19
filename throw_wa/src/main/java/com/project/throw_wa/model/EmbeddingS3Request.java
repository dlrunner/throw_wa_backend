package com.project.throw_wa.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmbeddingS3Request {

    private String id;
    private List<Float> embedding;
    private String link;
    private String type;
    private String date;
    private String summary;
    private String keyword;
    private String title;
    private String s3OriginalFilename;
    private String s3Key;
    private String s3Url;
}
