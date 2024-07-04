package com.project.throw_wa.authority.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "authority",
        uniqueConstraints =
        @UniqueConstraint(
                name = "uq_authority_member_id_name",
                columnNames = {"member_id", "name"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "member_id")
    private Long memberId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleAuth roleAuth; // 권한이름
}