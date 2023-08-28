package springjpa.signup.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    @NotEmpty(message = "이메일 입력은 필수 입니다.")
    @Email(message = "이메일 형식을 지켜주세요.")
    private String email;

    @NotEmpty(message = "비밀번호 입력은 필수 입니다.")
    private String password;
}
