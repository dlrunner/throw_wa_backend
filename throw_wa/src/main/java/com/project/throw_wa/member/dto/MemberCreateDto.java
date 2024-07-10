package com.project.throw_wa.member.dto;

import com.project.throw_wa.member.entity.Member;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class MemberCreateDto {
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public Member toMember() {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
