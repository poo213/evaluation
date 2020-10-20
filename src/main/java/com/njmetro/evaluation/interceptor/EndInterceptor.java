package com.njmetro.evaluation.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.domain.JudgeSubmitState;
import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.JudgeSubmitStateService;
import com.njmetro.evaluation.service.SeatDrawService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 牟欢
 * @Classname EndInterceptor
 * @Description TODO
 * @Date 2020-10-19 10:18
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EndInterceptor implements HandlerInterceptor {
    public final ConfigService configService;
    public final SeatDrawService seatDrawService;
    public final JudgeSubmitStateService judgeSubmitStateService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Config config = configService.getById(1);
        Boolean flag = true;
        //判断考生是否全部结束
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound());
        List<SeatDraw> seatDrawList = seatDrawService.list(seatDrawQueryWrapper);
        for(SeatDraw seatDraw : seatDrawList){
            if(!seatDraw.getState().equals(4)){
                log.info("考生没有全部提交");
                flag =false;
                break;
            }
        }
        if(flag){
            // 当前场次轮次考生是否全部结束
            QueryWrapper<JudgeSubmitState> judgeSubmitStateQueryWrapper = new QueryWrapper<>();
            judgeSubmitStateQueryWrapper.eq("game_number",config.getGameNumber())
                    .eq("game_round",config.getGameRound());
            List<JudgeSubmitState> judgeSubmitStateList = judgeSubmitStateService.list(judgeSubmitStateQueryWrapper);
            for(JudgeSubmitState judgeSubmitState : judgeSubmitStateList){
                if(judgeSubmitState.getState().equals(0)){
                    log.info("裁判成绩没有全部提交");
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            log.info("全部提交，改变config中最终状态");
            config.setState(4);
            configService.updateById(config);
        }

    }
}
