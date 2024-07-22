package com.project.throw_wa.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String userId;
    private String password;
    private String email;
    private String name;
    private String type;
    private String role;

    public User(SignUpRequestDto dto) {
        this.userId = dto.getUserId();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.type = "app";
        this.role = "ROLE_USER";
    }

    public User(String userId, String email, String type) {
        this.userId = userId;
        this.password = "Passw0rd";
        this.email = email;
        this.type = type;
        this.role = "ROLE_USER";
    }
}
