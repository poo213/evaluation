package com.njmetro.evaluation.controller;

import com.njmetro.evaluation.exception.ErrorEnum;
import com.njmetro.evaluation.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: evaluation
 * @description: 测试用
 * @author: zc
 * @create: 2020-09-19 11:05
 **/
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {
    @GetMapping("/test")
    public String test()
    {
//        int a = 1;
//        if (a==1){
//            throw new GlobalException(HttpStatus.BAD_REQUEST, ErrorEnum.GET_USER_INFO_ERROR);
//        }
        log.info("11111");
        return "12111";
    }
}
