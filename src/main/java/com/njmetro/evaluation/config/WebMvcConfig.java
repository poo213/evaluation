package com.njmetro.evaluation.config;

import com.njmetro.evaluation.common.SystemCommon;
import com.njmetro.evaluation.interceptor.IpInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 牟欢
 * @Classname CorsConfig
 * @Description TODO
 * @Date 2020-08-31 10:21
 */
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final IpInterceptor ipInterceptor;

    /**
     * 配置静态文件映射(将本地pdf文件映射成浏览器可以访问的 pdf 网址)
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(SystemCommon.WEB_CONFIG_HANDLER).addResourceLocations(SystemCommon.WEB_CONFIG_LOCATION);
    }

    /**
     * 添加自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipInterceptor).addPathPatterns("/api/**");
    }

    /**
     * 解决跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

}