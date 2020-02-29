package zhangdahu.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SesstionInterceptor sesstionInterceptor;

    public void addInterceptors(InterceptorRegistry registy)
    {
        registy.addInterceptor(sesstionInterceptor).addPathPatterns("/**");
    }
}
