package com.njmetro.evaluation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 牟欢
 * @Classname CorsConfig
 * @Description TODO
 * @Date 2020-08-31 10:21
 */
@Configuration(proxyBeanMethods = false)
public class CorsConfig {
    /**
     * 允许访问所有路径和支持所有方法
     *
     * @return WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }
}
