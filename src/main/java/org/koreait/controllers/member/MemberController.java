package org.koreait.controllers.member;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.models.member.FindMemberService;
import org.koreait.models.member.MemberInfo;
import org.koreait.models.member.MemberSaveService;
import org.koreait.models.member.MemberWithdrawalService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController{

    private final FindMemberService findMemberService;
    private final MemberSaveService saveService;
    private final MemberWithdrawalService withdrawalService;
    private final JoinValidator validator;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String join(Model model){

        commonProcess(model, "회원가입");

        JoinForm joinForm = new JoinForm();

        model.addAttribute("joinForm",joinForm);

        return "member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model){
        commonProcess(model, "회원가입");
        boolean[] n = joinForm.getAgrees();
        if(n == null || n.length == 0){System.out.println("joinForm.getAgrees() ->"+n+", 길이 : "+n.length);}
        else{System.out.println("joinForm.getAgrees() ->" + n[0]);}

        validator.validate(joinForm,errors);

        System.out.println(errors);

        if(errors.hasErrors()) {return "member/join";}

        saveService.save(joinForm);

        model.addAttribute("joinForm",joinForm);

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, HttpSession session,
                        @CookieValue(required = false) String saveId, Model model){
        if(saveId != null){
            loginForm.setMemberId(saveId);
            loginForm.setSaveId(true);
        }

        String memberId = (String) session.getAttribute("memberId");
        if(memberId != null){
            loginForm.setMemberId(memberId);
        }

        commonProcess(model,"로그인");
        return "member/login";
    }

    @GetMapping("/findId")
    public String findId(@ModelAttribute FindIdForm findIdForm, Model model){
        commonProcess(model,"아이디 찾기");
        return "member/findid";
    }

    @PostMapping("/findId")
    public String findIdPs(@Valid FindIdForm findIdForm, Errors errors, Model model){

        if(errors.hasErrors()){ return "member/findid";}

        String memberId = findMemberService.findId(findIdForm);

        if(memberId == null){ errors.reject("Validation.member.notExists.name");}
        model.addAttribute("findIdData",memberId);
        commonProcess(model,"아이디 찾기");
        return "member/findid";
    }

    @GetMapping("/findPw")
    public String findPw(@ModelAttribute FindPwForm findPwForm, Model model){
        commonProcess(model,"비밀번호 찾기");
        return "member/findpw";
    }

    @GetMapping("/withdrawal")
    public String delete(Model model){
        commonProcess(model,"회원 탈퇴");
        return "member/withdrawal";
    }

    @PostMapping("/withdrawal")
    public String delete(Model model, HttpSession httpSession, String memberPw, String withdrawalMsg){
        MemberInfo memberInfo = (MemberInfo) httpSession.getAttribute("memberInfo");
        String memberId = memberInfo.getMemberId();
        String _memberPw = memberInfo.getMemberPw();

        boolean result = passwordEncoder.matches(memberPw,_memberPw);


        if(!result || withdrawalMsg.isBlank() || withdrawalMsg == null){
            return "member/withdrawal";
        }

        withdrawalService.withdrawal(memberId);
        MessageDto message = new MessageDto("회원 탈퇴가 완료되었습니다.", "/member/login", RequestMethod.GET, null);
        
        httpSession.removeAttribute("memberInfo");
        httpSession.invalidate();

        commonProcess(model,"회원 탈퇴");
        
        return showMsgAndRedirect(message, model);
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("addCss","style2");
        model.addAttribute("title",title);
    }

    private String showMsgAndRedirect(MessageDto params, Model model){
        model.addAttribute("params",params);
        return "common/messageRedirect";
    }
}
