package com.njmetro.evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.service.JudgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@RestController
@RequestMapping("/judge")
@RequiredArgsConstructor
@Slf4j
public class JudgeController {
    private final JudgeService judgeService;
    /**
     * 获取裁判列表
     */
    @GetMapping("/getJudgeList")
    public List<Judge> getStudentList() {
        return judgeService.list();
    }

    /**
     * 裁判签到
     */
    @PostMapping("/signIn")
    public String signIn(@RequestBody List<Integer> idList) {
        List<String> errorInfo = new ArrayList<>();
        for (Integer id : idList) {
            UpdateWrapper<Judge> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id).set("sign_state", "1");
            if (!judgeService.update(updateWrapper)) {
                errorInfo.add(id + "号，签到失败!");
            }
        }
        if (errorInfo.size() == 0) {
            return "签到成功";
        } else {
            return errorInfo.toString();
        }

    }

}

