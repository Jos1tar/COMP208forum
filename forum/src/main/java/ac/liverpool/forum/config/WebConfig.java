package ac.liverpool.forum.config;

import ac.liverpool.forum.interceptor.DemoInterceptor;
import ac.liverpool.forum.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*//自定义的拦截器对象
    @Autowired
    private DemoInterceptor demoInterceptor;*/

    //拦截器对象
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器对象
        registry.addInterceptor(tokenInterceptor)
               /* .addPathPatterns("/**")//设置拦截器拦截的请求路径（ /** 表示拦截所有请求）
                .excludePathPatterns("/login");//设置拦截器不拦截的请求路径*/
                .excludePathPatterns("/**");//测试期间暂时设置为全都不拦截
    }
}
