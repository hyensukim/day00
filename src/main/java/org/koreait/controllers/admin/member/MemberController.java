package org.koreait.controllers.admin.member;

import lombok.RequiredArgsConstructor;
import org.koreait.dtos.admin.board.BoardCount;
import org.koreait.dtos.admin.member.MemberCount;
import org.koreait.entities.member.MemberEntity;
import org.koreait.services.member.MemberListService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("AdminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberListService memberListService;

    @GetMapping
    public String index(Model model){
        commonProcess(model, "회원 목록");

        MemberCount memberCount = new MemberCount();
        Page<MemberEntity> list = memberListService.gets(memberCount);
        model.addAttribute("items", list.getContent());

        return "admin/member/index";
    }
    
    private void commonProcess(Model model, String title){
        model.addAttribute("title", title);
    }
}
