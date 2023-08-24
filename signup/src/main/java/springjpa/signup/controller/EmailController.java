package springjpa.signup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import springjpa.signup.service.EmailService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/mail")
    @ResponseBody
    public void emailConfirm(@RequestBody EmailForm emailForm) throws Exception {
        log.info("post emailConfirm");
        emailService.sendSimpleMessage(emailForm.getEmail());
    }

    @PostMapping("/verifyCode")
    @ResponseBody
    public int verifyCode(@RequestBody EmailForm emailForm) {
        log.info("post verifyCode");

        int result = 0;
        if (emailService.getePw().equals(emailForm.getCode())) {
            result = 1;
        }
        return result;
    }
}
