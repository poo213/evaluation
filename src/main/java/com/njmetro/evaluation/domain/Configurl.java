package com.njmetro.evaluation.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Classname Configurl
 * @Description TODO
 * @Date 2020/11/14 9:56
 * @Created by zc
 */
@Getter
@Component
public class Configurl {
    @Value("${spring.datasource.url}")
    private String url;
}
