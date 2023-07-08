package org.koreait.services.member;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.utils.MemberUtil;
import org.koreait.dtos.member.MemberInfo;
import org.koreait.exceptions.member.WrongInfoException;
import org.koreait.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWithdrawalService {

    private final MemberRepository repository;
    private final MemberUtil memberUtil;
    private final PasswordEncoder passwordEncoder;

    public void withdrawal(String memberPw){
        if(memberUtil.isLogin()) {
            MemberInfo memberInfo = memberUtil.getMember();
            String id = memberInfo.getMemberId();
            String pw = memberInfo.getMemberPw();

            boolean match = passwordEncoder.matches(memberPw, pw);

            if (!match) {
                throw new WrongInfoException("Validation.memberPw.notMatch", HttpStatus.BAD_REQUEST);
            }
            repository.delete(repository.findByMemberId(id));
            repository.flush();
        }
    }

    public void forcedWithdrawal(){

    }
}