package com.project.throw_wa.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmbeddingRequest {

    private String id;
    private List<Float> embedding;
    private String link;
}
