package com.njmetro.evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.exception.ConfigException;
import com.njmetro.evaluation.exception.JudgeSubmitStateException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.ConfigVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 主裁配置场次和轮次
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
@Slf4j
public class ConfigController {
    private static Integer INDEX_OUT_ROUND = 4;
    private static Integer INDEX_OUT_NUMBER = 8;
    private final ConfigService configService;
    private final JudgeDrawResultService judgeDrawResultService;
    private final SeatDrawService seatDrawService;
    private final JudgeSubmitStateService judgeSubmitStateService;
    private final QuestionDrawService questionDrawService;



    public Integer getJudgeId(Integer judgeSeatId){
        QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
        judgeDrawResultQueryWrapper.eq("seat_id",judgeSeatId);
        return judgeDrawResultService.getOne(judgeDrawResultQueryWrapper).getJudgeId();
    }


    /**
     * 将抽签结果写入系统中
     */
    public void writeJudgeSubmitState(){
        Config config = configService.getById(1);
        QueryWrapper<JudgeSubmitState> judgeSubmitStateQueryWrapper = new QueryWrapper<>();
        judgeSubmitStateQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound());
        List<JudgeSubmitState> judgeSubmitStateList = judgeSubmitStateService.list(judgeSubmitStateQueryWrapper);
        if(judgeSubmitStateList.isEmpty()){
            QueryWrapper<SeatDraw>seatDrawQueryWrapper = new QueryWrapper<>();
            seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                    .eq("game_round",config.getGameRound());
            List<SeatDraw> seatDrawList = seatDrawService.list(seatDrawQueryWrapper);
            // 根据 studentSeatId->judgeSeatId(2个)-> 场次轮次对应的 judgeId
            for(SeatDraw seatDraw : seatDrawList){
                Integer leftJudgeSeatId = SeatUtil.getLeftJudgeSeatIdByStudentSeatId(seatDraw.getSeatId());
                Integer RightJudgeSeatId = SeatUtil.getRightJudgeSeatIdByStudentSeatId(seatDraw.getSeatId());
                //  查找 leftJudgeId  rightJudgeId
                Integer leftJudgeId = getJudgeId(leftJudgeSeatId);
                Integer rightJudgeId = getJudgeId(RightJudgeSeatId);
                JudgeSubmitState leftJudgeSubmitState = new JudgeSubmitState(seatDraw,leftJudgeId);
                JudgeSubmitState rightJudgeSubmitState = new JudgeSubmitState(seatDraw,rightJudgeId);
                judgeSubmitStateService.save(leftJudgeSubmitState);
                judgeSubmitStateService.save(rightJudgeSubmitState);

            }
        }else {
            throw new JudgeSubmitStateException("下一场切换成功，请勿重新切换");
        }

    }


    /**
     * 获取本轮比赛状态
     * @return
     */
    @GetMapping("/getState")
    public Integer getState(){
        return configService.getById(1).getState();

    }

    /**
     * 切换下一场
     * @return
     */
    @GetMapping("/doNext")
    public Boolean doNext() {
        Config config = configService.getById(1);
        Integer gameRound = config.getGameRound() + 1;
        log.info("config1 {}",config);
        if (gameRound.equals(INDEX_OUT_ROUND)) {
            Integer gameNumber = config.getGameNumber() + 1 ;
            if(gameNumber.equals(INDEX_OUT_NUMBER)){
                log.info("全部考试已结束");
                throw new ConfigException("全部考试已结束");
            }else {
                config.setGameNumber(gameNumber);
                config.setGameRound(1);
                log.info("切换下一场");
            }
            log.info("正常切换场次~~~");
        }else {
            log.info("正常切换轮次~~~");
            config.setGameRound(gameRound);
        }
        config.setState(0);
        log.info("config {}",config);
        log.info("下发config{}",config);
        Boolean flag = configService.updateById(config);
        log.info("flag {}",flag);
        log.info("修改config成功");
        // 将裁判抽签结果表中的就绪状态全部设为 0
        List<JudgeDrawResult> judgeDrawResultList = judgeDrawResultService.list();
        for(JudgeDrawResult judgeDrawResult : judgeDrawResultList){
            judgeDrawResult.setState(0);
            judgeDrawResultService.updateById(judgeDrawResult);
        }
        // 将 seatDraw中裁判信息写入系统中
        writeJudgeSubmitState();
        return true;
    }

    /**
     * 下发试题
     * @return
     */
    @GetMapping("/doIssue")
    public Boolean doIssue(){
        // 将裁判状态改为2： 监考中
        List<JudgeDrawResult> judgeDrawResultList = judgeDrawResultService.list();
        for(JudgeDrawResult judgeDrawResult : judgeDrawResultList){
            judgeDrawResult.setState(2);
            judgeDrawResultService.updateById(judgeDrawResult);
        }
        // 将config 状态改为 2
        Config config = configService.getById(1);
        config.setState(3);
        return configService.updateById(config);
    }

    /**
     * 获取场次和轮次信息
     *
     * @return
     */
    @GetMapping("/getInfo")
    public ConfigVO getInfo() {
        Config config = configService.getById(1);
        ConfigVO configVO = new ConfigVO();
        configVO.setGameNumber(config.getGameNumber());
        configVO.setGameRound(config.getGameRound());
        return configVO;
    }

    /**
     * 配置场次和轮次信息
     *
     * @return
     */
    @PostMapping("/setInfo")
    public Boolean setInfo(@RequestBody Config config) {
        log.info(config.toString());
        return configService.updateById(config);
    }

}

