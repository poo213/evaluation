package com.njmetro.evaluation.controller.api;

import com.njmetro.evaluation.vo.api.test;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: evaluation
 * @description: 裁判对外接口
 * @author: zc
 * @create: 2020-09-29 08:42
 **/
@RestController
@RequestMapping("/api/judge")
@RequiredArgsConstructor
@Slf4j
public class JudgeAPI {
    @PostMapping("/test")
    public void test(@RequestBody List<test> t) {
        log.info("t:{}",t);
    }
}
