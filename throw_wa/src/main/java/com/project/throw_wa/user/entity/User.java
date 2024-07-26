package com.project.throw_wa.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String email;
    private String password;
    private String name;
    private String type;
    private String role;

    public User(SignUpRequestDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.type = "app";
        this.role = "ROLE_USER";
    }

    public User(String userId, String email, String type) {
        this.password = "Passw0rd";
        this.email = email;
        this.type = type;
        this.role = "ROLE_USER";
    }
}
