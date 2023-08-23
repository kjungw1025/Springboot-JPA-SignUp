package springjpa.signup.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    @NotEmpty(message = "휴대폰 번호 입력은 필수 입니다.")
    private String phone;

    @NotEmpty(message = "이메일 입력은 필수 입니다.")
    private String email;

    @NotEmpty(message = "비밀번호 입력은 필수 입니다.")
    private String password;

    private String zipcode;
    private String streetAdr;
    private String detailAdr;
}
