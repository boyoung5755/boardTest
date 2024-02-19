package innovationT.util;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@RequiredArgsConstructor
//@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomIntercepter())
                .addPathPatterns("/admin");  // 인터셉터를 적용할 URL 패턴을 지정합니다.
	}

}
