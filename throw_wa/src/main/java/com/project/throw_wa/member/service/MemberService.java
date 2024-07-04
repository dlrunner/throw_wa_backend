package com.project.throw_wa.member.service;

import com.project.throw_wa.member.entity.Member;
import com.project.throw_wa.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    /**
     * 1. member테이블 한행 등록
     * 2. authority테이블 한행 등록
     * @param member
     * @return
     */
    public Member createMember(Member member) {
        memberRepository.save(member);
        return member;
    }
}
