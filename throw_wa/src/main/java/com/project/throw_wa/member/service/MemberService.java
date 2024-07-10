package com.project.throw_wa.member.service;

import com.project.throw_wa.member.dto.MemberCreateDto;
import com.project.throw_wa.member.entity.Member;
import com.project.throw_wa.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@Slf4j
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

//    public Member findByUsername(String username) {
//        return memberRepository.findByUsername(username);
//    }

    public ResponseEntity<?> createMember(MemberCreateDto memberCreateDto) {
        Member member = memberCreateDto.toMember();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return ResponseEntity.created(URI.create("/member/" + member.getId())).build();
    }
}
