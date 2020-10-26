package com.njmetro.evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.JudgeDrawResultService;
import com.njmetro.evaluation.service.SeatDrawService;
import com.njmetro.evaluation.util.SeatUtil;
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
    public final SeatDrawService seatDrawService;
    public final ConfigService configService;


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
                .eq("game_round",config.getGameRound());
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
}

