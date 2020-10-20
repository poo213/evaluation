package com.njmetro.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 牟欢
 * @Classname TestController
 * @Description TODO
 * @Date 2020-10-18 11:55
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final StudentService studentService;
    private final PadService padService;
    private final SeatDrawService seatDrawService;
    private final JudgeDrawResultService judgeDrawResultService;
    private final ConfigService configService;

    @GetMapping("/student/beReady")
    public void studentBeReady() {
        Config config = configService.getById(1);
        int[] padIDArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
        for (int i = 0; i < padIDArray.length; i++) {
            Pad pad = padService.getById(padIDArray[i]);
            UpdateWrapper<SeatDraw> seatDrawUpdateWrapper = new UpdateWrapper<>();
            seatDrawUpdateWrapper.eq("seat_id", pad.getSeatId())
                    .eq("game_number", config.getGameNumber())
                    .eq("game_round", config.getGameRound())
                    //选手准备就绪
                    .set("state", 1);
            log.info("学生状态修改成功");
            seatDrawService.update(seatDrawUpdateWrapper);
        }
    }

    @GetMapping("/judge/beReady")
    public void judgeBeReady() {
        int[] padIDArray = {19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54};
        for (int i = 0; i < padIDArray.length; i++) {
            Pad pad = padService.getById(padIDArray[i]);
            // 通过padId 在 JudgeDrawResult 中找到对应的记录
            QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
            judgeDrawResultQueryWrapper.eq("pad_id", pad.getId());
            JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(judgeDrawResultQueryWrapper);
            // 修改就绪状态为 1
            judgeDrawResult.setState(1);
            log.info("judgeDrawResult {}", judgeDrawResult);
            judgeDrawResultService.updateById(judgeDrawResult);
        }

    }

    @GetMapping("/doEnd")
    public Boolean doEnd() {
        Config config = configService.getById(1);
        config.setState(4);
        return configService.updateById(config);
    }

    /**
     * 模拟全部签到
     */

    @GetMapping("/signAll")
    public Boolean signAll() {
        List<Student> studentList = studentService.list();
        for (Student student : studentList) {
            student.setSignState("1");
            studentService.updateById(student);
        }
        return true;
    }


}