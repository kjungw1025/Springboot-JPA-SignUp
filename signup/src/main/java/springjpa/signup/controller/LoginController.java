package springjpa.signup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springjpa.signup.domain.Member;
import springjpa.signup.service.MemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/members/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/members/login")
    public String login(@Valid LoginForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = memberService.login(form.getEmail(), form.getPassword());

        if (loginMember == null) {
            result.reject("loginFail", "이메일 주소 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리 TODO
        return "redirect:/";
    }
}
