package com.project.throw_wa.member.service;

import com.project.throw_wa.member.dto.MemberCreateDto;
import com.project.throw_wa.member.entity.Member;
import com.project.throw_wa.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@Slf4j
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public ResponseEntity<?> createMember(Member member) {
        memberRepository.save(member);
        return ResponseEntity.created(URI.create("/member/" + member.getId())).build();
    }

    public Member findByEmail(String username) {
        return memberRepository.findByEmail(username);
    }
}
