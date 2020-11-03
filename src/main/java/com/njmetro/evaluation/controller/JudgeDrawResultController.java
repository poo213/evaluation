package com.njmetro.evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.JudgeDrawResultShowVO;
import com.njmetro.evaluation.vo.JudgeReadyShowVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-29
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/judgeDrawResult")
public class JudgeDrawResultController {
    public final JudgeDrawResultService judgeDrawResultService;
    public final JudgeSubmitStateService judgeSubmitStateService;
    public final TestResultService testResultService;
    public final TestQuestionStandardService testQuestionStandardService;
    public final QuestionDrawService questionDrawService;
    public final SeatDrawService seatDrawService;
    public final ConfigService configService;
    public final JudgeService judgeService;


    /**
     * 获取单个裁判 就绪状态表
     * @param left
     * @return
     */
    List<JudgeReadyShowVO> getJudgeReadyListOne(Integer left){
        // 根据当前场次轮次找到对应考生座位id
        Config config = configService.getById(1);
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound())
                .orderByAsc("seat_id");
        List<SeatDraw> seatDrawList = seatDrawService.list(seatDrawQueryWrapper);
        // 存放裁判 seatId的数组
        int[] judgeSeatIdArray = new int[seatDrawList.size()];
        for(int i = 0 ; i< seatDrawList.size() ; i++){
            Integer studentSeatId = seatDrawList.get(i).getSeatId();
            if(left == 0){
                judgeSeatIdArray[i] = SeatUtil.getLeftJudgeSeatIdByStudentSeatId(studentSeatId);
            }else {
                judgeSeatIdArray[i] = SeatUtil.getRightJudgeSeatIdByStudentSeatId(studentSeatId);
            }

        }
        List<JudgeReadyShowVO> judgeReadyShowVOList = new ArrayList<>();
        for(int i = 0; i < judgeSeatIdArray.length; i++){
            judgeReadyShowVOList.add(judgeDrawResultService.getJudgeReadyShowVO(judgeSeatIdArray[i]));
        }
        return judgeReadyShowVOList;
    }

    /**
     * 获取左侧裁判就绪状态
     * @return
     */
    @GetMapping("/getLeftJudgeReadyList")
    List<JudgeReadyShowVO> getLeftJudgeReadyList(){
        return getJudgeReadyListOne(0);
    }

    /**
     * 获取右侧裁判就绪状态
     * @return
     */
    @GetMapping("/getRightJudgeReadyList")
    List<JudgeReadyShowVO> getRightJudgeReadyList(){
        return getJudgeReadyListOne(1);
    }

    /**
     * 获取全部才哦按
     * @return
     */
    @GetMapping("/getJudgeReadyList")
    List<JudgeReadyShowVO> getJudgeReadyList(){
        // 根据当前场次轮次找到对应考生座位id
        Config config = configService.getById(1);
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound());
        List<SeatDraw> seatDrawList = seatDrawService.list(seatDrawQueryWrapper);
        // 存放裁判 seatId的数组
        int[] judgeSeatIdArray = new int[seatDrawList.size() * 2];
        for(int i = 0 ; i< seatDrawList.size() ; i++){
           Integer studentSeatId = seatDrawList.get(i).getSeatId();
            judgeSeatIdArray[i*2] = SeatUtil.getLeftJudgeSeatIdByStudentSeatId(studentSeatId);
            judgeSeatIdArray[i*2+1] = SeatUtil.getRightJudgeSeatIdByStudentSeatId(studentSeatId);
        }
        List<JudgeReadyShowVO> judgeReadyShowVOList = new ArrayList<>();
        for(int i = 0; i < judgeSeatIdArray.length; i++){
            judgeReadyShowVOList.add(judgeDrawResultService.getJudgeReadyShowVO(judgeSeatIdArray[i]));
        }
        return judgeReadyShowVOList;
    }

    /**
     * 获取当前执行裁判列表
     * @return
     */
    @GetMapping("/getJudgeDrawResultShowVOList")
    List<JudgeDrawResultShowVO> getJudgeDrawResultShowVOList(){
        return judgeDrawResultService.getJudgeDrawResultShowVOList();
    }

    /**
     * 修改 执行裁判
     */
    @GetMapping("/doChangeJudge")
    Boolean doChangeJudge(Integer seatId,Integer judgeId){
        // 修改 赛位中裁判id
        JudgeDrawResult judgeDrawResult = judgeDrawResultService.getById(seatId);
        Integer backUpJudgeId = judgeDrawResult.getJudgeId();
        judgeDrawResult.setJudgeId(judgeId);
        judgeDrawResultService.updateById(judgeDrawResult);
        // 被换下裁判,改为候补裁判
        Judge judgeMaster = judgeService.getById(backUpJudgeId);
        judgeMaster.setMaster(0);
        judgeService.updateById(judgeMaster);
        // 被换上裁判，改为主裁
        Judge judgeBackUp = judgeService.getById(judgeId);
        judgeBackUp.setMaster(1);
        judgeService.updateById(judgeBackUp);
        return true;
    }

    /**
     * 根据裁判座位Id 修改裁判为手动补录状态
     *
     * @param judgeSeatId 裁判座位id
     * @return
     */
    @GetMapping("/judgeWriteResult/submit")
    Boolean changeWriteResultOK(Integer judgeSeatId){
        Config config = configService.getById(1);
        // 获取裁判id
        Integer judgeId = judgeDrawResultService.getById(judgeSeatId).getJudgeId();
        // 获取考生id
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound())
                .eq("seat_id",SeatUtil.getStudentSeatIdByJudgeSeatId(judgeSeatId));
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
            testResult.setGameNumber(config.getGameRound());
            testResult.setGameRound(config.getGameRound());
            // 默认得分为 0 分
            testResult.setCent(0);
            testResult.setQuestionStandardId(testQuestionStandard.getId());
            testResult.setState(1);
            testResult.setJudgeId(judgeId);
            testResult.setQuestionId(questionDraw.getQuestionId());
            testResult.setStudentId(studentId);
            testResultService.save(testResult);
        }
        JudgeDrawResult judgeDrawResult = judgeDrawResultService.getById(judgeSeatId);
        judgeDrawResult.setState(3);
        judgeDrawResultService.updateById(judgeDrawResult);
        // 修改 judge_submit_state state 为 2
        QueryWrapper<JudgeSubmitState> judgeSubmitStateQueryWrapper = new QueryWrapper<>();
        judgeSubmitStateQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound())
                .eq("judge_id",judgeDrawResult.getJudgeId());
        JudgeSubmitState judgeSubmitState = judgeSubmitStateService.getOne(judgeSubmitStateQueryWrapper);
        judgeSubmitState.setState(2);
        return judgeSubmitStateService.updateById(judgeSubmitState);
    }

}

