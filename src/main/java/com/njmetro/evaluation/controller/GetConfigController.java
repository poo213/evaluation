package com.njmetro.evaluation.controller;

import com.njmetro.evaluation.domain.Configurl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname GetConfigController
 * @Description 获取当前配置文件中数据库连接信息
 * @Date 2020/11/14 8:58
 * @Created by zc
 */
@RestController
@RequestMapping("/ConfigUrl")
public class GetConfigController {
    @Autowired
    Configurl configurl;

    @GetMapping("/getConfigUrl")
    public String getConfigUrl() {
        return configurl.getUrl();
    }
}
