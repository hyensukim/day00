package org.koreait.services.member;

import lombok.RequiredArgsConstructor;
import org.koreait.dtos.admin.member.MemberCount;
import org.koreait.entities.member.MemberEntity;
import org.koreait.repositories.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.springframework.data.domain.Sort.Order.desc;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberListService {

    private final MemberRepository repository;

    public Page<MemberEntity> gets(MemberCount memberCount){
        int page = memberCount.getPage();
        int limit = memberCount.getLimit();
        page = page < 1 ? 1 : page;
        limit = limit < 1 ? 20 : limit;

        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(desc("createdAt")));
        Page<MemberEntity> list = repository.findAll(pageable);

        return list;
    }

}
