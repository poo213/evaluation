package com.njmetro.evaluation.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.JudgeDrawResultService;
import com.njmetro.evaluation.service.SeatDrawService;
import com.njmetro.evaluation.util.SeatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 牟欢
 * @Classname ReadyInterceptor
 * @Description TODO
 * @Date 2020-10-16 12:17
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ReadyInterceptor implements HandlerInterceptor {

    public final ConfigService configService;
    public final SeatDrawService seatDrawService;
    public final JudgeDrawResultService judgeDrawResultService;

    /**
     * 根据座位id 判断裁判是否准备就绪
     * @param seatId 座位id
     * @return
     */
    public Boolean getJudgeReadyStateBySeatId(Integer seatId){
        QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
        judgeDrawResultQueryWrapper.eq("seat_id",seatId);
        JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(judgeDrawResultQueryWrapper);
        log.info(seatId + "  "+judgeDrawResult.getState().toString());
        if(judgeDrawResult.getState() == 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 获取考生和裁判是否全部就绪
     * @return
     */
    public Boolean getReadyState(){
        Config config = configService.getById(1);
        // 1. 获取本场次本轮此全部 考生
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound());
        List<SeatDraw> seatDrawList = seatDrawService.list(seatDrawQueryWrapper);
        for(SeatDraw seatDraw : seatDrawList){
            log.info("################## {}",seatDraw.getSeatId());
            if(seatDraw.getState() != 1){
                return false;
            }else {
                Integer studentSeatId = seatDraw.getSeatId();
                Integer leftJudgeSeatId = SeatUtil.getLeftJudgeSeatIdByStudentSeatId(studentSeatId);
                Integer rightJudgeSeatId = SeatUtil.getRightJudgeSeatIdByStudentSeatId(studentSeatId);
                if(!getJudgeReadyStateBySeatId(leftJudgeSeatId)){
                    return false;
                }else {
                    if(!getJudgeReadyStateBySeatId(rightJudgeSeatId)){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 当更改完考生就绪状态和裁判就绪状态后，判断是否全部就绪，如果全部就绪，将可以抽题的状态改为 1
        Config config = configService.getById(1);
        if(getReadyState()){
            config.setState(1);
            configService.updateById(config);
            log.info("考生裁判全部就绪，可以抽题");
        }
    }
}
