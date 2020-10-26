package com.njmetro.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.njmetro.evaluation.domain.JudgeSubmitState;
import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.JudgeDrawResultService;
import com.njmetro.evaluation.service.JudgeSubmitStateService;
import com.njmetro.evaluation.service.SeatDrawService;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.SeatDrawVO;
import com.njmetro.evaluation.vo.StudentReadyShowVO;
import com.njmetro.evaluation.vo.StudentShowVO;
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
 * @since 2020-09-28
 */
@Slf4j
@RestController
@RequestMapping("/seatDraw")
@RequiredArgsConstructor
public class SeatDrawController {
    private final SeatDrawService seatDrawService;
    private final ConfigService configService;
    private final JudgeDrawResultService judgeDrawResultService;
    private final JudgeSubmitStateService judgeSubmitStateService;


    @GetMapping("/getSeatDrawList")
    public List<SeatDrawVO> getSeatDrawList() {
        return seatDrawService.getSeatDraw();
    }

    /**
     * 返回考生赛场状态（1： 就绪  2：比赛中 3：中断 4： 结束  5：缺考  6: 违纪）
     *
     * @return
     */
    @GetMapping("/getStudentReadyShowVO")
    public List<StudentReadyShowVO> getStudentReadyShowVO() {
        Config config = configService.getById(1);
        List<StudentReadyShowVO> studentReadyShowVOS = seatDrawService.listStudentReady(config.getGameNumber(), config.getGameRound());
        if (studentReadyShowVOS.isEmpty()) {
            return null;
        } else {
            return studentReadyShowVOS;
        }
    }


    /**
     * 根据裁判座位Id将 裁判状态改为 就绪状态 1; 并获取裁判id
     *
     * @param judgeSeatId
     */
    Integer changeJudgeState(Integer judgeSeatId) {
        QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
        judgeDrawResultQueryWrapper.eq("seat_id", judgeSeatId);
        JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(judgeDrawResultQueryWrapper);
        judgeDrawResult.setState(1);
        judgeDrawResultService.updateById(judgeDrawResult);
        return judgeDrawResult.getJudgeId();
    }

    void changeJudgeSubmitState(Integer gameNumber, Integer gameRound, Integer studentId, Integer judgeId) {
        QueryWrapper<JudgeSubmitState> judgeSubmitStateQueryWrapper = new QueryWrapper<>();
        judgeSubmitStateQueryWrapper.eq("game_number", gameNumber)
                .eq("game_round", gameRound)
                .eq("student_id", studentId)
                .eq("judge_id", judgeId);
        JudgeSubmitState judgeSubmitState = judgeSubmitStateService.getOne(judgeSubmitStateQueryWrapper);

        if (judgeSubmitState != null) {
            judgeSubmitState.setState(1);
            judgeSubmitStateService.updateById(judgeSubmitState);
        }

    }

    Integer getStudentIdBySeatId(Integer gameNumber, Integer gameRound, Integer seatId) {
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("game_number", gameNumber)
                .eq("game_round", gameRound)
                .eq("seat_id", seatId);
        return seatDrawService.getOne(seatDrawQueryWrapper).getStudentId();
    }

    Boolean changeStudentState(Integer seatDrawId, Integer type) {
        Config config = configService.getById(1);
        // 将考生状态改为 5
        SeatDraw seatDraw = seatDrawService.getById(seatDrawId);
        switch (type) {
            case 5:
                seatDraw.setState(5);
                break;
            case 6:
                seatDraw.setState(6);
                break;
        }
        // 找到考生对应的裁判，将状态改为就绪
        Integer studentSeatId = seatDraw.getSeatId();
        Integer leftJudgeSeatId = SeatUtil.getLeftJudgeSeatIdByStudentSeatId(studentSeatId);
        Integer rightJudgeSeatId = SeatUtil.getRightJudgeSeatIdByStudentSeatId(studentSeatId);
        Integer leftJudgeId = changeJudgeState(leftJudgeSeatId);
        Integer rightJudgeId = changeJudgeState(rightJudgeSeatId);
        changeJudgeState(rightJudgeSeatId);
        // 将裁判最终提交成绩改为 1
        Integer studentId = getStudentIdBySeatId(config.getGameNumber(), config.getGameRound(), studentSeatId);
        changeJudgeSubmitState(config.getGameNumber(), config.getGameRound(), studentId, leftJudgeId);
        changeJudgeSubmitState(config.getGameNumber(), config.getGameRound(), studentId, rightJudgeId);
        return seatDrawService.updateById(seatDraw);
    }


    /**
     * 设置考生缺考
     *
     * @param seatDrawId
     * @return
     */
    @GetMapping("/miss/beReady")
    public Boolean doStudentMiss(Integer seatDrawId) {
       return changeStudentState(seatDrawId,5);
    }

    /**
     * 设置考生违纪
     *
     * @param seatDrawId
     * @return
     */
    @GetMapping("/error/beReady")
    public Boolean doStudentError(Integer seatDrawId) {
        return changeStudentState(seatDrawId,6);
    }


}

