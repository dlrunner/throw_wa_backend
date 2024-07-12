package com.project.throw_wa.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp
    private LocalDate createdAt;
    @Enumerated(EnumType.STRING)
    private Authority authority;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "member_id") // authority.member_id 컬럼 작성
//    private List<RoleAuth> authorities;
}
