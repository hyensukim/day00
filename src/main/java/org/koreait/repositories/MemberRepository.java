package org.koreait.repositories;

import org.koreait.entities.member.MemberEntity;
import org.koreait.entities.member.QMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<MemberEntity,Long>,
        QuerydslPredicateExecutor<MemberEntity> {

    MemberEntity findByMemberId(String memberId); // 아이디로 멤버 조회

    MemberEntity findByMemberNmAndEmail(String memberNm, String email); // 이름과 이메일로 멤버 조회
    
    MemberEntity findByMemberIdAndEmail(String memberId, String email); // 회원아이디와 이메일로 조회

    // 회원 아이디 중복체크
    default boolean isExist(String memberId){
        QMemberEntity member = QMemberEntity.memberEntity;
        return exists(member.memberId.eq(memberId));
    }

}
