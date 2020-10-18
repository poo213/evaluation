package com.njmetro.evaluation.controller;

import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.SeatDrawService;
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

    @GetMapping("/getSeatDrawList")
    public List<SeatDrawVO> getSeatDrawList() {
        return seatDrawService.getSeatDraw();
    }

    /**
     * 返回考生赛场状态（1： 就绪  2：比赛中 3：中断 4： 结束）
     * @return
     */
    @GetMapping("/getStudentReadyShowVO")
    public List<StudentReadyShowVO> getStudentReadyShowVO(){
        Config config = configService.getById(1);
        List<StudentReadyShowVO> studentReadyShowVOS = seatDrawService.listStudentReady(config.getGameNumber(),config.getGameRound());
        if(studentReadyShowVOS.isEmpty()){
            return null;
        }else {
            return studentReadyShowVOS;
        }
    }

}

