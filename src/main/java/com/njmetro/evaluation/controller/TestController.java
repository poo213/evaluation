package com.njmetro.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.exception.BaseException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    private final TestQuestionService testQuestionService;

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

    /**
     * Put 更新数据
     * @return
     */
    @PutMapping("/update")
    public Boolean a() {
        return true;
    }

    /**
     * 重置 student 数据库状态
     * @return
     */
    @GetMapping("/resetStudent")
    public Boolean resetStudent() {
        List<Student>  studentList = studentService.list();
        for(Student student : studentList){
            student.setTestDayState(0);
            studentService.updateById(student);
        }
        return true;
    }

    /**
     * 移除掉不用的试题
     * @param name
     * @param typeName
     * @return
     */
    @GetMapping("/remove")
    Boolean remove(String name, String typeName){
        // 找到试题
        QueryWrapper<TestQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seat_type",typeName)
                .eq("name",name);
        List<TestQuestion> testQuestions  = testQuestionService.list(queryWrapper);
        if(testQuestions.isEmpty()){
            throw new BaseException("要保留的试题未找到，请核对后再次请求");
        }else {
            // 根据试题类型获取全部考题
            QueryWrapper<TestQuestion> testQuestionQueryWrapper = new QueryWrapper<>();
            testQuestionQueryWrapper.eq("seat_type",typeName);
            List<TestQuestion> testQuestionList = testQuestionService.list(testQuestionQueryWrapper);
            for(TestQuestion testQuestion : testQuestionList)
            {
                if(testQuestion.getName().equals(name)){
                    log.info("不删除试题 {}",testQuestion);
                }else {
                    testQuestionService.removeById(testQuestion.getId());
                    log.info("试题 {} 删除成功",testQuestion);
                }
            }
        }
        return true;
    }

    /**
     *  常用的四种 请求方式
     *  1. GET 从服务器获取资源
     *  2. POST 从服务器新建资源
     *  3. PUT 在服务器更新资源
     *  4. DELETE 从服务器删除资源
     */

}
