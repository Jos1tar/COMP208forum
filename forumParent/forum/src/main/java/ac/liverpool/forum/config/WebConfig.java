package ac.liverpool.forum.config;

import ac.liverpool.forum.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //the interceptor for token object
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //add interceptor
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")//what to intercept
                .excludePathPatterns("/login","/user/register","/user/send-verification-code");
                //.excludePathPatterns("/**");//set to intercept no requests while testing

    }

    //set cors for cross-origin requests(in browser)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(false);
    }
}
