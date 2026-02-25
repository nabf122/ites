package egovframework.com.ites.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 사용자 영역 보호
        registry.addInterceptor(new AuthInterceptor(false))
                .addPathPatterns("/user/**");

        // 관리자 영역 보호
        registry.addInterceptor(new AuthInterceptor(true))
                .addPathPatterns("/admin/**");
    }
}