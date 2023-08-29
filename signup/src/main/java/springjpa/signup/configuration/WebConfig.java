package springjpa.signup.configuration;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springjpa.signup.web.filter.LogFilter;
import springjpa.signup.web.filter.LoginCheckFilter;

@Configuration
public class WebConfig {
    
    @Bean
    // 스프링부트를 사용할 때 WAS를 가지고 띄우기때문에, WAS를 띄울 때 필터를 같이 넣어줌
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());  // 만든 로그 필터 삽입
        filterRegistrationBean.setOrder(1); // 필터가 chain으로 여러개 들어가므로 순서 정하기
        filterRegistrationBean.addUrlPatterns("/*");    // 어떤 url 패턴을 할 것인지 (/*는 모든 url에 적용됨)

        return filterRegistrationBean;
    }

    @Bean
    // 스프링부트를 사용할 때 WAS를 가지고 띄우기때문에, WAS를 띄울 때 필터를 같이 넣어줌
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");    // 어짜피 화이트리스트에 넣어놨음

        return filterRegistrationBean;
    }
}
