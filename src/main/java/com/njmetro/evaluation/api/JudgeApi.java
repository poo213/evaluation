package com.njmetro.evaluation.vo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 牟欢
 * @Classname JudgeApi
 * @Description TODO
 * @Date 2020-09-29 9:52
 */
@RestController
@RequestMapping("/api/judge")
@Slf4j
public class JudgeApi {

    /**
     * 轮询接口 获取 裁判信息，考生赛位号，场次，轮次
     * @param gameNumber
     * @param gameRound
     * @return
     */
    @GetMapping("/getJudgeInformation")
    public List<Integer> getJudgeInformation(Integer gameNumber,Integer gameRound){
        return null;
    }
}
