package org.koreait.services.member;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.Role;
import org.koreait.entities.member.MemberEntity;
import org.koreait.exceptions.member.MemberDataNotExistsException;
import org.koreait.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void updatePw(long memberNo, String memberPw){
        MemberEntity member = repository.findById(memberNo).orElseThrow(MemberDataNotExistsException::new);

        member.setMemberPw(passwordEncoder.encode(memberPw));

        repository.saveAndFlush(member);
    }

    public void updateRoll(long memberNo, Role role){
        MemberEntity member = repository.findById(memberNo).orElseThrow(MemberDataNotExistsException::new);
        Role role_db = member.getRole();

        if(role_db != role){
            member.setRole(role);
            repository.saveAndFlush(member);
        }
    }
}
