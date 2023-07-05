package org.koreait.entities.member;

import jakarta.persistence.*;
import lombok.*;
import org.koreait.commons.constants.Role;
import org.koreait.entities.BaseEntity;

@Entity @Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes={
        @Index(name="idx_member_memberNm",columnList = "memberNm"),
        @Index(name="idx_member_email",columnList = "email")
})
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private long memberNo; // 회원번호

    @Column(length=45, nullable = false, unique = true)
    private String memberId; // 회원아이디

    @Column(length=65, nullable=false)
    private String memberPw; // 회원비밀번호

    @Column(length=35, nullable = false)
    private String memberNm; // 회원명

    @Column(length=100, nullable = false)
    private String email; // 이메일

    @Column(length=11)
    private String mobile; // 연락처

    @Lob
    private String termsAgree; // 약관 동의 내역

    @Enumerated(EnumType.STRING)
    @Column(length=15,nullable = false)
    private Role role = Role.MEMBER; // 권한
}
