package springjpa.signup.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        // preHandle 에서 지정한 값을 postHandle, afterCompletion 에서 함께 사용하려면 어딘가에 담아둬야함
        // LogInterceptor 도 싱글톤 처럼 사용되기 때문에 맴버변수를 사용하면 위험하다.
        // 따라서 request 에 담아두도록 한다. 이 값은 afterCompletion 에서 request.getAttribute(LOG_ID) 로 찾아서 사용한다.
        request.setAttribute(LOG_ID, uuid);

        // @RequestMapping: HandlerMethod
        // 정적 리소스: ResourceHttpRequestHandler
        // 일반적으로 사용하는 컨트롤러는 instance of로 타입이 맞는지 확인하고 사용하면 됨
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;   // 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.

        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;    // 다음 컨트롤러 호출
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}][{}]", uuid, requestURI, handler);

        // 발생한 에러 호출
        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
