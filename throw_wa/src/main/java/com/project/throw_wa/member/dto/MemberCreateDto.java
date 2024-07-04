package com.project.throw_wa.member.dto;

import com.project.throw_wa.member.entity.Member;
import lombok.Data;

@Data
public class MemberCreateDto {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;

    public Member toMember() {
        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .build();
    }
}
