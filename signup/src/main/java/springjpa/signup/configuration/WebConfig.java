package springjpa.signup.configuration;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springjpa.signup.web.filter.LogFilter;
import springjpa.signup.web.filter.LoginCheckFilter;
import springjpa.signup.web.interceptor.LogInterceptor;
import springjpa.signup.web.interceptor.LoginCheckInterceptor;

@Configuration
// Servlet Filter는 public class WebConfig만 해줘도됨
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")     // 모든 걸 허용하지만
                .excludePathPatterns("/css/**", "/*.ico", "/error");    // 그중에서 인터셉터 예외 구간

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/new", "/login", "/logout", "/css/**", "/*.ico", "/error");  // 인터셉터의 큰 장점!
    }

//    @Bean
    // 스프링부트를 사용할 때 WAS를 가지고 띄우기때문에, WAS를 띄울 때 필터를 같이 넣어줌
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());  // 만든 로그 필터 삽입
        filterRegistrationBean.setOrder(1); // 필터가 chain으로 여러개 들어가므로 순서 정하기
        filterRegistrationBean.addUrlPatterns("/*");    // 어떤 url 패턴을 할 것인지 (/*는 모든 url에 적용됨)

        return filterRegistrationBean;
    }

//    @Bean
    // 스프링부트를 사용할 때 WAS를 가지고 띄우기때문에, WAS를 띄울 때 필터를 같이 넣어줌
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");    // 어짜피 화이트리스트에 넣어놨음

        return filterRegistrationBean;
    }

    /**
     * 필터가 인터셉터보다 먼저 적용 되므로
     * 순서 : logFilter() > loginCheckFilter() > addInterceptors()
     * 
     * 인터셉터 사용할 것이므로 서블릿 필터 부분은 주석처리했음
     */
}
