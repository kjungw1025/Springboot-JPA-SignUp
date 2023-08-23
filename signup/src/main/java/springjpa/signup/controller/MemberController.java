package springjpa.signup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springjpa.signup.domain.Address;
import springjpa.signup.domain.Member;
import springjpa.signup.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getZipcode(), form.getStreetAdr(), form.getDetailAdr());

        Member member = new Member();
        member.setName(form.getName());
        member.setPhone(form.getPhone());
        member.setEmail(form.getEmail());
        member.setPassword(passwordEncoder.encode(form.getPassword()));
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }


}
