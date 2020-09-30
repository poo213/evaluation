package com.njmetro.evaluation.config;
import com.njmetro.evaluation.common.SystemCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置消息转换器
 * list为空 -> []
 * string -> ""
 * boolean false
 * 消除循环引用
 * @author mubaisama
 */
@Slf4j
@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {
    /**
     *  解决静态文件无法找到的问题
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //文件上传后的映射地址
        registry.addResourceHandler(SystemCommon.WEB_CONFIG_HANDLER).addResourceLocations(SystemCommon.WEB_CONFIG_LOCATION);
    }
}