package springjpa.signup.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    private String ePw;

    @Value("${spring.mail.username}")
    private String id;

    public MimeMessage createMessage(String to)throws MessagingException, UnsupportedEncodingException {
        ePw = createKey();

        log.info("보내는 대상 : "+ to);
        log.info("인증 번호 : " + ePw);
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
        message.setSubject("Shop 회원가입 인증 코드: "); // 메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 인증 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += ePw;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id,"Shop_Admin")); //보내는 사람의 메일 주소, 보내는 사람 이름

        return message;
    }

    public String getePw() {
        return ePw;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        // 인증 코드 8자리
        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch(index) {
                case 0: // a ~ z
                    key.append((char) ((int)(rnd.nextInt(26)) + 97));
                    break;
                case 1: // A ~ Z
                    key.append((char) ((int)(rnd.nextInt(26)) + 65));
                    break;
                case 2: // 0 ~ 9
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }

        return key.toString();
    }

    /*
    메일 발송
    sendSimpleMessage의 매개변수로 들어온 to는 인증번호를 받을 메일주소
    MimeMessage 객체 안에 내가 전송할 메일의 내용을 담아준다.
    bean으로 등록해둔 javaMailSender 객체를 사용하여 이메일 send
 */
    public void sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try{
            javaMailSender.send(message); // 메일 발송
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
