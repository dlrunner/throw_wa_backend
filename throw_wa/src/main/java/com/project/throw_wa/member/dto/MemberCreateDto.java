package com.project.throw_wa.member.dto;

import com.project.throw_wa.member.entity.Member;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MemberCreateDto {
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public Member toMember() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
