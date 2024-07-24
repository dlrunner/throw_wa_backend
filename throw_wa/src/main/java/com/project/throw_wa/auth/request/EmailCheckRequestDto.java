package com.project.throw_wa.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailCheckRequestDto {

    @NotBlank
    private String email;
}
