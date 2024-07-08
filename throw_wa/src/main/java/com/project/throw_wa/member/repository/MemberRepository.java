package com.project.throw_wa.member.repository;

import com.project.throw_wa.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

//    Member findByUsername(String username);
}
