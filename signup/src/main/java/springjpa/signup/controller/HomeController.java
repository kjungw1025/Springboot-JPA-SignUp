package springjpa.signup.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import springjpa.signup.domain.Member;
import springjpa.signup.service.MemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

//    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }

//    @GetMapping("/")
    public String homeLogin2(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null)
            return "home";

        // 로그인
        Member loginMember = memberService.findOne(memberId);

        System.out.println("로그인 성공인지 확인" + loginMember.getEmail());

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    // 이미 로그인 된 사용자를 찾을 때 @SessionAttribute 사용. 이 기능은 세션을 생성하지 않음
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
//        세션이 없으면 home (HttpServletRequest request)
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return "home";
//        }
//
//        // 로그인
//        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
