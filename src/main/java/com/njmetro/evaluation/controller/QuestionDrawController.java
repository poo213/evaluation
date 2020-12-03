package com.njmetro.evaluation.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.exception.QuestionDrawException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.KnuthUtil;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.QuestionDrawVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final TestResultService testResultService;
    private final TestQuestionStandardService testQuestionStandardService;


    /**
     * 根据考试类型和比赛场次抽取赛题
     *
     * @param gameNumber 场次
     * @param type 考试类型
     * @return
     */
    public Boolean doDrawOneType1(Integer gameNumber,String type) {
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
        log.info("type {} 类型抽题成功",type);
        return true;
    }

    /**
     * 将成绩写入 test_result 表中
     * @return
     */
    public Boolean writeTestResult(){
        // 获取当前场次轮次信息
        Config config = configService.getById(1);
        QueryWrapper<TestResult> testResultQueryWrapper = new QueryWrapper<>();
        testResultQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound());
        List<TestResult> testResultList = testResultService.list(testResultQueryWrapper);
        if(testResultList.isEmpty()){
            // 最外层循环 36个执行裁判
            List<JudgeDrawResult> judgeDrawResultList = judgeDrawResultService.list();
            for(JudgeDrawResult judgeDrawResult : judgeDrawResultList){
                // 获取该裁判对应的试题 及 评分标准，最后写入 test_result 表中
                Integer judgeSeatId = judgeDrawResult.getSeatId();
                // 获取裁判id
                Integer judgeId = judgeDrawResultService.getById(judgeSeatId).getJudgeId();
                // 获取考生id
                QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
                seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                        .eq("game_round",config.getGameRound())
                        .eq("seat_id",SeatUtil.getStudentSeatIdByJudgeSeatId(judgeSeatId));
                SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);
                // 第七场 第六赛位没有学生考试
                if(seatDraw != null){
                    Integer studentId = seatDrawService.getOne(seatDrawQueryWrapper).getStudentId();
                    // 获取试题
                    String gameType = SeatUtil.getTypeNameByJudgeSeatId(judgeSeatId);
                    QueryWrapper<QuestionDraw> questionDrawQueryWrapper = new QueryWrapper<>();
                    questionDrawQueryWrapper.eq("game_type",gameType)
                            .eq("game_number",config.getGameNumber());
                    QuestionDraw questionDraw = questionDrawService.getOne(questionDrawQueryWrapper);
                    // 获取试题评分标准
                    QueryWrapper<TestQuestionStandard> testQuestionStandardQueryWrapper = new QueryWrapper<>();
                    testQuestionStandardQueryWrapper.eq("test_question_id",questionDraw.getQuestionId());
                    List<TestQuestionStandard> testQuestionStandardList = testQuestionStandardService.list(testQuestionStandardQueryWrapper);
                    // 模拟裁判写入成绩
                    for(TestQuestionStandard testQuestionStandard :testQuestionStandardList){
                        TestResult testResult = new TestResult();
                        testResult.setGameNumber(config.getGameNumber());
                        testResult.setGameRound(config.getGameRound());
                        // 默认得分为 0 分
                        testResult.setCent(0);
                        testResult.setQuestionStandardId(testQuestionStandard.getId());
                        testResult.setState(0);
                        testResult.setJudgeId(judgeId);
                        testResult.setQuestionId(questionDraw.getQuestionId());
                        testResult.setStudentId(studentId);
                        testResultService.save(testResult);
                    }
                }
            }
            return true;
        }else {
            throw new QuestionDrawException("抽签结果已产生，请勿重新抽签");
        }
    }

    /**
     * 裁判抽签
     * @return
     */
    @GetMapping("doDraw1")
    @Transactional
    public Boolean doDraw1() {
        // 根据比赛场次判断是否已经抽题
        Config config = configService.getById(1);
        Integer gameNumber = config.getGameNumber();
        List<QuestionDrawVO> questionDrawList = questionDrawService.selectQuestionDrawList(config.getGameNumber());
        if(questionDrawList.isEmpty()){
            log.info("进去抽题");
            // 列表为空，说明是第一轮，需要重新抽题
            doDrawOneType1(gameNumber,"光缆接续");
            doDrawOneType1(gameNumber,"交换机组网");
            doDrawOneType1(gameNumber,"视频搭建");
            log.info("抽题结束");
        }else {
            log.info("已抽题，无需再抽");
        }
        writeTestResult();
        // 抽题结束，改变抽题状态，不允许再次抽题
        config.setState(2);
        log.info("抽题： {}",config);
        return  configService.updateById(config);
    }

    /**
     * 根据考试类型和比赛场次抽取赛题
     *
     * @param type 考试类型
     * @return
     */
    public Boolean doDrawOneType(String type) {
        log.info("开始写时间 {}", LocalDateTime.now());
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
            // 插入七场抽签结果
            for(int i = 1 ; i <= 7 ; i++){
                log.info("2");
                QuestionDraw questionDraw = new QuestionDraw();
                questionDraw.setGameNumber(i);
                questionDraw.setGameType(type);
                questionDraw.setQuestionId(drawQuestionId);
                questionDrawService.save(questionDraw);
            }
        }
        log.info("结束时间 {}", LocalDateTime.now());
        log.info("type {} 类型抽题成功，七轮抽题结果全部写入数据库中",type);
        return true;
    }

    /**
     * 裁判抽签
     * @return
     */
    @GetMapping("doDraw")
    @Transactional
    public Boolean doDraw() {
        Config config = configService.getById(1);
        // 获取抽签列表
        List<QuestionDrawVO> questionDrawList = questionDrawService.selectQuestionDrawList(config.getGameNumber());
        if(questionDrawList.isEmpty()){
            // 没有抽签，需要抽签
            doDrawOneType("光缆接续");
            doDrawOneType("交换机组网");
            doDrawOneType("视频搭建");
            log.info("抽提");
        }else {
            log.info("已抽题，无需再抽");
        }
        // 抽题全部完成，将裁判打分结果全部写入数据库中
        writeTestResult();
        // 抽题结束，改变抽题状态，不允许再次抽题
        config.setState(2);
        log.info("抽题完成");
        return  configService.updateById(config);
    }

    /**
     *  返回已抽签的试题列表
     */
    @GetMapping("/getList")
    public List<QuestionDrawVO> getList(){
        Config config = configService.getById(1);
        return questionDrawService.selectQuestionDrawList(config.getGameNumber());
    }

}

