package springjpa.signup.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import springjpa.signup.controller.SessionConst;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    // 로그인 인증이라는 것은 컨트롤러 호출 전에만 호출되면 된다. 따라서 preHandle 만 구현하면 된다.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            
            // 로그인으로 redirect
            response.sendRedirect("/login?redirectURL=" + requestURI);
            
            // 더 이상 진행 안하겠다는 의미
            return false;
        }

        return true;
    }
}
