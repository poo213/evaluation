package com.njmetro.evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.exception.QuestionDrawException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.KnuthUtil;
import com.njmetro.evaluation.vo.QuestionDrawVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-29
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/questionDraw")
public class QuestionDrawController {
    public final TestQuestionService testQuestionService;
    public final QuestionDrawService questionDrawService;
    public final ConfigService configService;
    public final JudgeDrawResultService judgeDrawResultService;
    public final SeatDrawService seatDrawService;

    /**
     * 根据考试类型和比赛场次抽取赛题
     *
     * @param gameNumber 场次
     * @param type 考试类型
     * @return
     */
    public Boolean doDrawOneType(Integer gameNumber,String type) {
        // 根据考试类型查找所有试题
        QueryWrapper<TestQuestion> testQuestionQueryWrapper = new QueryWrapper<>();
        testQuestionQueryWrapper.eq("seat_type", type);
        List<TestQuestion> testQuestionList = testQuestionService.list(testQuestionQueryWrapper);
        if (testQuestionList.isEmpty()) {
            throw new QuestionDrawException(type + "类型考试试题数目为空,抽签失败");
        } else {
            // 采用抽签算法，挑选试题ID
            Integer[] arr = new Integer[testQuestionList.size()];
            for (int i = 0 ; i < testQuestionList.size() ; i++){
                arr[i] = testQuestionList.get(i).getId();
            }
            // 选取打乱后的第一个值，作为抽签结果
            Integer drawQuestionId = KnuthUtil.result(arr)[0];
            // 根据场次 和 类型
            QueryWrapper<QuestionDraw> questionDrawQueryWrapper = new QueryWrapper<>();
            questionDrawQueryWrapper.eq("game_number",gameNumber)
                    .eq("game_type",type);
            QuestionDraw questionDraw = questionDrawService.getOne(questionDrawQueryWrapper);
            if(questionDraw == null){
                // 插入一条新纪录
                QuestionDraw saveQuestionDraw = new QuestionDraw();
                saveQuestionDraw.setQuestionId(drawQuestionId);
                saveQuestionDraw.setGameNumber(gameNumber);
                saveQuestionDraw.setGameType(type);
                questionDrawService.save(saveQuestionDraw);
                log.info("{} 场次 {} 类型，插入成功",gameNumber,type);
            }else {
                questionDraw.setQuestionId(drawQuestionId);
                questionDrawService.updateById(questionDraw);
                log.info("{} 场次 {} 类型，更改成功",gameNumber,type);
            }
        }
        return true;
    }

    /**
     * 裁判抽签
     * @return
     */
    @GetMapping("/doDraw")
    public Boolean doDraw() {
        Integer gameNumber = configService.getById(1).getGameNumber();
        doDrawOneType(gameNumber,"光缆接续");
        doDrawOneType(gameNumber,"交换机组网");
        doDrawOneType(gameNumber,"视频搭建");
        return true;
    }

    /**
     *  返回已抽签的试题列表
     */
    @GetMapping("/getList")
    public List<QuestionDrawVO> getList(){
        return questionDrawService.selectList();
    }

    @GetMapping("/getReadyState")
    public Boolean getReadyState(){
        Config config = configService.getById(1);
        // 判断裁判是否全部就绪
       /* QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
        judgeDrawResultQueryWrapper.eq()
        JudgeDrawResult*/

        return true;
    }

}

