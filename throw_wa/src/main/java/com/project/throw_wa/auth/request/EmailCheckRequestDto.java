package com.project.throw_wa.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailCheckRequestDto {

    private String email;

    private String password;

    private String name;
}
