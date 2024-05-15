package org.geely.common.config;

import org.geely.common.interceptor.AccountInterceptor;
import org.geely.common.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurationSupport {

    /**
     * 白名单路由集合
     */
    private static final List<String> WHITE_LIST = new ArrayList<>();

    /**
     * 初始化的时候添加白名单路由
     */
    static {
        WHITE_LIST.add("/swagger-resources/**");
        WHITE_LIST.add("/webjars/**");
        WHITE_LIST.add("/v2/api-docs");
        WHITE_LIST.add("/swagger-ui.html/**");
        WHITE_LIST.add("/error/**");
    }

    @Bean
    public AuthInterceptor getAuthInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    public AccountInterceptor getAccountInterceptor() {
        return new AccountInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(WHITE_LIST)
                .excludePathPatterns("/static/**", "/sso/**", "/auth/**", "/site/**", "/app/**");
        registry.addInterceptor(getAccountInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(WHITE_LIST)
                .excludePathPatterns("/static/**", "/sso/**", "/auth/**", "/app/**","/permission/getUserRolePermissions**","/user/appDetail**");
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);

        //swagger(bootstrap风格)
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    //     拦截器跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域路径
        registry.addMapping("/**") // 所有接口
                .allowCredentials(true) // 是否发送 Cookie
                .allowedOriginPatterns("*") // 支持域
                .allowedMethods("POST", "GET", "OPTIONS", "DELETE", "PUT") // 支持方法
                .allowedHeaders("*")// 允许请求头
                .exposedHeaders("*");// 暴露出去的响应头

    }
}
